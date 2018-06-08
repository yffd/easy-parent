package com.yffd.easy.demo.shiro.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.demo.shiro.dao.IRoleDao;
import com.yffd.easy.demo.shiro.model.Permission;
import com.yffd.easy.demo.shiro.model.Role;
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
public class RoleService {

	@Autowired
	private IRoleDao roleDao;
	
	public List<Role> findList(Role role) {
		return roleDao.selectListBy(role, null, null, null);
	}
	
	public Role findOne(Role role) {
		return roleDao.selectOneBy(role, null);
	}
	
	public Set<String> findRoleNames(String userName) {
		User user = new User();
		user.setUserName(userName);
		List<Role> roleList = this.roleDao.selectRoleByUser(user);
		if(null==roleList || roleList.size()==0) return null;
		Set<String> ret = new HashSet<String>();
		for(Role role : roleList) {
			ret.add(role.getRoleName());
		}
		return ret;
	}
}

