/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity.a;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * 
 * @Description  角色-资源关系信息.
 * @Date		 2018年2月1日 上午9:47:50 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmRoleResourceEntity extends UupmBasePojo {
	
	private static final long serialVersionUID = 1L;
	private String rsCode;			//功能编号
	private String roleCode;		//角色编号
	
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}