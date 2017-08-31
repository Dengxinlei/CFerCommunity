package com.yn.cfer.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.DARK_TEAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.protocol.MyRequestWrapper;

public class RequestExecuteTimesFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(RequestExecuteTimesFilter.class);
    // key: | String | 用户上传上来的登录token    value: | JSONObject | "memberId"=会员Id, "userId"=用户Id, "userType"=用户类型, "expire"=失效时间（2017-02-03 22:36:12）
    public final static Map<String, JSONObject> memberIdCache = new HashMap<String, JSONObject>();
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String remoteIp = null;
        try {
			remoteIp = getIpAddr(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
        // 带有缓存流的request对象
        request = new MyRequestWrapper(req);
        String requestUri = req.getRequestURI();
        String content = getBody(request.getReader());
        if(StringUtils.isNotBlank(content)) {
        	JSONObject obj = JSON.parseObject(content);
        	String token = obj.getString("token");
        	if(StringUtils.isBlank(token)) {
        		response(ErrorCode.ERROR_CODE_MISS_TOKEN, "缺少token参数", response);
        		return;
        	}
        	if(tokenIsExpired(token)) {
        		response(ErrorCode.ERROR_CODE_TOKEN_IS_EXPIRED, "token已失效", response);
        		return;
        	}
        	
        }
        long t1 = System.currentTimeMillis();
        logger.debug("RequestId=[{}], RequestExecuteTimesFilter-> Path=[{}], Content=[{}] IP=[{}]", t1, requestUri, content, remoteIp);
        chain.doFilter(request, response);
        long t2 = System.currentTimeMillis();
        logger.debug("RequestId=[{}], path=[{}], 花费时间＝[{}], IP=[{}]", t1, requestUri, t2 - t1, remoteIp);
    }

    public void init(FilterConfig arg0) throws ServletException {
    	ServletContext sc = arg0.getServletContext(); 
        XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
//      if(cxt != null && cxt.getBean("usersService") != null && usersService == null)
//        usersService = (UsersService) cxt.getBean("usersService");
    }
    private boolean tokenIsExpired(String token) {
    	JSONObject userInfo = memberIdCache.get(token);
    	if(userInfo == null) {
    		return false;
    	}
    	Date expiredTime = string4Format(userInfo.getString("expire"), "yyyy-MM-dd HH:mm:ss");
    	if(expiredTime == null) {
    		return false;
    	}
    	Date now = new Date();
    	if(now.compareTo(expiredTime) >= 0) {
    		return true;
    	}
    	return false;
    }
    // 根据客户端上传token 获取member_id
    private void getMemberId(String token) {
    	// 根据token 查询T_TOKEN表
    	// 根据USER_ID查询 T_USER表
    	// 比对USER_TYPE 如果不等于4返回无效用户
    	// 将拿到的用户信息存入缓存
    }
    private void response(Integer code, String message, ServletResponse response) {
    	try {
			PrintWriter writer = response.getWriter();
			JSONObject resp = new JSONObject();
			resp.put("code", code);
			resp.put("message", message);
			writer.write(resp.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    private String getBody(BufferedReader br) {
        try {
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            return sb.toString();
        } catch (IOException e) {
            logger.error("读取请求参数异常: {}", e.toString());
        }
        return null;
    }
    /** 
     * 获取访问用户的客户端IP（适用于公网与局域网）. 
     */  
    public static final String getIpAddr(final HttpServletRequest request)  
            throws Exception {  
        if (request == null) {  
            throw (new Exception("getIpAddr method HttpServletRequest Object is null"));  
        }  
        String ipString = request.getHeader("x-forwarded-for");  
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
            ipString = request.getHeader("Proxy-Client-IP");  
        }  
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
            ipString = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
            ipString = request.getRemoteAddr();  
        }  
      
        // 多个路由时，取第一个非unknown的ip  
        final String[] arr = ipString.split(",");  
        for (final String str : arr) {  
            if (!"unknown".equalsIgnoreCase(str)) {  
                ipString = str;  
                break;  
            }  
        }  
      
        return ipString;  
    } 
    
    private static Date string4Format(String date, String format) {
    	if(StringUtils.isBlank(date)) {
    		return null;
    	}
        try {
        	
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
    }
}
