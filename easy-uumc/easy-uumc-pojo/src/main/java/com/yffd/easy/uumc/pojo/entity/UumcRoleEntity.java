package com.yffd.easy.uumc.pojo.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:00:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UumcRoleEntity extends CommonEntity {
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

