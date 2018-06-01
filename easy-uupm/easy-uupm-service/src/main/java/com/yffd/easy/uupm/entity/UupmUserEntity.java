/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * 
 * @Description  用户信息.
 * @Date		 2018年2月1日 上午10:04:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmUserEntity extends UupmBasePojo {
	
	private static final long serialVersionUID = 1L;
	
	private String userCode;		//用户编号
	private String userName;		//用户名称
	private String orgCode;			//机构编号
	private String accountId;		//账号ID
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}