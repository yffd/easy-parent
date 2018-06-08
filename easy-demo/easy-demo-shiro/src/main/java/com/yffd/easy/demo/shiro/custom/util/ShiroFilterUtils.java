package com.yffd.easy.demo.shiro.custom.util;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午4:16:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ShiroFilterUtils {
	private static final Logger LOG = LoggerFactory.getLogger(ShiroFilterUtils.class);
	
	//登录页面
	static final String LOGIN_URL = "/u/login.shtml";
	//踢出登录提示
	final static String KICKED_OUT = "/open/kickedOut.shtml";
	//没有权限提醒
	final static String UNAUTHORIZED = "/open/unauthorized.shtml";
	
	/**
	 * 是否是Ajax请求
	 * @Date	2018年6月4日 下午4:17:40 <br/>
	 * @author  zhangST
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * 异步响应指定的信息
	 * @Date	2018年6月4日 下午4:21:20 <br/>
	 * @author  zhangST
	 * @param response
	 * @param message
	 */
	public static void responseAjax(ServletResponse response, String message) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(message);
		} catch (Exception e) {
			LOG.error("输出JSON报错", e);
		} finally {
			if(null!=out) {
				out.flush();
				out.close();
			}
		}
	}
}

