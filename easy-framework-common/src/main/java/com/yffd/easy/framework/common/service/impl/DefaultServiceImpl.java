package com.yffd.easy.framework.common.service.impl;

import java.util.Date;

import com.yffd.easy.framework.common.service.ICommonService;
import com.yffd.easy.framework.pojo.entity.CommonEntity;
import com.yffd.easy.framework.pojo.login.LoginInfo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月6日 下午5:31:47 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class DefaultServiceImpl<E> extends CustomServiceImpl<E> implements ICommonService<E> {

	@Override
	protected void beforeSetPropertiesForQuery(Object pojo, LoginInfo loginInfo) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void beforeSetPropertiesForAdd(Object pojo, LoginInfo loginInfo) {
		if(pojo instanceof CommonEntity) {
			CommonEntity entity = (CommonEntity) pojo;
			entity.setVersion(0);
			entity.setDelFlag("0");
			entity.setCreateTime(new Date());
			if(null!=loginInfo) entity.setCreateBy(loginInfo.getUserCode());
		}
	}

	@Override
	protected void beforeSetPropertiesForUpdate(Object pojo, LoginInfo loginInfo) {
		if(pojo instanceof CommonEntity) {
			CommonEntity entity = (CommonEntity) pojo;
			entity.setUpdateTime(new Date());
			if(null!=loginInfo) entity.setUpdateBy(loginInfo.getUserCode());
		}
		
	}

	@Override
	protected void beforeSetPropertiesForDelete(Object pojo, LoginInfo loginInfo) {
		// TODO Auto-generated method stub
		
	}
	
}

