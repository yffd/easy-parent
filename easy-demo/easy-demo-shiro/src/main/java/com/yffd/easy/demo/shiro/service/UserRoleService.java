package com.yffd.easy.demo.shiro.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.demo.shiro.dao.IUserRoleDao;
import com.yffd.easy.demo.shiro.model.UserRole;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 上午10:47:59 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UserRoleService {

	@Autowired
	private IUserRoleDao userRoleDao;
	
	public Set<String> findRoleIds(String userName) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userName);
		List<UserRole> list = userRoleDao.selectListBy(userRole , null, null, null);
		if(null==list || list.size()==0) return null;
		Set<String> roleIds = new HashSet<>();
		for(UserRole ur : list) {
			roleIds.add(ur.getRoleId());
		}
		return roleIds;
	}
	
}

