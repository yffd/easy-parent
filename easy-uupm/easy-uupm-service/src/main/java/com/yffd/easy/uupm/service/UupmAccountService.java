package com.yffd.easy.uupm.service;

import org.springframework.stereotype.Service;

import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.pojo.entity.UupmAccountEntity;
import com.yffd.easy.uupm.pojo.enums.UupmAccountTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmStatusStyleEnum;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月09日 17时22分43秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmAccountService extends UupmBaseService<UupmAccountEntity> {
	
	public PageResult<UupmAccountEntity> findTenantAccount(UupmAccountEntity paramModel, PageParam paramPage, LoginInfo loginInfo) {
		paramModel.setAcntType(UupmAccountTypeEnum.TENANT.getCode());
		return this.findPage(paramModel, paramPage, loginInfo);
	}
	
	public int addTenantAccount(UupmAccountEntity entity, LoginInfo loginInfo) {
		entity.setAcntType(UupmAccountTypeEnum.TENANT.getCode());
		entity.setAcntStatus(UupmStatusStyleEnum.ACTIVE.getCode());
		return this.save(entity, loginInfo);
	}
	
	public int addUserAccount(UupmAccountEntity entity, LoginInfo loginInfo) {
		entity.setAcntType(UupmAccountTypeEnum.USER.getCode());
		entity.setAcntStatus(UupmStatusStyleEnum.ACTIVE.getCode());
		return this.save(entity, loginInfo);
	}
}
