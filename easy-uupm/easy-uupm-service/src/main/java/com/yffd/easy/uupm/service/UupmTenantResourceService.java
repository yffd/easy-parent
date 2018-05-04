package com.yffd.easy.uupm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.uupm.dao.UupmTenantResourceDao;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;
import com.yffd.easy.uupm.pojo.vo.UupmTenantResourceVo;
import com.yffd.easy.uupm.pojo.vo.factory.UupmTenantResourceVoFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月05日 15时04分21秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmTenantResourceService extends CommonSimpleServiceImpl<UupmTenantResourceEntity> {

	@Autowired
	private UupmTenantResourceVoFactory uupmTenantResourceVoFactory;
	
	@Autowired
	private UupmTenantResourceDao uupmTenantResourceDao;
	
	@Override
	protected BakICommonExtDao<UupmTenantResourceEntity> getBindDao() {
		return this.uupmTenantResourceDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveTenantResource(String tenantCode, List<UupmTenantResourceEntity> modelList) {
		this.delByTenantCode(tenantCode);
		this.save(modelList);
	}
	
	public void delByTenantCode(String tenantCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantCode", tenantCode);
		this.delete(null, paramMap);
	}
	 
	public List<UupmTenantResourceVo> findTenantResource(String tenantCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantCode", tenantCode);
		List<Map<String, Object>> list = this.uupmTenantResourceDao.findTenantResource(paramMap);
		return uupmTenantResourceVoFactory.create(list);
	}
}
