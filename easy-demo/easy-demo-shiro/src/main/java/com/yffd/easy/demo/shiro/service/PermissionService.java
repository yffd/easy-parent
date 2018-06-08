package com.yffd.easy.demo.shiro.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.demo.shiro.dao.IPermissionDao;
import com.yffd.easy.demo.shiro.model.Permission;
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
public class PermissionService {

	@Autowired
	private IPermissionDao permissionDao;
	
	public List<Permission> findList(Permission pms) {
		return permissionDao.selectListBy(pms, null, null, null);
	}
	
	public Permission findOne(Permission pms) {
		return permissionDao.selectOneBy(pms, null);
	}
	
	public Set<String> findPmsNames(String userName) {
		User user = new User();
		user.setUserName(userName);
		List<Permission> pmsList = this.permissionDao.selectPmsByUser(user);
		if(null==pmsList || pmsList.size()==0) return null;
		Set<String> ret = new HashSet<String>();
		for(Permission pms : pmsList) {
			ret.add(pms.getPmsName());
		}
		return ret;
	}
}

