/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * 
 * @Description  用户-角色关系信息.
 * @Date		 2018年2月1日 上午9:59:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmUserRoleEntity extends UupmBasePojo {
	
	private static final long serialVersionUID = 1L;
	private String userCode;	//用户编号
	private String roleCode;	//角色编号
	
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