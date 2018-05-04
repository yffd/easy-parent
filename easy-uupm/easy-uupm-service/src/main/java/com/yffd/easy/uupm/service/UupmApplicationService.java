package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.framework.core.exception.BizException;
import com.yffd.easy.uupm.dao.UupmApplicationDao;
import com.yffd.easy.uupm.entity.UupmApplicationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月06日 14时08分50秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmApplicationService extends CommonSimpleServiceImpl<UupmApplicationEntity> {

	@Autowired
	private UupmApplicationDao uupmApplicationDao;
	
	@Override
	protected BakICommonExtDao<UupmApplicationEntity> getBindDao() {
		return uupmApplicationDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveAppCfg(UupmApplicationEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTenantCode())) throw BizException.BIZ_TENANT_IS_EMPTY();
		this.deleteByAppCode(model.getAppCode());
		return this.save(model);
	}
	
	public int deleteByAppCode(String appCode) {
		if(EasyStringCheckUtils.isEmpty(appCode)) throw BizException.BIZ_TENANT_IS_EMPTY();
		UupmApplicationEntity model = new UupmApplicationEntity();
		model.setAppCode(appCode);
		return this.delete(model);
	}
}
