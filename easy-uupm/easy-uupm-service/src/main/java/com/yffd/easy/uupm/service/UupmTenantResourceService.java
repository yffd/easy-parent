package com.yffd.easy.uupm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.service.CommonService;
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
public class UupmTenantResourceService extends CommonService<UupmTenantResourceEntity> {

	@Autowired
	private UupmTenantResourceDao uupmTenantResourceDao;
	
	@Override
	protected IMybatisCommonDao<UupmTenantResourceEntity> getBindDao() {
		return this.uupmTenantResourceDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveTenantResource(String tenantCode, List<UupmTenantResourceEntity> modelList) {
		this.delByTenantCode(tenantCode);
		this.save(modelList);
	}
	
	public void delByTenantCode(String tenantCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTenantCode(tenantCode);
		this.getBindDao().delete(entity);
	}
	
	/**
	 * 某租户所拥有的所有资源
	 * @Date	2018年5月23日 上午11:30:33 <br/>
	 * @author  zhangST
	 * @param tenantCode
	 * @return
	 */
	public List<UupmResourceEntity> findResourceByTenantCode(String tenantCode) {
		List<UupmResourceEntity> list = this.uupmTenantResourceDao.findResourceForTenant(tenantCode);
		return list;
	}
	
	/**
	 * 查找某租户已拥有的资源编号
	 * @Date	2018年5月23日 上午11:30:08 <br/>
	 * @author  zhangST
	 * @param tenantCode
	 * @return
	 */
	public Set<String> findResourceCodesByTenantCode(String tenantCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTenantCode(tenantCode);
		List<UupmTenantResourceEntity> list = this.findList(entity);
		if(null==list || list.size()==0) return null;
		Set<String> codes = new HashSet<String>();
		for(UupmTenantResourceEntity rs : list) {
			codes.add(rs.getRsCode());
		}
		return codes;
	}
}
