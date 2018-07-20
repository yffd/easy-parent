package com.yffd.easy.demo.ssm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.demo.javaconfig.spring.DruidDataSourceJavaConfig;
import com.yffd.easy.demo.javaconfig.spring.SpringJunitJavaConfig;
import com.yffd.easy.demo.ssm.domain.User;
import com.yffd.easy.demo.ssm.service.UserService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月20日 上午9:54:56 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UserServiceTest extends SpringJunitJavaConfig {

	@Autowired
	private UserService userService;
	
	@Test
	public void findUserTest() {
//		ApplicationContext actx = new AnnotationConfigApplicationContext(SpringJavaConfig.class);
//		UserService userService = actx.getBean(UserService.class);
		
		User u = userService.findUser("test");
		System.out.println(u.getId());
		System.out.println(u.getUserName());
		
	}
	
}

