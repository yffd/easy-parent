package com.yffd.easy.common.security.shiro.realm;

import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.security.service.ISecurityService;
import com.yffd.easy.common.security.vo.LoginAccountVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月22日 下午1:48:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class ShiroSecurityRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(ShiroSecurityRealm.class);
	
	private ISecurityService securityService;
	
	public ISecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(LOG.isInfoEnabled()) LOG.info("=========shiro realm [授权]");
		if(principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String username = (String) getAvailablePrincipal(principals);
        Set<String> roleCodes = this.securityService.getRoleCodes(username);
        Set<String> pmsCodes = this.securityService.getPmsCodes(username);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleCodes);
        info.setStringPermissions(pmsCodes);
        return info;
	}

	// 认证信息，主要针对用户登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(LOG.isInfoEnabled()) LOG.info("=========shiro realm [认证]");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if(username == null) {
            throw new AccountException("账号名称(ID)不能为空.");
        }
        LoginAccountVo accountVo = this.securityService.getLoginAccountVo(username);
        if(null==accountVo) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        if(accountVo.isLocked()) {
        	throw new LockedAccountException("Locked account for user [" + username + "]");
        }
        String encryptPwd = accountVo.getAcntPwd();
        String salt = accountVo.getAcntId();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, encryptPwd, getName());
        if(salt!=null) info.setCredentialsSalt(ByteSource.Util.bytes(salt));
		return info;
	}


}

