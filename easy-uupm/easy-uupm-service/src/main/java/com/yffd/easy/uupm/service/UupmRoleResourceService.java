package com.yffd.easy.uupm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.uupm.dao.UupmRoleResourceDao;
import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;
import com.yffd.easy.uupm.entity.UupmUserRoleEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月05日 16时46分25秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmRoleResourceService extends CommonSimpleServiceImpl<UupmRoleResourceEntity> {

	@Autowired
	private UupmRoleResourceDao uupmRoleResourceDao;
	
	@Override
	protected BakICommonExtDao<UupmRoleResourceEntity> getBindDao() {
		return uupmRoleResourceDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveRoleResource(String roleCode, List<UupmRoleResourceEntity> modelList) {
		this.delByRoleCode(roleCode);
		this.save(modelList);
	}
	
	public void delByRoleCode(String roleCode) {
		UupmRoleResourceEntity entity = new UupmRoleResourceEntity();
		entity.setRoleCode(roleCode);
		this.delete(entity);
	}
	
	public Set<String> findRsCode(String roleCode) {
		UupmRoleResourceEntity paramModel = new UupmRoleResourceEntity();
		paramModel.setRoleCode(roleCode);
		List<UupmRoleResourceEntity> resultList = this.findList(paramModel, null);
		if(null==resultList ||resultList.size()==0) return null;
		Set<String> rsCodes = new HashSet<String>();
		for(UupmRoleResourceEntity model : resultList) {
			rsCodes.add(model.getRsCode());
		}
		return rsCodes;
	}
	
	public Set<String> findRsCode(Set<String> roleCodes) {
		List<UupmRoleResourceEntity> resultList = this.uupmRoleResourceDao.findByRoleCodes(roleCodes);
		if(null==resultList ||resultList.size()==0) return null;
		Set<String> rsCodes = new HashSet<String>();
		for(UupmRoleResourceEntity model : resultList) {
			rsCodes.add(model.getRsCode());
		}
		return rsCodes;
	}
	
	public List<UupmRoleResourceEntity> findResources(List<UupmUserRoleEntity> userRoleList) {
		if(null==userRoleList || userRoleList.size()==0) return null;
		List<String> roleCodeList = new ArrayList<String>();
		for(UupmUserRoleEntity model : userRoleList) {
			roleCodeList.add(model.getRoleCode());
		}
		return this.uupmRoleResourceDao.findByResourceCodes(roleCodeList);
	}
}
