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
import com.yffd.easy.uumc.pojo.entity.UumcRelationRolePmsEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时21分43秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcRelationRolePmsService extends UupmBaseService<UumcRelationRolePmsEntity> {

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveCfg(String roleCode, List<String> pmsCodeList, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(roleCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		// 先删除已有的关联
		UumcRelationRolePmsEntity relationEntity = new UumcRelationRolePmsEntity();
		relationEntity.setRoleCode(roleCode);
		int num = this.delete(relationEntity, null);
		if (null == pmsCodeList || pmsCodeList.size() == 0) return num;
		// 添加关联
		List<UumcRelationRolePmsEntity> entityList = new ArrayList<>();
		for (String pmsCode : pmsCodeList) {
			UumcRelationRolePmsEntity entity = new UumcRelationRolePmsEntity();
			entity.setRoleCode(roleCode);
			entity.setPmsCode(pmsCode);
			entityList.add(entity);
		}
		return this.save(entityList, loginInfo);
	}
	
	public Set<String> findPmsCode(String roleCode) {
		if (EasyStringCheckUtils.isEmpty(roleCode)) return null;
		UumcRelationRolePmsEntity relationEntity = new UumcRelationRolePmsEntity();
		relationEntity.setRoleCode(roleCode);
		List<UumcRelationRolePmsEntity> list = this.findList(relationEntity, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UumcRelationRolePmsEntity tmp : list) {
			codes.add(tmp.getPmsCode());
		}
		return codes;
	}
	
	public int delByPmsCode(String pmsCode) {
		if (EasyStringCheckUtils.isEmpty(pmsCode)) return 0;
		UumcRelationRolePmsEntity entity = new UumcRelationRolePmsEntity();
		entity.setPmsCode(pmsCode);
		return this.delete(entity, null);
	}
	
	public int delByPmsCode(Set<String> pmsCodes) {
		if (null == pmsCodes || pmsCodes.size() == 0) return 0;
		return this.deleteByProps("pmsCodeIter", pmsCodes);
	}
	
	public int delByRoleCode(String roleCode) {
		if (EasyStringCheckUtils.isEmpty(roleCode)) return 0;
		UumcRelationRolePmsEntity entity = new UumcRelationRolePmsEntity();
		entity.setRoleCode(roleCode);
		return this.delete(entity, null);
	}
}
