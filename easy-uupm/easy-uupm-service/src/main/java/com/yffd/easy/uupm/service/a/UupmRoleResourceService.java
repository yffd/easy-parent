package com.yffd.easy.uupm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.dao.UupmRoleResourceDao;
import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月05日 16时46分25秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmRoleResourceService extends UupmBaseService<UupmRoleResourceEntity> {

	@Autowired
	private UupmRoleResourceDao uupmRoleResourceDao;
	
	@Override
	protected IMybatisCommonDao<UupmRoleResourceEntity> getBindDao() {
		return uupmRoleResourceDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveRoleResource(String roleCode, List<String> rsCodesList, LoginInfo loginInfo) {
		this.delByRoleCode(roleCode, loginInfo);
		List<UupmRoleResourceEntity> modelList = new ArrayList<UupmRoleResourceEntity>();
		for(String rsCode : rsCodesList) {
			UupmRoleResourceEntity model = new UupmRoleResourceEntity();
			model.setRoleCode(roleCode);
			model.setRsCode(rsCode);
			modelList.add(model);
		}
		return this.save(modelList, loginInfo);
	}
	
	public void delByRoleCode(String roleCode, LoginInfo loginInfo) {
		UupmRoleResourceEntity entity = new UupmRoleResourceEntity();
		entity.setRoleCode(roleCode);
		this.delete(entity, loginInfo);
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
	
	public Set<String> findRsCodesByRoleCode(String roleCode, LoginInfo loginInfo) {
		UupmRoleResourceEntity entity = new UupmRoleResourceEntity();
		entity.setRoleCode(roleCode);
		List<UupmRoleResourceEntity> listResult = this.findList(entity, loginInfo);
		Set<String> rsCodes = new HashSet<String>();
		for(UupmRoleResourceEntity model : listResult) {
			rsCodes.add(model.getRsCode());
		}
		return rsCodes;
	}
	
	public int delByRsCodes(Set<String> rsCodes) {
		return this.uupmRoleResourceDao.delByRsCodes(rsCodes);
	}
}
