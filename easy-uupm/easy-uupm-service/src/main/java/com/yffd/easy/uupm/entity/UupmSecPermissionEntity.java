package com.yffd.easy.uupm.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 上午10:45:39 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmSecPermissionEntity extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String ttCode;		//租户编号
	private String rsCode;		//资源编号
	private String pmsCode;		//权限编号
	
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
	public String getPmsCode() {
		return pmsCode;
	}
	public void setPmsCode(String pmsCode) {
		this.pmsCode = pmsCode;
	}
	
}

