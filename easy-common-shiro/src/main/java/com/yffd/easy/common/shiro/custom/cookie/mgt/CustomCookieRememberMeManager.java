package com.yffd.easy.common.shiro.custom.cookie.mgt;

import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月9日 上午10:44:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomCookieRememberMeManager extends CookieRememberMeManager implements InitializingBean {

	private String cookieCipher;
	private String cookieName;
	private int cookieMaxAge;
	
	public void setCookieCipher(String cookieCipher) {
		this.cookieCipher = cookieCipher;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] bytes = org.apache.shiro.codec.Base64.decode(this.cookieCipher);
		super.setCipherKey(bytes);
		
		SimpleCookie cookie = new SimpleCookie();
		cookie.setName(cookieName);
		cookie.setMaxAge(cookieMaxAge);
		super.setCookie(cookie);
	}

}

