package com.yffd.easy.demo.shiro.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.demo.shiro.custom.service.ISecurityService;
import com.yffd.easy.demo.shiro.custom.token.AccountInfo;
import com.yffd.easy.demo.shiro.model.User;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午6:20:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class AuthService implements ISecurityService {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public AccountInfo getAccountInfo(String accountId) {
		User user = new User();
		user.setUserName(accountId);
		User result = this.userService.findOne(user );
		AccountInfo info = new AccountInfo();
		info.setAccountPwd(result.getPassword());
		return info;
	}

	@Override
	public Set<String> getRoleCodes(String accountId) {
		return roleService.findRoleNames(accountId);
	}

	@Override
	public Set<String> getPmsCodes(String accountId) {
		return permissionService.findPmsNames(accountId);
	}

}

