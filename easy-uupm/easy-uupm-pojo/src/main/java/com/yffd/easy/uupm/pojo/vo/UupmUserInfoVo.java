package com.yffd.easy.uupm.pojo.vo;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月23日 下午2:11:33 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmUserInfoVo extends UupmBasePojo {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String userCode;		//用户编号
	private String userName;		//用户名称
	private String orgCode;			//机构编号
	
	private String orgName;
	private String accountId;
	private String accountStatus;
	private String accountType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

}

