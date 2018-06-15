package com.yffd.easy.uumc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uumc.exception.UumcBizException;
import com.yffd.easy.uumc.pojo.entity.UumcPermissionEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时21分22秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcPermissionService extends UupmBaseService<UumcPermissionEntity> {
	@Autowired
	private UumcRelationRolePmsService uumcRelationRolePmsService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int savePermission(String appCode, List<String> rsCodeList, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(appCode)) throw UumcBizException.BIZ_PARAMS_ERROR();
		// 删除已有关联
		UumcPermissionEntity relationEentity = new UumcPermissionEntity();
		relationEentity.setAppCode(appCode);
		int delNum = this.delete(relationEentity, null);
		if (null == rsCodeList || rsCodeList.size() == 0) return delNum;
		// 添加关联
		List<UumcPermissionEntity> entityList = new ArrayList<>();
		for (String rsCode : rsCodeList) {
			UumcPermissionEntity entity = new UumcPermissionEntity();
			entity.setPmsName(appCode + ":" + rsCode);
			entity.setPmsCode(appCode + "-" + rsCode);
			entity.setAppCode(appCode);
			entity.setRsCode(rsCode);
			entityList.add(entity);
		}
		if (entityList.size()==0) return 0;
		return this.save(entityList, loginInfo);
	}
	
	public Set<String> findHavePmsForRsCode(String appCode) {
		UumcPermissionEntity entity = new UumcPermissionEntity();
		entity.setAppCode(appCode);
		List<UumcPermissionEntity> list = this.findList(entity, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UumcPermissionEntity rs : list) {
			codes.add(rs.getRsCode());
		}
		return codes;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByPmsCode(String pmsCode) {
		if (EasyStringCheckUtils.isEmpty(pmsCode)) throw UumcBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除权限
		UumcPermissionEntity entity = new UumcPermissionEntity();
		entity.setPmsCode(pmsCode);
		int num = this.delete(entity, null);
		// 删除关联关系：权限-角色
		if (!EasyStringCheckUtils.isEmpty(pmsCode)) this.uumcRelationRolePmsService.delByPmsCode(pmsCode);
		return num;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByPmsCode(Set<String> pmsCodes) {
		if (null == pmsCodes || pmsCodes.size() == 0) throw UumcBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除权限
		int num = this.deleteByProps("pmsCodeIter", pmsCodes);
		// 删除关联关系：权限-角色
		if (null != pmsCodes && pmsCodes.size() > 0) this.uumcRelationRolePmsService.delByPmsCode(pmsCodes);
		return num;
	}
	
}
