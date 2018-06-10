package com.yffd.easy.uupm.pojo.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 下午4:09:01 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmSecRelationRolePms extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String ttCode;
	private String roleCode;
	private String pmsCode;
	
	public String getTtCode() {
		return ttCode;
	}
	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getPmsCode() {
		return pmsCode;
	}
	public void setPmsCode(String pmsCode) {
		this.pmsCode = pmsCode;
	}
	
}

