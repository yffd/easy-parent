package com.yffd.easy.uumc.web.controller.login;

import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.yffd.easy.common.shiro.support.AbstractCustomConfigManager;
import com.yffd.easy.common.shiro.support.AccountInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月9日 下午5:23:16 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class LoginConfigManager extends AbstractCustomConfigManager {

	@Override
	public String getLoginUrl() {
		return "/common/login.jsp";
	}

	@Override
	public String getSuccessUrl() {
		return "/common/index.jsp";
	}

	@Override
	public String getUnauthorizedUrl() {
		return "/common/msg/unauthorized.jsp";
	}

	@Override
	public LinkedHashMap<String, String> getFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/favicon.ico", "anon");
		map.put("/static/**", "anon");
		map.put("/Kaptcha.jpg", "anon");
		map.put("/common/error/*", "anon");
		map.put("/common/msg/*", "anon");
		map.put("/common/login.jsp", "anon");
		map.put("/uumc/login", "captcha");
		map.put("/common/index.jsp", "authc");
		map.put("/**", "kickout,authc");
		return map;
	}

	@Override
	public String getCookieCipher() {
		return "6AvVhmFLUs3KTA1Kprsdag==";
	}

	@Override
	public String getCookieName() {
		return "easy-uumc";
	}

	@Override
	public AccountInfoVo getAccountInfoVo(String identityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getRoleCodes(String identityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getPmsCodes(String identityId) {
		// TODO Auto-generated method stub
		return null;
	}

}

