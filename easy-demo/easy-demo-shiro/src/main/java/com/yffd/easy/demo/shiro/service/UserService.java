package com.yffd.easy.demo.shiro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.demo.shiro.dao.IUserDao;
import com.yffd.easy.demo.shiro.model.User;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 上午10:47:59 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UserService {

	@Autowired
	private IUserDao userDao;
	
	public List<User> findList(User user) {
		return userDao.selectListBy(user, null, null, null);
	}
	
	public User findOne(User user) {
		return userDao.selectOneBy(user, null);
	}
}

