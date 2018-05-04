package com.yffd.easy.framework.web.exception;

import com.yffd.easy.common.core.exception.EasyCommonException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月12日 下午4:41:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WebException extends EasyCommonException {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = 6423665148877079307L;

	public static final EasyCommonException newInstance(String msg) {
		return new EasyCommonException("5C0001", msg);
	}
	
	public static final EasyCommonException newInstance(String msg, Throwable cause) {
		return new EasyCommonException("5C0001", msg, cause);
	}
	
}

