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
import com.yffd.easy.uupm.dao.UupmTenantResourceDao;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月05日 15时04分21秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmTenantResourceService extends UupmBaseService<UupmTenantResourceEntity> {

	@Autowired
	private UupmTenantResourceDao uupmTenantResourceDao;
	
	@Override
	protected IMybatisCommonDao<UupmTenantResourceEntity> getBindDao() {
		return this.uupmTenantResourceDao;
	}

	/**
	 * 某租户所拥有的所有资源
	 * @Date	2018年5月23日 上午11:30:33 <br/>
	 * @author  zhangST
	 * @param tenantCode
	 * @return
	 */
	public List<UupmResourceEntity> findTenantResource(String tenantCode) {
		return this.uupmTenantResourceDao.selectTenantResource(tenantCode);
	}
	
	/**
	 * 查找某租户已拥有的资源编号
	 * @Date	2018年5月23日 上午11:30:08 <br/>
	 * @author  zhangST
	 * @param tenantCode
	 * @return
	 */
	public Set<String> findRsCodesByTenantCode(String tenantCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTenantCode(tenantCode);
		List<UupmTenantResourceEntity> list = this.findList(entity, null);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UupmTenantResourceEntity rs : list) {
			codes.add(rs.getRsCode());
		}
		return codes;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveTenantResource(String tenantCode, List<String> rsCodesList, LoginInfo loginInfo) {
		this.delByTenantCode(tenantCode);
		List<UupmTenantResourceEntity> modelList = new ArrayList<UupmTenantResourceEntity>();
		for(String rsCode : rsCodesList) {
			UupmTenantResourceEntity model = new UupmTenantResourceEntity();
			model.setTenantCode(tenantCode);
			model.setRsCode(rsCode);
			modelList.add(model);
		}
		if(modelList.size()==0) return 0;
		return this.save(modelList, loginInfo);
	}
	
	public int delByTenantCode(String tenantCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTenantCode(tenantCode);
		return this.delete(entity, null);
	}
	
	public int delByRsCodes(Set<String> rsCodes) {
		return this.uupmTenantResourceDao.delByRsCodes(rsCodes);
	}
	
}
