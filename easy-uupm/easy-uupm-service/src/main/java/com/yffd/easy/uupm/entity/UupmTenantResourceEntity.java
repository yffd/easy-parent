/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import com.yffd.easy.framework.common.persist.entity.CommonEntity;

/**
 * 
 * @Description  租户资源配置信息.
 * @Date		 2018年2月1日 上午10:01:22 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmTenantResourceEntity extends CommonEntity {
	
	private static final long serialVersionUID = 1L;
	private String tenantCode;	//租户编号
	private String rsCode;		//系统资源编号
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	
}