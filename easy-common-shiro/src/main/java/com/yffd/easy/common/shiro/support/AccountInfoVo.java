package com.yffd.easy.common.shiro.support;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月22日 下午1:45:30 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class AccountInfoVo implements Serializable {
	private static final long serialVersionUID = 5836978948490970891L;
	
	private String acntId;				// 账号ID
	private String acntPwd;				// 账号密码
	private boolean locked = false;		// true=账号锁定、false=账号激活
	
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
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
}

