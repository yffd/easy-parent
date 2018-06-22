package com.yffd.easy.common.security.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.yffd.easy.common.security.shiro.credential.RetryLimitHashedCredentialsMatcher;

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
		SimpleHash hash = new SimpleHash(RetryLimitHashedCredentialsMatcher.HASH_ALGORITHM_NAME, acntPwd, salt, RetryLimitHashedCredentialsMatcher.HASH_ITERATIONS);
//		byte[] bytes = hash.getBytes();
//		if (RetryLimitHashedCredentialsMatcher.HEX_ENCODED) {
//			return Hex.encodeToString(bytes);
//        } else {
//            return Base64.encodeToString(bytes);
//        }
		return hash.toString();
	}
	
	public static void main(String[] args) {
		String str = EncryptPasswordUtils.encryPassword("zst", "qwezxcasd");
		System.out.println(str);
		System.out.println(str.length());
	}
}

