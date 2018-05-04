package com.yffd.easy.framework.web.model;

import java.io.Serializable;
import java.util.Set;

import com.yffd.easy.common.core.pojo.IPOJO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月7日 下午1:59:54 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class LoginInfo implements IPOJO, Serializable {

	private static final long serialVersionUID = -8544013546779025925L;
	private String tenantCode;
	private String userCode;
	private String userName;
	private String orgCode;
	private String orgName;
	private Set<String> roles;
	private Set<String> resources;
	
	private String accountId;
	private String accountPwd;
	private String accountSalt;
	private String accountStatus;
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public Set<String> getResources() {
		return resources;
	}
	public void setResources(Set<String> resources) {
		this.resources = resources;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	public String getAccountSalt() {
		return accountSalt;
	}
	public void setAccountSalt(String accountSalt) {
		this.accountSalt = accountSalt;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
}

