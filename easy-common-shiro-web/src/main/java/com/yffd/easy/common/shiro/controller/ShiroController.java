package com.yffd.easy.common.shiro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yffd.easy.common.shiro.custom.token.CustomAccountToken;
import com.yffd.easy.common.shiro.support.constants.ShiroConstants;

@Controller
@RequestMapping("/shiro")
public class ShiroController {
	
//	@Autowired
//	private ShiroService shiroService;
	
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "value12345");
//		shiroService.testMethod();
		return "redirect:/jsp/shiro/list.jsp";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse resp){
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if(null!=session) {
			System.out.println(">>>>>>>>>>>>>>>>sessionId:" + session.getId());
		} else {
			System.out.println(">>>>>>>>>>>>>>>>session kong");
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>currentUser.isAuthenticated():" + currentUser.isAuthenticated());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>currentUser.isRemembered():" + currentUser.isRemembered());
		if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
			CustomAccountToken token = new CustomAccountToken(username, password);
            // rememberme
            token.setRememberMe(true);
            try {
            	System.out.println("1. " + token.hashCode());
            	// 执行登录. 
                currentUser.login(token);
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("登录失败: " + ae.getMessage());
            	try {
            		req.setAttribute("msg", "登录失败");
					req.getRequestDispatcher("/views/shiro/login.jsp").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
            	return null;
//            	return "redirect:/views/shiro/login.jsp";
            }
        }
		String loginMsg = (String) req.getAttribute(ShiroConstants.ATTRIBUTE_KEY_LOGIN_FAILURE);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>:" + loginMsg);
		return "redirect:/views/shiro/list.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:/views/shiro/login.jsp";
	}
}
