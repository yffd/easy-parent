package com.yffd.easy.uupm.pojo.vo;

import java.util.Set;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月28日 下午2:29:48 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmLoginInfoVo extends UupmCommonVo {
	private static final long serialVersionUID = -2763900513852904512L;

	private String accountId;
	private String accountPwd;
	private String accountStatus;
	private String salt;
	private String userCode;
	private String userName;
	private String orgCode;
	private String orgName;
	private String dataPath;
	
	private Set<String> roleCodes;
	private Set<String> resourceCodes;
	
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
	public String getDataPath() {
		return dataPath;
	}
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	public Set<String> getRoleCodes() {
		return roleCodes;
	}
	public void setRoleCodes(Set<String> roleCodes) {
		this.roleCodes = roleCodes;
	}
	public Set<String> getResourceCodes() {
		return resourceCodes;
	}
	public void setResourceCodes(Set<String> resourceCodes) {
		this.resourceCodes = resourceCodes;
	}
	
}

