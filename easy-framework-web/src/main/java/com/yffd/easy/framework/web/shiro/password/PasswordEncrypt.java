package com.yffd.easy.framework.web.shiro.password;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月7日 下午5:33:20 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PasswordEncrypt {
	private static final RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	private static String algorithmName = "md5";
    private static int hashIterations = 2;
    
	public static String getEncryptPassword(String accountId, String accountPwd, String salt) {
		String credentialsSalt = accountId + salt;
		String newPassword = new SimpleHash(algorithmName,
				accountPwd,
				ByteSource.Util.bytes(credentialsSalt),
				hashIterations).toHex();
		return newPassword;
	}
	
	public static String getRandomSalt() {
		return randomNumberGenerator.nextBytes().toHex();
	}
	
}

