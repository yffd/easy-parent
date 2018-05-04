package com.yffd.easy.framework.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月13日 上午11:37:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class LoginValidateFilter extends FormAuthenticationFilter {
	private Logger LOG = LoggerFactory.getLogger(LoginValidateFilter.class);
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if(request.getAttribute(getFailureKeyAttribute()) != null) {
			return true;
		}
		AuthenticationToken token = this.createToken(request, response);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch(ShiroException e) {
			request.setAttribute(getFailureKeyAttribute(), e.getClass().getName());
			LOG.error("shiro认证错误:" + e.getClass().getName());
		}
		return true;
	}
}

