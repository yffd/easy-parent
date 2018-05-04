package com.yffd.easy.uupm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
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
public class UupmUserRoleService extends CommonSimpleServiceImpl<UupmUserRoleEntity> {

	@Autowired
	private UupmUserRoleDao uupmUserRoleDao;
	
	@Override
	protected BakICommonExtDao<UupmUserRoleEntity> getBindDao() {
		return uupmUserRoleDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveUserRole(String tennantCode, String userCode, List<UupmUserRoleEntity> modelList) {
		this.delByUserCode(tennantCode, userCode);
		this.save(modelList);
	}
	
	public void delByUserCode(String tennantCode, String userCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tennantCode", tennantCode);
		paramMap.put("userCode", userCode);
		this.delete(null, paramMap);
	}
	
	public List<UupmUserRoleEntity> findRoles(UupmUserRoleEntity paramModel) {
		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getUserCode())) {}
		List<UupmUserRoleEntity> resultList = this.findList(paramModel, null);
		return resultList;
	}
//	public Set<String> findRoleCodes(UupmUserRoleModel paramModel) {
//		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getUserCode())) {}
//		List<UupmUserRoleModel> resultList = this.findList(paramModel, null);
//		if(null==resultList || resultList.size()==0) return null;
//		Set<String> roleCodes = new HashSet<String>();
//		for(UupmUserRoleModel model : resultList) {
//			roleCodes.add(model.getRoleCode());
//		}
//		return roleCodes;
//	}
	
}
