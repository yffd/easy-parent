package com.yffd.easy.uupm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.pojo.entity.UupmSecPermissionEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 上午11:24:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmSecPermissionService extends UupmBaseService<UupmSecPermissionEntity> {

	@Autowired
	private UupmSecRelationRolePmsService uupmSecRelationRolePmsService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int savePermission(String ttCode, List<String> rsCodesList, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(ttCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除已有关联
		UupmSecPermissionEntity relationEentity = new UupmSecPermissionEntity();
		relationEentity.setTtCode(ttCode);
		int delNum = this.delete(relationEentity, null);
		if (null == rsCodesList || rsCodesList.size() == 0) return delNum;
		// 添加关联
		List<UupmSecPermissionEntity> entityList = new ArrayList<>();
		for (String rsCode : rsCodesList) {
			UupmSecPermissionEntity entity = new UupmSecPermissionEntity();
			entity.setTtCode(ttCode);
			entity.setRsCode(rsCode);
			entity.setPmsCode(ttCode + ":" + rsCode);
			entityList.add(entity);
		}
		if (entityList.size()==0) return 0;
		return this.save(entityList, loginInfo);
	}
	
	public Set<String> findPmsRsCodes(String ttCode) {
		if (EasyStringCheckUtils.isEmpty(ttCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		UupmSecPermissionEntity entity = new UupmSecPermissionEntity();
		entity.setTtCode(ttCode);
		List<UupmSecPermissionEntity> list = this.findList(entity, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UupmSecPermissionEntity rs : list) {
			codes.add(rs.getRsCode());
		}
		return codes;
	}
//	public Set<String> findPmsCodes(String rsCode) {
//		if(EasyStringCheckUtils.isEmpty(rsCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
//		UupmSecPermissionEntity entity = new UupmSecPermissionEntity();
//		entity.setRsCode(rsCode);
//		List<UupmSecPermissionEntity> list = this.findList(entity, null);
//		if(null==list || list.size()==0) return null;
//		Set<String> codes = new HashSet<String>();
//		for(UupmSecPermissionEntity tmp : list) {
//			codes.add(tmp.getPmsCode());
//		}
//		return codes;
//	}
	
//	public int delByTtCode(String ttCode) {
//		if (EasyStringCheckUtils.isEmpty(ttCode)) return 0;
//		UupmSecPermissionEntity entity = new UupmSecPermissionEntity();
//		entity.setTtCode(ttCode);
//		return this.delete(entity, null);
//	}
	
//	public int delByPmsCodes(Set<String> pmsCodes) {
//		return this.deleteByProps("pmsCodeIter", pmsCodes);
//	}
	
	public Set<String> findPmsCodes(Set<String> rsCodes) {
		if (null == rsCodes || rsCodes.size() == 0) return null;
		List<UupmSecPermissionEntity> list = this.selectListByProps("pmsCodeIter", rsCodes, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UupmSecPermissionEntity tmp : list) {
			codes.add(tmp.getPmsCode());
		}
		return codes;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByRsCodes(Set<String> rsCodes) {
		// 删除权限
		int num = this.deleteByProps("rsCodeIter", rsCodes);
		// 删除关联关系：权限-角色
		Set<String> pmsCodes = this.findPmsCodes(rsCodes);
		if (null != pmsCodes && pmsCodes.size() > 0) this.uupmSecRelationRolePmsService.delByPmsCodes(pmsCodes);
		return num;
	}
	
	
	
	
}

