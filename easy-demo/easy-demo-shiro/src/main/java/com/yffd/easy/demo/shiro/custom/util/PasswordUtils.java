package com.yffd.easy.demo.shiro.custom.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtils {
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 获取随机的数值
	 * @Date	2018年6月5日 下午3:26:40 <br/>
	 * @author  zhangST
	 * @param length
	 * @return
	 */
	public static String getRandom20(Integer length) {
		String result = "";
		int len = 20;
		if(null!=length && length>0) {
			len = length;
		}
		boolean[] bool = new boolean[len];
		int randInt = 0;
		for(int i=0;i<len;i++) {
			do {
				randInt = random.nextInt(len);
			} while (bool[randInt]);
			bool[randInt] = true;
			result += randInt;
		}
		return result;
	}
	
	/**
	 * MD5 加密
	 * @Date	2018年6月5日 下午3:44:12 <br/>
	 * @author  zhangST
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException("MD5转换异常", e);
		}
		byte[] byteArray = messageDigest.digest();  
		StringBuffer md5StrBuff = new StringBuffer();  
		for(int i=0;i<byteArray.length;i++) {
			if(Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
		}
		return md5StrBuff.toString();  
	}
	
	public static void main(String[] args) {
		String result = PasswordUtils.getRandom20(20);
		System.out.println(result.length());
		 
		String result1 = PasswordUtils.getMD5("qwe");
		System.out.println(result1);
		System.out.println(result1.length());
	}
	
}