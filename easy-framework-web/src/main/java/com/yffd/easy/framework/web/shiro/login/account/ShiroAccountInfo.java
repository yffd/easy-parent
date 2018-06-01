package com.yffd.easy.framework.web.shiro.login.account;

import java.io.Serializable;

import com.yffd.easy.framework.pojo.IPOJO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月28日 下午3:43:25 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ShiroAccountInfo implements IPOJO, Serializable {

	private static final long serialVersionUID = 1L;
	private String accountId;
	private String accountPwd;
	private String accountStatus;
	private String salt;
	
	public String getCredentialsSalt() {
		return accountId + salt;
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
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}

