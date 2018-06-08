package com.yffd.easy.uupm.service.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.entity.a.UupmRoleEntity;
import com.yffd.easy.uupm.entity.a.UupmRoleResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月04日 16时46分57秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmRoleService extends UupmBaseService<UupmRoleEntity> {

	@Autowired
	private UupmRoleResourceService uupmRoleResourceService;
	@Autowired
	private UupmUserRoleService uupmUserRoleService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delByRoleCode(String roleCode, LoginInfo loginInfo) {
		if(EasyStringCheckUtils.isEmpty(roleCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		UupmRoleEntity model = new UupmRoleEntity();
		model.setRoleCode(roleCode);
		int num = this.delete(model, loginInfo);	// 删除角色
		
		UupmRoleResourceEntity rrModel = new UupmRoleResourceEntity();
		rrModel.setRoleCode(roleCode);
		this.uupmRoleResourceService.delete(rrModel , loginInfo);		// 删除 角色-资源关联
		this.uupmUserRoleService.delByRoleCode(roleCode, loginInfo);	// 删除 角色-用户关联
		return num;
	}
}
