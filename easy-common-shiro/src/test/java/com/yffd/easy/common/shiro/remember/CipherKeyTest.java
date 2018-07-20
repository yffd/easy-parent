package com.yffd.easy.common.shiro.remember;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月6日 下午5:51:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CipherKeyTest {

	public static void main(String[] args) {
		byte[] k1 = org.apache.shiro.codec.Base64.decode("qweasdzxc123");
		System.out.println(k1);
		System.out.println();
		
		String kk1 = org.apache.shiro.codec.Base64.encodeToString(k1);
		System.out.println(kk1);
	}
}

