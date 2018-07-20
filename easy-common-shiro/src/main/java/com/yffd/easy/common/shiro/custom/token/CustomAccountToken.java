package com.yffd.easy.common.shiro.custom.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月2日 上午11:44:24 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomAccountToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;
	private String acntPwd;
	
	public CustomAccountToken(String acntId, String acntPwd) {
		super(acntId, acntPwd);
		this.acntPwd = acntPwd;
	}

	public String getAcntId() {
		return super.getUsername();
	}

	public String getAcntPwd() {
		return acntPwd;
	}
	
}

