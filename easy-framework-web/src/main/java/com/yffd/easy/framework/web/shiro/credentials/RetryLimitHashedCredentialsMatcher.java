package com.yffd.easy.framework.web.shiro.credentials;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.framework.web.login.service.ILoginService;
import com.yffd.easy.framework.web.model.LoginInfo;
import com.yffd.easy.framework.web.mvc.WebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月8日 上午10:06:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	@Autowired
	private ILoginService loginService;
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		boolean matches = super.doCredentialsMatch(token, info);
		String accountId = (String)token.getPrincipal();
		if(matches) {
			LoginInfo loginInfo = this.loginService.getLoginInfo(accountId);
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			session.setAttribute(WebController.KEY_SESSION_LOGIN_INFO, loginInfo);
		}
		return matches;
	}

	
}

