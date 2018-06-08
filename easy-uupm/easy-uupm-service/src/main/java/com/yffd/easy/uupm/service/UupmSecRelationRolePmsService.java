package com.yffd.easy.uupm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.entity.UupmSecRelationRolePms;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月05日 16时46分25秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmSecRelationRolePmsService extends UupmBaseService<UupmSecRelationRolePms> {

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveRoleResource(String ttCode, String roleCode, List<String> pmsCodes, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(ttCode) || EasyStringCheckUtils.isEmpty(roleCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		if (null == pmsCodes || pmsCodes.size() == 0) return 0;
		// 先删除已有的关联
		UupmSecRelationRolePms relationEntity = new UupmSecRelationRolePms();
		relationEntity.setTtCode(ttCode);
		relationEntity.setRoleCode(roleCode);
		this.delete(relationEntity, null);
		// 添加关联
		List<UupmSecRelationRolePms> entityList = new ArrayList<>();
		for (String pmsCode : pmsCodes) {
			UupmSecRelationRolePms entity = new UupmSecRelationRolePms();
			entity.setTtCode(ttCode);
			entity.setRoleCode(roleCode);
			entity.setPmsCode(pmsCode);
			entityList.add(entity);
		}
		return this.save(entityList, loginInfo);
	}
	
//	public Set<String> findPmsCodes(String roleCode, LoginInfo loginInfo) {
//		UupmSecRelationRolePms entity = new UupmSecRelationRolePms();
//		entity.setRoleCode(roleCode);
//		List<UupmSecRelationRolePms> list = this.findList(entity, loginInfo);
//		if(null==list || list.size()==0) return null;
//		Set<String> codes = new HashSet<String>();
//		for(UupmSecRelationRolePms tmp : list) {
//			codes.add(tmp.getPmsCode());
//		}
//		return codes;
//	}
	
//	public int delRelation(String ttCode, String roleCode) {
//		if (EasyStringCheckUtils.isEmpty(ttCode) || EasyStringCheckUtils.isEmpty(roleCode)) return 0;
//		UupmSecRelationRolePms entity = new UupmSecRelationRolePms();
//		entity.setTtCode(ttCode);
//		entity.setRoleCode(roleCode);
//		return this.delete(entity, null);
//	}
	
//	public Set<String> findRsCode(Set<String> pmsCodes) {
//		List<UupmRoleResourceEntity> resultList = this.findByRoleCodes(roleCodes);
//		if(null==resultList ||resultList.size()==0) return null;
//		Set<String> rsCodes = new HashSet<String>();
//		for(UupmRoleResourceEntity model : resultList) {
//			rsCodes.add(model.getRsCode());
//		}
//		return rsCodes;
//	}
//	
//	public Set<String> findRsCodesByRoleCode(String roleCode, LoginInfo loginInfo) {
//		UupmRoleResourceEntity entity = new UupmRoleResourceEntity();
//		entity.setRoleCode(roleCode);
//		List<UupmRoleResourceEntity> listResult = this.findList(entity, loginInfo);
//		Set<String> rsCodes = new HashSet<String>();
//		for(UupmRoleResourceEntity model : listResult) {
//			rsCodes.add(model.getRsCode());
//		}
//		return rsCodes;
//	}
//	
//	public int delByRsCodes(Set<String> rsCodes) {
//		return this.deleteByProps("rsCodeIter", rsCodes);
//	}
//	
//	public List<UupmRoleResourceEntity> findByRoleCodes(Set<String> roleCodes) {
//		return this.selectListByProps("roleCodeIter", roleCodes, null);
//	}
	
	
	public int delByPmsCodes(Set<String> pmsCodes) {
		if (null == pmsCodes || pmsCodes.size() == 0) return 0;
		return this.deleteByProps("pmsCodeIter", pmsCodes);
	}

}
