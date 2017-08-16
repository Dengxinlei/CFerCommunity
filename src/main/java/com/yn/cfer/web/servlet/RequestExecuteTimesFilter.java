package com.yn.cfer.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yn.cfer.web.protocol.MyRequestWrapper;

public class RequestExecuteTimesFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(RequestExecuteTimesFilter.class);
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
        long t1 = System.currentTimeMillis();
        logger.debug("RequestId=[{}], RequestExecuteTimesFilter-> Path=[{}], Content=[{}] IP=[{}]", t1, requestUri, content, remoteIp);
        chain.doFilter(request, response);
        long t2 = System.currentTimeMillis();
        logger.debug("RequestId=[{}], path=[{}], 花费时间＝[{}], IP=[{}]", t1, requestUri, t2 - t1, remoteIp);
    }

    public void init(FilterConfig arg0) throws ServletException {

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
}
