package com.yffd.easy.common.shiro.support.encrypt;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.yffd.easy.common.shiro.custom.credential.CustomHashedCredentialsMatcher;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月22日 下午2:17:13 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EncryptPasswordUtils {

	public static String encryPassword(String acntId, String acntPwd) {
		ByteSource salt = ByteSource.Util.bytes(acntId);
		SimpleHash hash = new SimpleHash(CustomHashedCredentialsMatcher.HASH_ALGORITHM_NAME, acntPwd, salt, CustomHashedCredentialsMatcher.HASH_ITERATIONS);
//		byte[] bytes = hash.getBytes();
//		if (RetryLimitHashedCredentialsMatcher.HEX_ENCODED) {
//			return Hex.encodeToString(bytes);
//        } else {
//            return Base64.encodeToString(bytes);
//        }
		return hash.toString();
	}
	
	public static void main(String[] args) {
		String str = EncryptPasswordUtils.encryPassword("test", "123123");
		System.out.println(str);
		System.out.println(str.length());
	}
}

