package com.yffd.easy.uupm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.dao.UupmUserRoleDao;
import com.yffd.easy.uupm.entity.UupmUserRoleEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月06日 13时19分44秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmUserRoleService extends UupmBaseService<UupmUserRoleEntity> {

	@Autowired
	private UupmUserRoleDao uupmUserRoleDao;
	
	@Override
	protected IMybatisCommonDao<UupmUserRoleEntity> getBindDao() {
		return uupmUserRoleDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveUserRole(String userCode, List<String> roleCodesList, LoginInfo loginInfo) {
		this.delByUserCode(userCode, loginInfo);
		List<UupmUserRoleEntity> modelList = new ArrayList<UupmUserRoleEntity>();
		for(String roleCode : roleCodesList) {
			UupmUserRoleEntity model = new UupmUserRoleEntity();
			model.setUserCode(userCode);
			model.setRoleCode(roleCode);
			modelList.add(model);
		}
		return this.save(modelList, loginInfo);
	}
	
	public void delByUserCode(String userCode, LoginInfo loginInfo) {
		UupmUserRoleEntity entity = new UupmUserRoleEntity();
		entity.setUserCode(userCode);
		this.delete(entity, loginInfo);
	}
	
	public int delByRoleCode(String roleCode, LoginInfo loginInfo) {
		if(EasyStringCheckUtils.isEmpty(roleCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		UupmUserRoleEntity model = new UupmUserRoleEntity();
		model.setRoleCode(roleCode);
		return this.delete(model, loginInfo);
	}
	
}
