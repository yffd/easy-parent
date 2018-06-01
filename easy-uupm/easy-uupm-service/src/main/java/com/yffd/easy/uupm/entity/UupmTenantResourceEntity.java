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
	private String ttCode;		//租户编号
	private String rsCode;		//系统资源编号
	
	public String getTtCode() {
		return ttCode;
	}
	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	
}