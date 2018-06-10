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
public class UupmSecRelationRoleUser extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String ttCode;
	private String userCode;	//用户编号
	private String roleCode;	//角色编号
	
	public String getTtCode() {
		return ttCode;
	}
	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}

