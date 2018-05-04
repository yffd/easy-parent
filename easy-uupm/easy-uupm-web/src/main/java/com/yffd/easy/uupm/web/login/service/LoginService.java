package com.yffd.easy.uupm.web.login.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.web.login.service.ILoginService;
import com.yffd.easy.framework.web.model.LoginInfo;
import com.yffd.easy.uupm.service.UupmAccountService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月17日 下午3:40:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class LoginService implements ILoginService {

	@Autowired
	private UupmAccountService uupmAccountService;
	
	@Override
	public LoginInfo getLoginInfo(String accountId) {
		Map<String, Object> loginMap = this.uupmAccountService.findLoginInfo(accountId);
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setTenantCode((String) loginMap.get("tenantCode"));
		loginInfo.setUserCode((String) loginMap.get("userCode"));
		loginInfo.setOrgCode((String) loginMap.get("orgCode"));
		loginInfo.setOrgName((String) loginMap.get("orgName"));
		loginInfo.setRoles((Set<String>) loginMap.get("roles"));
		loginInfo.setResources((Set<String>) loginMap.get("resources"));
		loginInfo.setAccountId((String) loginMap.get("accountId"));
		loginInfo.setAccountPwd((String) loginMap.get("accountPwd"));
		loginInfo.setAccountSalt((String) loginMap.get("accountSalt"));
		loginInfo.setAccountStatus((String) loginMap.get("accountStatus"));
		return loginInfo;
	}

}

