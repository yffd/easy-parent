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
			UupmBasePojo entity = (UupmBasePojo) pojo;
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(entity.getTenantCode()))
				entity.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(entity.getTenantCode())) 
				LOG.warn("租户信息未指定");
		}
	}

	@Override
	public void beforeSetPropertiesForAdd(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo entity = (UupmBasePojo) pojo;
			entity.setVersion(0);
			entity.setDelFlag("0");
			entity.setCreateTime(new Date());
			if(null!=loginInfo) entity.setCreateBy(loginInfo.getUserCode());
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(entity.getTenantCode()))
				entity.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(entity.getTenantCode())) 
				LOG.warn("租户信息未指定");
		} else if(pojo instanceof CommonEntity) {
			CommonEntity entity = (CommonEntity) pojo;
			entity.setVersion(0);
			entity.setDelFlag("0");
			entity.setCreateTime(new Date());
			entity.setCreateBy(loginInfo.getUserCode());
		}
	}

	@Override
	public void beforeSetPropertiesForUpdate(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo entity = (UupmBasePojo) pojo;
			entity.setUpdateTime(new Date());
			if(null!=loginInfo) entity.setCreateBy(loginInfo.getUserCode());
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(entity.getTenantCode()))
				entity.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(entity.getTenantCode())) 
				LOG.warn("租户信息未指定");
		} else if(pojo instanceof CommonEntity) {
			CommonEntity entity = (CommonEntity) pojo;
			entity.setUpdateTime(new Date());
			entity.setUpdateBy(loginInfo.getUserCode());
		}
	}

	@Override
	public void beforeSetPropertiesForDelete(POJO pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo entity = (UupmBasePojo) pojo;
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(entity.getTenantCode()))
				entity.setTenantCode(loginInfo.getTenantCode());
			if(EasyStringCheckUtils.isEmpty(entity.getTenantCode())) 
				LOG.warn("租户信息未指定");
		}
	}

}
