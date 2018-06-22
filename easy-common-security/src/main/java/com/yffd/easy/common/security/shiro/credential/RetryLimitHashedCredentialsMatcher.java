package com.yffd.easy.common.security.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月8日 上午10:06:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	private static final Logger LOG = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
	public static final String HASH_ALGORITHM_NAME = "SHA-256";
	public static final boolean HEX_ENCODED = true;	// true means hex encoded, false means base64 encoded
	public static final int HASH_ITERATIONS = 512;
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro credential] : RetryLimitHashedCredentialsMatcher#doCredentialsMatch(%s, %s) =======", token, info));
//		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//		char[] password = (char[]) upToken.getCredentials();
//		if(null==password) {
//			if(LOG.isWarnEnabled()) LOG.warn("无密码登录！");
//			return true;
//		}
//		
//		SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo) info;
//		String encryptPwd = (String) simpleInfo.getCredentials();
//		ByteSource credentialsSalt = simpleInfo.getCredentialsSalt();
//		
//		if(LOG.isInfoEnabled()) LOG.info(String.format("password=%s, encryptPwd=%s, credentialsSalt=%s", new String(password), encryptPwd, credentialsSalt));
////		if(PasswordUtils.getMD5(new String(password)).equals(encryptPwd)) {
////			return true;
////		} else {
//////			throw new IncorrectCredentialsException("密码不匹配错误");
////		}
//		return false;
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

