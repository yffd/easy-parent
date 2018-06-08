/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * 
 * @Description  角色信息.
 * @Date		 2018年2月1日 上午10:01:22 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmRoleEntity extends UupmBasePojo {
	
	private static final long serialVersionUID = 1L;
	private String roleCode;	//角色编号
	private String roleName;	//角色名称
	private String roleStatus;	//角色状态
	private String remark;		//备注
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
   
}