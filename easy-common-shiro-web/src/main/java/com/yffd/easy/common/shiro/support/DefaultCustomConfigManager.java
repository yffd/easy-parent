package com.yffd.easy.common.shiro.support;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月9日 上午11:26:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class DefaultCustomConfigManager extends AbstractCustomConfigManager {

	@Override
	public String getCookieCipher() {
		return "6AvVhmFLUs3KTA1Kprsdag==";
	}

	@Override
	public String getCookieName() {
		return "shiro-web";
	}

	@Override
    public String getLoginUrl() {
		return "/views/shiro/login.jsp";
	}
    
	@Override
    public String getSuccessUrl() {
    	return "/views/shiro/list.jsp";
	}
    
	@Override
    public String getUnauthorizedUrl() {
    	return "/views/shiro/unauthorized.jsp";
	}

	@Override
	public LinkedHashMap<String, String> getFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/favicon.ico", "anon");
		map.put("/logo.png", "anon");
		map.put(".jar", "anon");
        map.put("/views/shiro/login.jsp", "anon");
		map.put("/shiro/login", "captcha");
		map.put("/shiro/withPms", "permission[sys:add,sys:edit]");
		map.put("/shiro/withRole", "role[admin,gaokai]");
		map.put("/**", "kickout,authc");
		return map;
	}
	
	@Override
	public AccountInfoVo getAccountInfoVo(String identityId) {
		if("admin".equals(identityId)) {
			AccountInfoVo vo = new AccountInfoVo();
			vo.setAcntId("admin");
			vo.setAcntPwd("9c5a03e4289d36248c066533a90d0bd263dd53e86b28c05881b25f5e276420f0");
			return vo;
		} else if("test".equals(identityId)) {
			AccountInfoVo vo = new AccountInfoVo();
			vo.setAcntId("test");
			vo.setAcntPwd("9bcb7446afbe8678a6a3ee2d5246ec2fa1d50faa175fe04f1fc95130a063a895");
			return vo;
		}
		return null;
		
	}

	@Override
	public Set<String> getRoleCodes(String identityId) {
		if("admin".equals(identityId)) {
			Set<String> codes = new HashSet<>();
			codes.add("gaokai");
			codes.add("zhongkai");
			codes.add("chukai");
			codes.add("admin");
			codes.add("test");
			return codes;
		} else if("test".equals(identityId)) {
			Set<String> codes = new HashSet<>();
			codes.add("test");
			return codes;
		}
		return null;
		
	}

	@Override
	public Set<String> getPmsCodes(String identityId) {
		if("admin".equals(identityId)) {
			Set<String> codes = new HashSet<>();
			codes.add("sys:add");
			codes.add("sys:edit");
			codes.add("sys:del");
			codes.add("sys:view");
			codes.add("rs:view");
			codes.add("rs:edit");
			codes.add("rs:del");
			codes.add("rs:view");
			return codes;
		} else if("test".equals(identityId)) {
			Set<String> codes = new HashSet<>();
			codes.add("test:edit");
			codes.add("test:del");
			codes.add("test:view");
			return codes;
		}
		return null;
	}
	
	
	
}

