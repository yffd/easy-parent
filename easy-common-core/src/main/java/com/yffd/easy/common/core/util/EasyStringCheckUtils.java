package com.yffd.easy.common.core.util;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月11日 上午11:53:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyStringCheckUtils {

	public static boolean isEmpty(String str) {
		return null==str || "".equals(str.trim());
	}
}

