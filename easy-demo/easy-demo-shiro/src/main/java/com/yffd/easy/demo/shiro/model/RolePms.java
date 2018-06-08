package com.yffd.easy.demo.shiro.model;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 上午10:21:32 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class RolePms implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String roleId;
	private String pmsId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPmsId() {
		return pmsId;
	}
	public void setPmsId(String pmsId) {
		this.pmsId = pmsId;
	}
	
}

