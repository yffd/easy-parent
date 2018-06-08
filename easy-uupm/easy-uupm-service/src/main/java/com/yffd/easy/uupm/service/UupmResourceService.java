package com.yffd.easy.uupm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.uupm.entity.UupmResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 下午4:00:50 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
@Service
public class UupmResourceService extends UupmBaseService<UupmResourceEntity> {

	@Autowired
	private UupmSecPermissionService uupmSecPermissionService;
	
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
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByTreeId(String treeId) {
		if(EasyStringCheckUtils.isEmpty(treeId)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除资源
		UupmResourceEntity model = new UupmResourceEntity();
		model.setTreeId(treeId);
		int num = this.delete(model , null);	
		// 删除关联关系： 资源-权限
		Set<String> rsCodes = this.findRsCodesByTreeId(treeId);
		if(null!=rsCodes && rsCodes.size()>0) {
			this.uupmSecPermissionService.delCascadeByRsCodes(rsCodes);	
		}
		return num;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByRsCodes(Set<String> rsCodes) {
		// 删除资源
		int num = this.deleteByProps("rsCodeIter", rsCodes);
		// 删除关联关系： 资源-权限
		this.uupmSecPermissionService.delCascadeByRsCodes(rsCodes);
		return num;
	}
	
}
