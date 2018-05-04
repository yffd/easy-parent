package com.yffd.easy.common.core.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年9月8日 下午3:26:01 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class RandomUtils {
	public static final String CHARACTER_ALL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";
	public static final String CHARACTER_DEFULT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String CHARACTER_LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String CHARACTER_NUMBER = "0123456789";
	public static final String CHARACTER_SYMBOL = "!@#$%^&*()_+";
	
	private static SecureRandom random = new SecureRandom();
	
    /**
     * 
     * 私有构造方法,将该工具类设为单例模式.
     */
    private RandomUtils() {
    }

    /**
	 * 
	 * generateWithAll:返回一个定长的随机字符串--所有可见字符，数字、英文大小写字母、特殊字符等. <br/>
	 * @Date	2017年5月18日 上午11:39:31 <br/>
	 * @author  zhangST
	 * @param length	生成随机数的长度
	 * @return			如果length小于等于0，返回null
	 */
	public static String generateWithAll(int length) {
		return generator(CHARACTER_ALL, length);
	}
	
	/**
	 * 
	 * generateWithDefault:返回一个定长的随机字符串--数字、英文大小写字母. <br/>
	 * @Date	2017年5月18日 上午11:39:31 <br/>
	 * @author  zhangST
	 * @param length	生成随机数的长度
	 * @return			如果length小于等于0，返回null
	 */
	public static String generateWithDefault(int length) {
		return generator(CHARACTER_DEFULT, length);
	}
	
	/**
	 * 
	 * generateWithLetter:返回一个定长的随机字符串--美国字母，包括大小写. <br/>
	 * @Date	2017年5月18日 上午11:39:31 <br/>
	 * @author  zhangST
	 * @param length	生成随机数的长度
	 * @return			如果length小于等于0，返回null
	 */
	public static String generateWithLetter(int length) {
		return generator(CHARACTER_LETTER, length);
	}
	
	/**
	 * 
	 * generateWithNumber:返回一个定长的随机字符串--数字. <br/>
	 * @Date	2017年5月18日 上午11:39:31 <br/>
	 * @author  zhangST
	 * @param length	生成随机数的长度
	 * @return			如果length小于等于0，返回null
	 */
	public static String generateWithNumber(int length) {
		return generator(CHARACTER_NUMBER, length);
	}
	
	/**
	 * 
	 * generateWithSymbol:返回一个定长的随机字符串--用键盘符号. <br/>
	 * @Date	2017年5月18日 上午11:39:31 <br/>
	 * @author  zhangST
	 * @param length	生成随机数的长度
	 * @return			如果length小于等于0，返回null
	 */
	public static String generateWithSymbol(int length) {
		return generator(CHARACTER_SYMBOL, length);
	}
	
	
	/**
	 * 
	 * generator:随机生成器. <br/>
	 * @Date	2017年5月18日 上午11:36:17 <br/>
	 * @author  zhangST
	 * @param characters
	 * @param length
	 * @return
	 */
	public static String generator(String characters, int length) {
		if(length<=0) return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}
		return sb.toString();
	}
    
    /**
     * 
     * get32UUID:获取去掉横线的长度为32的UUID串. <br/>
     * @Date    2017年9月8日 下午3:14:57 <br/>
     * @author  zhangST
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 
     * get36UUID:获取带横线的长度为36的UUID串. <br/>
     * @Date    2017年9月8日 下午3:15:19 <br/>
     * @author  zhangST
     * @return
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }
    
    
}

