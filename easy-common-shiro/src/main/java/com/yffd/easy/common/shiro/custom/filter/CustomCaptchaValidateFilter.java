package com.yffd.easy.common.shiro.custom.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.shiro.support.constants.ShiroConstants;
import com.yffd.easy.common.support.web.util.EasyServletUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月3日 上午9:58:13 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomCaptchaValidateFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(CustomCaptchaValidateFilter.class);
	
	private boolean captchaEbabled = true;// 是否开启验证码支持

	private String captchaParam = "captchaCode";// 前台提交的验证码参数名

	public void setCaptchaEbabled(boolean captchaEbabled) {
		this.captchaEbabled = captchaEbabled;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter captcha =========");
		// 1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
		request.setAttribute("captchaEbabled", captchaEbabled);

		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		// 2、判断验证码是否禁用 或不是表单提交（允许访问）
//		if(!"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
//			if(LOG.isInfoEnabled()) LOG.info("========= login is not post method =========");
//			return false;
//		}
		if(captchaEbabled == false) {
			return true;
		}
		// 3、此时是表单提交，验证验证码是否正确
		// 获取页面提交的验证码
		String submitCaptcha = httpServletRequest.getParameter(captchaParam);
		// 获取session中的验证码
		HttpSession session = httpServletRequest.getSession();
		String captcha = (String) session.getAttribute(session.getId());
		if(submitCaptcha.equals(captcha)) {
			return true;
		}
		// 如果验证码失败了，存储失败key属性
		request.setAttribute(ShiroConstants.ATTRIBUTE_KEY_LOGIN_FAILURE, ShiroConstants.MSG_KEY_CODE_100003);
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter captcha error : " + ShiroConstants.MSG_KEY_DESC_100003);
		// 输出信息
		Map<String, String> msg = new HashMap<>();
		msg.put("code", ShiroConstants.MSG_KEY_CODE_100003);
		msg.put("desc", ShiroConstants.MSG_KEY_DESC_100003);
		
		if(EasyServletUtils.isAjax(request)) {
			EasyServletUtils.respAjax(response, JSON.toJSONString(msg));
		} else {
			WebUtils.issueRedirect(request, response, getLoginUrl(), msg);
		}
		
		return Boolean.FALSE;
	}

}

