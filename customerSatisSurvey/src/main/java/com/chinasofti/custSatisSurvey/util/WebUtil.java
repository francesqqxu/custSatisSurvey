package com.chinasofti.custSatisSurvey.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.custSatisSurvey.constant.WebConst;
import com.chinasofti.custSatisSurvey.pojo.TUser;

 

 

public class WebUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

	 /**
     * 返回当前登录用户
     *
     * @param request
     * @return
     */
    public static TUser getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (TUser) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
    }
    
    /**
     * 返回当前登录用户
     *
     * @param request
     * @return
     */
    public static TUser getLoginCustUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (TUser) session.getAttribute(WebConst.LOGINCUST_SESSION_KEY);
    }
    
    
    public static void getReqInfo(HttpServletRequest httpReq) {
		System.out.println("httpReq.target: " + httpReq.getParameter("target")); 
		System.out.println("httpReq.remoteUser: " + httpReq.getRemoteUser());
		System.out.println("httpReq.LTPAToken: " + httpReq.getParameter("LTPAToken"));
		
		System.out.println("------headers-----");
		Enumeration<String> headNames =  httpReq.getHeaderNames();
		
		while(headNames.hasMoreElements()) {
			String headName = headNames.nextElement();
			System.out.println(headName + ":" + httpReq.getHeader(headName));
		}
		
		System.out.println("-----paramters-----");
		Map<String,String[]> map = httpReq.getParameterMap();
		Set<String> keys = map.keySet();
		for(String key : keys) {
			String[] value = map.get(key);
			System.out.println(key + "= " + value[0]);
		}
		
		System.out.println("-----session-----");
		HttpSession session = httpReq.getSession();
		
		Enumeration<?> enumeration = session.getAttributeNames();
		while(enumeration.hasMoreElements()) {
			String name = enumeration.nextElement().toString();
			
			Object value = session.getAttribute(name);
			System.out.println("<B>" + name + "</B>=" + value + "<br>/n");
			
		}
		
				
		System.out.println("request.getContentLength():" + httpReq.getContentLength());
		System.out.println("request.getContentType():" + httpReq.getContentType());
		System.out.println("request.getcontextPath():" + httpReq.getContextPath());
		System.out.println("request.getMethod():" + httpReq.getMethod());
		System.out.println("request.getLocale():" + httpReq.getLocale());
		System.out.println("request.getQueryString():" + httpReq.getQueryString());
		System.out.println("request.getRequestURI():" + httpReq.getRequestURI());
		System.out.println("request.getRequestURL():" + httpReq.getRequestURL());
		System.out.println("request.getServletPath():" + httpReq.getServletPath());
		System.out.println("request.getRemoteAddr():" + httpReq.getRemoteAddr());
		System.out.println("request.getRemoteHost():" + httpReq.getRemoteHost());
		System.out.println("request.getRemotePort():" + httpReq.getRemotePort());
		System.out.println("request.getServerName():" + httpReq.getServerName());
		System.out.println("request.getServerPort();" + httpReq.getServerPort());
	}


}
