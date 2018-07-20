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
		
		User u = userService.findUser("zhangsan");
		System.out.println(u.getId());
		System.out.println(u.getUserName());
		
	}
	
	@Test
	public void insertTwoTest() throws Exception {
		User u1 = new User();
		u1.setUserCode("lisi");
		u1.setUserName("李四");
		u1.setOrgCode("dept1");
		
		User u2 = new User();
		u2.setUserCode("wangwu");
		u2.setUserName("王五");
		u2.setOrgCode("dept2");
		
		this.userService.insertTwo(u1, u2);
		
	}
	
}

