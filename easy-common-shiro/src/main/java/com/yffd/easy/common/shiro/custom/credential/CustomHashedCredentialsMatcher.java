package com.yffd.easy.common.shiro.custom.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.token.CustomAccountToken;
import com.yffd.easy.common.shiro.support.encrypt.EncryptPasswordUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月8日 上午10:06:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {
	private static final Logger LOG = LoggerFactory.getLogger(CustomHashedCredentialsMatcher.class);
	public static final String HASH_ALGORITHM_NAME = "SHA-256";
	public static final boolean HEX_ENCODED = true;	// true means hex encoded, false means base64 encoded
	public static final int HASH_ITERATIONS = 512;
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom credential] : #doCredentialsMatch(%s, %s) =======", token, info));
		/**
		AccountTokenVo accountToken = (AccountTokenVo) token;
		String acntId = accountToken.getAcntId();
		String acntPwd = accountToken.getAcntPwd();
		if(null==acntPwd) {
			if(LOG.isWarnEnabled()) LOG.warn("无密码登录！");
			return false;
		}
		
		SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo) info;
		String encryptPwd = (String) simpleInfo.getCredentials();
//		ByteSource credentialsSalt = simpleInfo.getCredentialsSalt();
		
		if(EncryptPasswordUtils.encryPassword(acntId, acntPwd).equals(encryptPwd)) {
			if(LOG.isInfoEnabled()) LOG.info(String.format("登录成功，账户ID：%s", acntId));
			return true;
		} else {
			if(LOG.isInfoEnabled()) LOG.info(String.format("登录失败，账户ID：%s", acntId));
			throw new IncorrectCredentialsException("密码不匹配错误");
		}
		*/
		boolean isMatched = super.doCredentialsMatch(token, info);
		return isMatched;
	}

	@Override
	public String getHashAlgorithmName() {
		return HASH_ALGORITHM_NAME;
	}

	@Override
	public boolean isStoredCredentialsHexEncoded() {
		return HEX_ENCODED;
	}

	@Override
	public int getHashIterations() {
		return HASH_ITERATIONS;
	}
	
}

