package com.yffd.easy.common.shiro.custom.realm;

import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.conf.mgt.ICustomConfigManager;
import com.yffd.easy.common.shiro.custom.token.CustomAccountToken;
import com.yffd.easy.common.shiro.support.AccountInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月22日 下午1:48:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class CustomSecurityRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(CustomSecurityRealm.class);
	
	private ICustomConfigManager customConfigManager;

	public void setCustomConfigManager(ICustomConfigManager customConfigManager) {
		this.customConfigManager = customConfigManager;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(LOG.isInfoEnabled()) LOG.info("========= custom security realm [授权] =========");
		if(principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String username = (String) getAvailablePrincipal(principals);
        Set<String> roleCodes = this.customConfigManager.getRoleCodes(username);
        Set<String> pmsCodes = this.customConfigManager.getPmsCodes(username);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleCodes);
        info.setStringPermissions(pmsCodes);
        return info;
	}

	// 认证信息，主要针对用户登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(LOG.isInfoEnabled()) LOG.info("========= custom security realm [认证] =========");
		CustomAccountToken accountToken = (CustomAccountToken) token;
        String acntId = accountToken.getAcntId();
        if(acntId == null) {
            throw new AccountException("账号名称(ID)不能为空.");
        }
        AccountInfoVo accountInfo = this.customConfigManager.getAccountInfoVo(acntId);
        if(null==accountInfo) {
            throw new UnknownAccountException("No account found for user [" + acntId + "]");
        }
        if(accountInfo.isLocked()) {
        	throw new LockedAccountException("Locked account for user [" + acntId + "]");
        }
        String encryptPwd = accountInfo.getAcntPwd();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(acntId, encryptPwd, getName());
        String salt = accountInfo.getAcntId();
        if(salt!=null) info.setCredentialsSalt(ByteSource.Util.bytes(salt));
		return info;
	}


}

