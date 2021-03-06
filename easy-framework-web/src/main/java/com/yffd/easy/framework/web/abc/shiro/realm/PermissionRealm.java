package com.yffd.easy.framework.web.abc.shiro.realm;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.common.core.util.ValidUtils;
import com.yffd.easy.framework.web.abc.shiro.login.account.ShiroAccountInfo;
import com.yffd.easy.framework.web.abc.shiro.login.account.ShiroUserInfo;
import com.yffd.easy.framework.web.abc.shiro.login.service.ILoginService;
import com.yffd.easy.framework.web.mvc.WebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月8日 上午9:52:35 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PermissionRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(PermissionRealm.class);

	@Autowired
	private ILoginService loginService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		LOG.info("=========shiro PermissionRealm [设置登录信息--角色、资源]");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute(WebController.KEY_SESSION_LOGIN_INFO);
		if(null==userInfo) return null;
		// 设置角色
		Set<String> roleCodes = userInfo.getRoleCods();
		authorizationInfo.setRoles(roleCodes);
		// 设置资源
		Set<String> rsCodes = userInfo.getRsCodes();
		authorizationInfo.setStringPermissions(rsCodes);
        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LOG.info("=========shiro PermissionRealm [登录认证]");
		String accountId = (String)token.getPrincipal();
		if(ValidUtils.isBlank(accountId)) throw new UnknownAccountException();// 没找到帐号
		
		ShiroAccountInfo account = this.loginService.getAccountInfo(accountId);
		if(null==account) throw new UnknownAccountException();//没找到帐号
		if("active".equals(account.getAccountStatus())) 
			throw new LockedAccountException(); //帐号锁定
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				account.getAccountId(), //账号
				account.getAccountPwd(), //密码
                ByteSource.Util.bytes(account.getCredentialsSalt()),
                getName()  //realm name
        );
        return authenticationInfo;
	}
	
}

