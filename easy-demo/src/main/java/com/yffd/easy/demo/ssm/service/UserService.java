package com.yffd.easy.demo.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.demo.ssm.dao.UserDao;
import com.yffd.easy.demo.ssm.domain.User;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月20日 上午9:48:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User findUser(String userCode) {
		return this.userDao.select(userCode);
	}
	
//	@Transactional(rollbackFor={Exception.class})
	public void insertTwo(User first, User second) throws Exception {
		int num1 = this.userDao.insert(first);
		System.out.println("num1:" + num1);
		
		if(true) throw new Exception("事务测试异常");
		
		int num2 = this.userDao.insert(second);
		System.out.println("num2:" + num2);
	}
}

