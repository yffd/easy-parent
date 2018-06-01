package com.yffd.easy.framework.web.shiro.login.account;

import java.io.Serializable;
import java.util.Set;

import com.yffd.easy.framework.pojo.IPOJO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月7日 下午1:59:54 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ShiroUserInfo implements IPOJO, Serializable {

	private static final long serialVersionUID = 1L;

	private Set<String> roleCods;
	private Set<String> rsCodes;
	
	public Set<String> getRoleCods() {
		return roleCods;
	}
	public void setRoleCods(Set<String> roleCods) {
		this.roleCods = roleCods;
	}
	public Set<String> getRsCodes() {
		return rsCodes;
	}
	public void setRsCodes(Set<String> rsCodes) {
		this.rsCodes = rsCodes;
	}
	
}

