/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import java.util.Date;

/**
 * 
 * @Description  用户信息.
 * @Date		 2018年2月1日 上午10:04:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmAccountEntity extends UupmCommonEntity {
	
	private static final long serialVersionUID = 7097872211031842341L;
	
	private String tenantCode;		// 租户编号
	private String accountId;		// 账号ID
	private String accountPwd;		// 账号密码
	private String accountStatus;	// 账号状态：1=激活、0=冻结
	private String accountType;		// 账号类型：admin=管理员、defaul=默认
	private String nickName; 		// 昵称
    private String salt; 			// 加密密码的盐
    private String online; 			// 是否在线:YES、NO
    private String visitIp; 		// 访问IP
    private Integer visitCount; 	// 登录次数
    private Date visitFirst; 		// 第一次登录时间
    private Date visitPrevious; 	// 上一次登录时间
    private Date visitLast; 		// 最后一次登录时间
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
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
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getVisitIp() {
		return visitIp;
	}
	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
	public Date getVisitFirst() {
		return visitFirst;
	}
	public void setVisitFirst(Date visitFirst) {
		this.visitFirst = visitFirst;
	}
	public Date getVisitPrevious() {
		return visitPrevious;
	}
	public void setVisitPrevious(Date visitPrevious) {
		this.visitPrevious = visitPrevious;
	}
	public Date getVisitLast() {
		return visitLast;
	}
	public void setVisitLast(Date visitLast) {
		this.visitLast = visitLast;
	}
    
}