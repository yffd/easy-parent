package com.yffd.easy.uumc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uumc.pojo.entity.UumcRelationRoleUserEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时22分05秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcRelationRoleUserService extends UupmBaseService<UumcRelationRoleUserEntity> {

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveCfg(String userCode, List<String> roleCodeList, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(userCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		// 先删除已有的关联
		UumcRelationRoleUserEntity relationEntity = new UumcRelationRoleUserEntity();
		relationEntity.setUserCode(userCode);
		int num = this.delete(relationEntity, null);
		if (null == roleCodeList || roleCodeList.size() == 0) return num;
		// 添加关联
		List<UumcRelationRoleUserEntity> entityList = new ArrayList<>();
		for (String roleCode : roleCodeList) {
			UumcRelationRoleUserEntity entity = new UumcRelationRoleUserEntity();
			entity.setRoleCode(roleCode);
			entity.setUserCode(userCode);
			entityList.add(entity);
		}
		return this.save(entityList, loginInfo);
	}
	
	public Set<String> findRoleCode(String userCode) {
		if (EasyStringCheckUtils.isEmpty(userCode)) return null;
		UumcRelationRoleUserEntity relationEntity = new UumcRelationRoleUserEntity();
		relationEntity.setUserCode(userCode);
		List<UumcRelationRoleUserEntity> list = this.findList(relationEntity, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UumcRelationRoleUserEntity tmp : list) {
			codes.add(tmp.getRoleCode());
		}
		return codes;
	}
	
	public int delByUserCode(String userCode) {
		if (EasyStringCheckUtils.isEmpty(userCode)) return 0;
		UumcRelationRoleUserEntity entity = new UumcRelationRoleUserEntity();
		entity.setUserCode(userCode);
		return this.delete(entity, null);
	}
	
}
