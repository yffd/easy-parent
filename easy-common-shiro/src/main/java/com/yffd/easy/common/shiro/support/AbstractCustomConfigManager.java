package com.yffd.easy.common.shiro.support;

import java.util.LinkedHashMap;
import java.util.Set;

import com.yffd.easy.common.shiro.custom.conf.mgt.ICustomConfigManager;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月6日 下午6:17:41 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class AbstractCustomConfigManager implements ICustomConfigManager {

	@Override
	public long getSessionTimeout() {
		// 30分钟
		return 1800000;
	}

	@Override
	public long getSessionValidationInterval() {
		// 1小时
		return 3600000;
	}

	@Override
	public boolean kickoutAfter() {
		return false;
	}

	@Override
	public int getMaxSession() {
		return 1;
	}

	@Override
	public String getKickoutUrl() {
		return this.getLoginUrl();
	}

	@Override
	public abstract String getLoginUrl();

	@Override
	public abstract String getSuccessUrl();

	@Override
	public abstract String getUnauthorizedUrl();

	@Override
	public abstract LinkedHashMap<String, String> getFilterChainDefinitionMap();

	@Override
	public abstract String getCookieCipher();

	@Override
	public abstract String getCookieName();

	@Override
	public int getCookieMaxAge() {
		// 30天
		return 2592000;
	}

	@Override
	public abstract AccountInfoVo getAccountInfoVo(String identityId);

	@Override
	public abstract Set<String> getRoleCodes(String identityId);

	@Override
	public abstract Set<String> getPmsCodes(String identityId);

	@Override
	public int getDbNum() {
		return 0;
	}
	

}

