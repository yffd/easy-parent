package com.yffd.easy.uupm.exception;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.framework.common.exception.CommonBizException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月7日 下午3:03:16 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmBizException extends CommonBizException {
	private static final long serialVersionUID = 1L;

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

