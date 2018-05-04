package com.yffd.easy.framework.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.framework.web.model.LoginInfo;
import com.yffd.easy.framework.web.mvc.WebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月13日 上午11:37:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PermissionValidateFilter extends AuthenticationFilter {
	private Logger LOG = LoggerFactory.getLogger(PermissionValidateFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		LOG.error("shiro授权[权限不足，拒绝访问]");
		Subject subject = this.getSubject(request, response);
		Session session = subject.getSession();
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(WebController.KEY_SESSION_LOGIN_INFO);
		if(null==loginInfo) { // 未登录情况下，访问资源，重定向到登录页面
			WebUtils.issueRedirect(request, response, "/admin/noLogin.jsp");
		}
		return false;
	}
	
}

