package com.yffd.easy.demo.shiro.custom.token;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午3:32:56 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = 5836978948490970891L;
	
	public static final String ACCOUNT_STATUS_ACTIVE = "A";		// 有效
	public static final String ACCOUNT_STATUS_INACTIVE = "I";	// 无效
	private String accountId;		// 账号ID
	private String accountPwd;		// 账号密码
	private String accountStatus;	// 账号状态
	private String salt;			// 账号加密的盐
	
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

