package com.yffd.easy.demo.shiro.custom.service;

import java.util.Set;

import com.yffd.easy.demo.shiro.custom.token.AccountInfo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午5:09:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ISecurityService {

	AccountInfo getAccountInfo(String accountId);
	
	Set<String> getRoleCodes(String accountId);
	
	Set<String> getPmsCodes(String accountId);
}

