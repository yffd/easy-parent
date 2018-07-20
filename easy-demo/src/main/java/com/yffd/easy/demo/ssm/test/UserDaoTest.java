package com.yffd.easy.demo.ssm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.demo.javaconfig.spring.SpringJunitJavaConfig;
import com.yffd.easy.demo.ssm.dao.UserDao;
import com.yffd.easy.demo.ssm.domain.User;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月20日 下午4:05:54 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UserDaoTest extends SpringJunitJavaConfig {
	@Autowired
	private UserDao userDao;
	
	@Test
	public void selectTest() {
		User u = userDao.select("zhangsan");
		System.out.println(u.getId());
		System.out.println(u.getUserName());
	}
	
	@Test
	public void insertTest() {
		User u = new User();
		u.setUserCode("zhangsan");
		u.setUserName("张三");
		u.setOrgCode("kaifa");
		int result = this.userDao.insert(u);
		System.out.println(result);
	}
}

