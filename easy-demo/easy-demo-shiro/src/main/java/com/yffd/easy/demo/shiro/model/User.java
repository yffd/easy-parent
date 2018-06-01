package com.yffd.easy.demo.shiro.model;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月29日 下午5:09:13 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class User {
	private String userName;
	private String password;
	private String salt;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

}

