package com.yffd.easy.uupm.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.uupm.dao.UupmResourceDao;
import com.yffd.easy.uupm.entity.UupmResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月10日 11时31分01秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmResourceService extends UupmBaseService<UupmResourceEntity> {

	@Autowired
	private UupmTenantResourceService uupmTenantResourceService;
	
	@Autowired
	private UupmResourceDao uupmResourceDao;
	
	@Override
	protected IMybatisCommonDao<UupmResourceEntity> getBindDao() {
		return this.uupmResourceDao;
	}
	
	public Set<String> findRsCodesByTreeId(String treeId) {
		if(EasyStringCheckUtils.isEmpty(treeId)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		UupmResourceEntity model = new UupmResourceEntity();
		model.setTreeId(treeId);
		List<UupmResourceEntity> result = this.findList(model, null);
		if(null==result || result.size()==0) return null;
		Set<String> rsCodes = new HashSet<String>();
		for(UupmResourceEntity entity : result) {
			rsCodes.add(entity.getRsCode());
		}
		return rsCodes;
	}
	
	public Integer delByIds(String idStr) {
		if(EasyStringCheckUtils.isEmpty(idStr)) return 0;
		String[] idsArr = idStr.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		Set<String> ids = new HashSet<String>(idsList);
		return this.uupmResourceDao.delByIds(ids);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delByTreeId(String treeId) {
		if(EasyStringCheckUtils.isEmpty(treeId)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		Set<String> rsCodes = this.findRsCodesByTreeId(treeId);
		if(null!=rsCodes && rsCodes.size()>0) this.uupmTenantResourceService.delByRsCodes(rsCodes);
		
		UupmResourceEntity model = new UupmResourceEntity();
		model.setTreeId(treeId);
		int num = this.delete(model , null);
		
		return num;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delByRsCodes(Set<String> rsCodes) {
		int num = this.uupmResourceDao.delByRsCodes(rsCodes);
		this.uupmTenantResourceService.delByRsCodes(rsCodes);
		return num;
	}
	
}
