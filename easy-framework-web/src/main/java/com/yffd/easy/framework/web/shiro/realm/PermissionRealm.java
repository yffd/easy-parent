package com.yffd.easy.framework.web.shiro.realm;

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
import com.yffd.easy.framework.web.enums.WebCommonEnum;
import com.yffd.easy.framework.web.login.service.ILoginService;
import com.yffd.easy.framework.web.model.LoginInfo;
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
		LOG.info("=========shiro PermissionRealm [设置登录信息--角色、权限]");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(WebController.KEY_SESSION_LOGIN_INFO);
		if(null==loginInfo) return null;
		// 设置角色
		Set<String> roles = loginInfo.getRoles();
		authorizationInfo.setRoles(roles);
		// 设置权限
		Set<String> resourceCodes = loginInfo.getResources();
		authorizationInfo.setStringPermissions(resourceCodes);
        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LOG.info("=========shiro PermissionRealm [登录认证]");
		String accountId = (String)token.getPrincipal();
		if(ValidUtils.isBlank(accountId)) throw new UnknownAccountException();// 没找到帐号
		
		LoginInfo loginInfo = this.loginService.getLoginInfo(accountId);
		if(null==loginInfo) throw new UnknownAccountException();//没找到帐号
		if(WebCommonEnum.INACTIVE.getValue().equals(loginInfo.getAccountStatus())) 
			throw new LockedAccountException(); //帐号锁定
		String credentialsSalt = loginInfo.getAccountId() + loginInfo.getAccountSalt();
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				loginInfo.getAccountId(), //账号
				loginInfo.getAccountPwd(), //密码
                ByteSource.Util.bytes(credentialsSalt),//salt=accountId+salt
                getName()  //realm name
        );
        return authenticationInfo;
	}
	
}

