package com.yffd.easy.uupm.service.a;

import java.util.Date;

import org.slf4j.Logger;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.service.impl.DefaultServiceImpl;
import com.yffd.easy.framework.pojo.entity.CommonEntity;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

public abstract class UupmBaseService<E> extends DefaultServiceImpl<E> {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UupmBaseService.class);
	
	@Override
	protected void beforeSetPropertiesForQuery(Object pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTtCode()))
				model.setTtCode(loginInfo.getTtCode());
			if(EasyStringCheckUtils.isEmpty(model.getTtCode())) 
				LOG.warn("租户信息未指定");
		}
	}

	@Override
	protected void beforeSetPropertiesForAdd(Object pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			model.setVersion(0);
			model.setDelFlag("0");
			model.setCreateTime(new Date());
			if(null!=loginInfo) model.setCreateBy(loginInfo.getUserCode());
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTtCode()))
				model.setTtCode(loginInfo.getTtCode());
			if(EasyStringCheckUtils.isEmpty(model.getTtCode())) 
				LOG.warn("租户信息未指定");
		} else if(pojo instanceof CommonEntity) {
			super.beforeSetPropertiesForAdd(pojo, loginInfo);
		}
	}

	@Override
	protected void beforeSetPropertiesForUpdate(Object pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			model.setUpdateTime(new Date());
			if(null!=loginInfo) model.setCreateBy(loginInfo.getUserCode());
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTtCode()))
				model.setTtCode(loginInfo.getTtCode());
			if(EasyStringCheckUtils.isEmpty(model.getTtCode())) 
				LOG.warn("租户信息未指定");
		} else if(pojo instanceof CommonEntity) {
			super.beforeSetPropertiesForUpdate(pojo, loginInfo);
		}
	}

	@Override
	protected void beforeSetPropertiesForDelete(Object pojo, LoginInfo loginInfo) {
		if(null==pojo) return;
		if(pojo instanceof UupmBasePojo) {
			UupmBasePojo model = (UupmBasePojo) pojo;
			if(null!=loginInfo && EasyStringCheckUtils.isEmpty(model.getTtCode()))
				model.setTtCode(loginInfo.getTtCode());
			if(EasyStringCheckUtils.isEmpty(model.getTtCode())) 
				LOG.warn("租户信息未指定");
		}
	}
	
	protected void beforeSetPropertiesForQuery(UupmBasePojo pojo, LoginInfo loginInfo) {
		if(null!=loginInfo && EasyStringCheckUtils.isEmpty(pojo.getTtCode()))
			pojo.setTtCode(loginInfo.getTtCode());
		if(EasyStringCheckUtils.isEmpty(pojo.getTtCode())) 
			LOG.warn("租户信息未指定");
	}

}
