package com.yffd.easy.framework.web.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.framework.web.mvc.WebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月17日 上午11:13:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
public class LoginController extends WebController {

	@RequestMapping(value="/login")
	public RespData login(HttpServletRequest req, HttpServletResponse resp) {
		if("GET".equalsIgnoreCase(req.getMethod())) {
			try {
				resp.sendRedirect("/common/login.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		String errorMsg = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			errorMsg = "用户名/密码错误"; // 账户名认证错误
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			errorMsg = "用户名/密码错误"; // 账户认证错误
		} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
			errorMsg = "用户名/密码错误"; // 账户锁定
		} else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
			errorMsg = "用户名/密码错误"; // 密码认证错误
		} else if (exceptionClassName != null) {
			errorMsg = "错误提示：" + exceptionClassName;
		}
		if(null==errorMsg) {
			return this.successAjax();
		} else {
			return this.error(errorMsg);
		}
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public RespData logout() {
		if(null==this.getSession()) {
			return this.successAjax();
		}
		SecurityUtils.getSubject().logout();
		return this.successAjax();
	}
	
}

