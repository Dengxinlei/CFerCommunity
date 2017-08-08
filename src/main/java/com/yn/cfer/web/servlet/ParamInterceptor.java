package com.yn.cfer.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 参数拦截器
 */
public class ParamInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(ParamInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if(StringUtils.isBlank(url) || url.endsWith(".html")
                || url.endsWith(".css") || url.endsWith(".js")
                || url.endsWith(".jpg") || url.endsWith(".png")){
            return true;
        }
        logger.debug("解析请求参数，请求地址:{}", url);
        try {
            String json = getRequestBody( request );
            Map<String,Object> params = (Map<String,Object>)JSON.parse( json );
            JSONObject client = (JSONObject)MapUtils.getObject(params, "client");				// 公共参数
            JSONObject resp = new JSONObject();
            if(client == null) {
    			resp.put("code", 1);
    			resp.put("message", "miss common param");
    			response.getWriter().write(resp.toJSONString());
    			response.getWriter().flush();
    			return false;
    		}
            request.setAttribute("params", params);
        } catch ( Exception e ) {
            logger.error("解析参数出错，请求地址：{}", request.getRequestURI());
        }
        return true;
    }

    /**
     * 从request获取请求内容
     * @param request
     * @return
     */
    private static String getRequestBody(HttpServletRequest request) {
        String json = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = request.getInputStream();
            StringBuilder messageBuffer = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                messageBuffer.append(line);
            }
            json = messageBuffer.toString();
            logger.info("接收JSON:{}", json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( br != null ){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( is != null ) {
                try {
                    is.close();
                } catch ( Exception e ){
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    public final String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
