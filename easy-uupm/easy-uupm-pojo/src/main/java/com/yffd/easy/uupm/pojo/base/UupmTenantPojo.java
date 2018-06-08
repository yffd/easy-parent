package com.yffd.easy.uupm.pojo.base;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  自定义持久化类基类.
 * @Date		 2018年3月26日 上午11:44:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmTenantPojo extends CommonEntity {
	
	private static final long serialVersionUID = 1L;
	private String ttCode;	//租户编号
	
	public String getTtCode() {
		return ttCode;
	}
	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}
	
}

