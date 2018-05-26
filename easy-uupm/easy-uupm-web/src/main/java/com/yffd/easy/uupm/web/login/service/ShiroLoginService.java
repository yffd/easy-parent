package com.yffd.easy.uupm.web.login.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.web.login.service.ILoginService;
import com.yffd.easy.uupm.pojo.vo.UupmLoginInfoVo;
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
public class ShiroLoginService implements ILoginService {

	@Autowired
	private UupmAccountService uupmAccountService;
	
	@Override
	public LoginInfo getLoginInfo(String accountId) {
//		UupmLoginInfoVo loginInfoVo = this.uupmAccountService.findLoginInfo(accountId);
//		LoginInfo loginInfo = new LoginInfo();
//		loginInfo.setTenantCode((String) loginInfoVo.getTenantCode());
//		loginInfo.setUserCode((String) loginInfoVo.getUserCode());
//		loginInfo.setOrgCode((String) loginInfoVo.getOrgCode());
//		loginInfo.setOrgName((String) loginInfoVo.getOrgName());
//		loginInfo.setRoles((Set<String>) loginInfoVo.getRoleCodes());
//		loginInfo.setResources((Set<String>) loginInfoVo.getResourceCodes());
//		loginInfo.setAccountId((String) loginInfoVo.getAccountId());
//		loginInfo.setAccountPwd((String) loginInfoVo.getAccountPwd());
//		loginInfo.setAccountSalt((String) loginInfoVo.getSalt());
//		loginInfo.setAccountStatus((String) loginInfoVo.getAccountStatus());
//		return loginInfo;
		return null;
	}

}

