package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.pojo.entity.UupmSecRelationRolePms;
import com.yffd.easy.uupm.pojo.entity.UupmSecRelationRoleUser;
import com.yffd.easy.uupm.pojo.entity.UupmSecRoleEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 下午4:00:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
@Service
public class UupmSecRoleService extends UupmBaseService<UupmSecRoleEntity> {

	@Autowired
	private UupmSecRelationRolePmsService uupmSecRelationRolePmsService;
	@Autowired
	private UupmSecRelationRoleUserService uupmSecRelationRoleUserService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByRole(UupmSecRoleEntity role, LoginInfo loginInfo) {
		if (null == role || EasyStringCheckUtils.isEmpty(role.getTtCode())
				|| EasyStringCheckUtils.isEmpty(role.getRoleCode())) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除角色
		int num = this.delete(role, loginInfo);	
		// 删除关联关系：角色-权限
		UupmSecRelationRolePms relationPms = new UupmSecRelationRolePms();
		relationPms.setTtCode(role.getTtCode());
		relationPms.setRoleCode(role.getRoleCode());
		this.uupmSecRelationRolePmsService.delete(relationPms, loginInfo);
		// 删除关联关系：角色-用户
		UupmSecRelationRoleUser relationUser = new UupmSecRelationRoleUser();
		relationUser.setTtCode(role.getTtCode());
		relationUser.setRoleCode(role.getRoleCode());
		this.uupmSecRelationRoleUserService.delete(relationUser, loginInfo);
		return num;
	}
	
}
