package com.yffd.easy.framework.web.shiro.login.account;

import java.io.Serializable;

import com.yffd.easy.framework.pojo.IPOJO;
import com.yffd.easy.framework.web.shiro.password.PasswordEncrypt;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月28日 下午3:43:25 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ShiroAccount implements IPOJO, Serializable {

	private static final long serialVersionUID = 1L;
	private String accountId;
	private String accountPwd;
	
	public ShiroAccount(String accountId, String accountPwd) {
		this.accountId = accountId;
		this.accountPwd = accountPwd;
	}
	
	public String getEncryptPwd() {
		return PasswordEncrypt.getEncryptPassword(accountPwd, getCredentialsSalt());
	}
	
	public String getCredentialsSalt() {
		return accountId + PasswordEncrypt.getRandomSalt();
	}
	
}

