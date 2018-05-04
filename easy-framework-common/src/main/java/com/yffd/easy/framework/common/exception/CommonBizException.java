package com.yffd.easy.framework.common.exception;

import com.yffd.easy.common.core.exception.EasyCommonException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月12日 上午11:09:22 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonBizException extends EasyCommonException {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = -6877199237574806153L;

	public static final EasyCommonException newInstance(String msg) {
		return new EasyCommonException("5B0101", msg);
	}
	
	public static final EasyCommonException newInstance(String msg, Throwable cause) {
		return new EasyCommonException("5B0101", msg, cause);
	}
	
	/**
	 * 业务参数为空
	 * @Date	2018年3月19日 下午2:02:20 <br/>
	 * @author  zhangST
	 * @return
	 */
	public static final EasyCommonException BIZ_PARAMS_IS_EMPTY() {
		return new EasyCommonException("5B0102", "业务参数为空.");
	}
	
	/**
	 * 租户参数为空
	 * @Date	2018年3月19日 下午2:02:20 <br/>
	 * @author  zhangST
	 * @return
	 */
	public static final EasyCommonException BIZ_TENANT_IS_EMPTY() {
		return new EasyCommonException("5B0103", "租户参数为空.");
	}
	
}

