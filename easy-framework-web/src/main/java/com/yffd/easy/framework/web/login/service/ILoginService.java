package com.yffd.easy.framework.web.login.service;

import com.yffd.easy.framework.web.model.LoginInfo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月17日 上午11:17:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ILoginService {

	public LoginInfo getLoginInfo(String accountId);
}

