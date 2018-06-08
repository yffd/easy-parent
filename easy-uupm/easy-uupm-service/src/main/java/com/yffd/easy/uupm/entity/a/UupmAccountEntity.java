/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity.a;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * 
 * @Description  用户信息.
 * @Date		 2018年2月1日 上午10:04:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmAccountEntity extends CommonEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String accountId;		// 账号ID
	private String accountPwd;		// 账号密码
	private String accountStatus;	// 账号状态
	private String accountType;		// 账号类型：tenant=租户、user=用户
	private String nickName; 		// 昵称
    private String salt; 			// 加密密码的盐
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
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
    
}