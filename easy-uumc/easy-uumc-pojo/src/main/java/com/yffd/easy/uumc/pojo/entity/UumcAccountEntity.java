package com.yffd.easy.uumc.pojo.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午1:55:40 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UumcAccountEntity extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String acntId;			// 账号ID
	private String acntPwd;			// 账号密码
	private String acntStatus;		// 账号状态
	private String acntType;		// 账号类型
	private String nickName; 		// 昵称
    private String salt; 			// 加密密码的盐
	public String getAcntId() {
		return acntId;
	}
	public void setAcntId(String acntId) {
		this.acntId = acntId;
	}
	public String getAcntPwd() {
		return acntPwd;
	}
	public void setAcntPwd(String acntPwd) {
		this.acntPwd = acntPwd;
	}
	public String getAcntStatus() {
		return acntStatus;
	}
	public void setAcntStatus(String acntStatus) {
		this.acntStatus = acntStatus;
	}
	public String getAcntType() {
		return acntType;
	}
	public void setAcntType(String acntType) {
		this.acntType = acntType;
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

