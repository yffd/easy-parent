package com.yffd.easy.common.security.service;

import java.util.Set;

import com.yffd.easy.common.security.vo.LoginAccountVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月22日 下午1:45:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public interface ISecurityService {

	LoginAccountVo getLoginAccountVo(String accountId);
	
	Set<String> getRoleCodes(String accountId);
	
	Set<String> getPmsCodes(String accountId);
}

