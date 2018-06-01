package com.yffd.easy.uupm.pojo.vo;

import java.util.Set;

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
	private String userCode;
	private String userName;
	private String orgCode;
	private String orgName;
	
	private String accountId;
	
	private Set<String> roleCodes;
	private Set<String> rsCodes;
	
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
	public Set<String> getRoleCodes() {
		return roleCodes;
	}
	public void setRoleCodes(Set<String> roleCodes) {
		this.roleCodes = roleCodes;
	}
	public Set<String> getRsCodes() {
		return rsCodes;
	}
	public void setRsCodes(Set<String> rsCodes) {
		this.rsCodes = rsCodes;
	}
	
}

