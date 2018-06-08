package com.yffd.easy.demo.shiro.dao;

import java.util.List;

import com.yffd.easy.demo.shiro.common.ICommonMapper;
import com.yffd.easy.demo.shiro.model.Role;
import com.yffd.easy.demo.shiro.model.User;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 上午9:56:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface IRoleDao extends ICommonMapper<Role> {

	List<Role> selectRoleByUser(User user);
}

