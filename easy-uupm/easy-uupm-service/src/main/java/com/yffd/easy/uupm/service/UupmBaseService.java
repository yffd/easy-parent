package com.yffd.easy.uupm.service;

import java.util.Date;

import org.slf4j.Logger;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.persist.entity.CommonEntity;
import com.yffd.easy.framework.common.service.impl.CommonServiceImpl;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

public abstract class UupmBaseService<POJO> extends CommonServiceImpl<POJO> {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UupmBaseService.class);
	
	@Override
	public void beforeSetPropertiesForQuery(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTenantCode()))
				model.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(model.getTenantCode())) 
				LOG.warn("租户信息未指定");
		}
	}

	@Override
	public void beforeSetPropertiesForAdd(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			model.setVersion(0);
			model.setDelFlag("0");
			model.setCreateTime(new Date());
			if(null!=loginInfo) model.setCreateBy(loginInfo.getUserCode());
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTenantCode()))
				model.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(model.getTenantCode())) 
				LOG.warn("租户信息未指定");
		} else if(pojo instanceof CommonEntity) {
			CommonEntity model = (CommonEntity) pojo;
			model.setVersion(0);
			model.setDelFlag("0");
			model.setCreateTime(new Date());
			model.setCreateBy(loginInfo.getUserCode());
		}
	}

	@Override
	public void beforeSetPropertiesForUpdate(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			model.setUpdateTime(new Date());
			if(null!=loginInfo) model.setCreateBy(loginInfo.getUserCode());
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTenantCode()))
				model.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(model.getTenantCode())) 
				LOG.warn("租户信息未指定");
		} else if(pojo instanceof CommonEntity) {
			CommonEntity model = (CommonEntity) pojo;
			model.setUpdateTime(new Date());
			model.setUpdateBy(loginInfo.getUserCode());
		}
	}

	@Override
	public void beforeSetPropertiesForDelete(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTenantCode()))
				model.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(model.getTenantCode())) 
				LOG.warn("租户信息未指定");
		}
	}
	
	public void beforeSetPropertiesForQuery(UupmBasePojo pojo, LoginInfo loginInfo) {
		if(null!=loginInfo && EasyStringCheckUtils.isEmpty(pojo.getTenantCode()))
			pojo.setTenantCode(loginInfo.getTenantCode());
		if(EasyStringCheckUtils.isEmpty(pojo.getTenantCode())) 
			LOG.warn("租户信息未指定");
	}

}
