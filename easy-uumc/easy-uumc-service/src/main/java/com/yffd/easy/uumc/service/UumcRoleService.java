package com.yffd.easy.uumc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.uumc.exception.UumcBizException;
import com.yffd.easy.uumc.pojo.entity.UumcRoleEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时22分43秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcRoleService extends UupmBaseService<UumcRoleEntity> {

	@Autowired
	private UumcRelationRolePmsService uumcRelationRolePmsService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByRoleCode(String roleCode) {
		if (EasyStringCheckUtils.isEmpty(roleCode)) throw UumcBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除角色
		UumcRoleEntity entity = new UumcRoleEntity();
		entity.setRoleCode(roleCode);
		int num = this.delete(entity, null);
		// 删除关联关系：角色-权限
		if (!EasyStringCheckUtils.isEmpty(roleCode)) this.uumcRelationRolePmsService.delByRoleCode(roleCode);
		return num;
	}
}
