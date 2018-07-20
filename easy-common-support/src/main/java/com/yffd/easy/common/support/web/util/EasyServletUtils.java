package com.yffd.easy.common.support.web.util;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月25日 上午10:03:59 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyServletUtils {
	private static final Logger LOG = LoggerFactory.getLogger(EasyServletUtils.class);
	
	/**
	 * 是否是Ajax请求
	 * @Date	2018年6月25日 上午10:05:52 <br/>
	 * @author  zhangST
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * 异步响应指定的信息
	 * @Date	2018年6月25日 上午10:05:56 <br/>
	 * @author  zhangST
	 * @param response
	 * @param message
	 */
	public static void respAjax(ServletResponse response, String message) {
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

