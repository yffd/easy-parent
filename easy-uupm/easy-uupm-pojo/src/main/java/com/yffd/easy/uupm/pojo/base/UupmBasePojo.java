package com.yffd.easy.uupm.pojo.base;

import com.yffd.easy.framework.common.persist.entity.CommonEntity;

/**
 * @Description  自定义持久化类基类.
 * @Date		 2018年3月26日 上午11:44:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmBasePojo extends CommonEntity {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String tenantCode;	//租户编号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	
}

