package com.yffd.easy.uumc.service;

import org.springframework.stereotype.Service;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uumc.exception.UumcBizException;
import com.yffd.easy.uumc.pojo.entity.UumcAccountEntity;
import com.yffd.easy.uumc.pojo.enums.UumcAccountTypeEnum;
import com.yffd.easy.uumc.pojo.enums.UumcStatusEnum;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时22分43秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcAccountService extends UupmBaseService<UumcAccountEntity> {

	public int addUserAccount(UumcAccountEntity entity, LoginInfo loginInfo) {
		if (null==entity || EasyStringCheckUtils.isEmpty(entity.getAcntId())) throw UumcBizException.BIZ_PARAMS_ERROR("账户ID不能为空。");
		entity.setAcntType(UumcAccountTypeEnum.USER.getCode());
		if (EasyStringCheckUtils.isEmpty(entity.getAcntStatus())) entity.setAcntStatus(UumcStatusEnum.ACTIVE.getCode());
		return this.save(entity, loginInfo);
	}
	
	public int resetUserAccount(String acntId, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(acntId)) throw UumcBizException.BIZ_PARAMS_ERROR("账户ID不能为空。");
		UumcAccountEntity newEntity = new UumcAccountEntity();
		newEntity.setAcntType(UumcAccountTypeEnum.USER.getCode());
		newEntity.setAcntStatus(UumcStatusEnum.ACTIVE.getCode());
		newEntity.setAcntId(acntId);
		newEntity.setAcntPwd(acntId);
		
		UumcAccountEntity oldEntity = new UumcAccountEntity();
		oldEntity.setAcntId(acntId);
		
		return this.update(newEntity, oldEntity, loginInfo);
	}
	
	public int updateStatus(String acntId, String acntStatus, LoginInfo loginInfo) {
		if (EasyStringCheckUtils.isEmpty(acntId) || EasyStringCheckUtils.isEmpty(acntStatus)) throw UumcBizException.BIZ_PARAMS_ERROR("账户ID或状态不能为空。");
		UumcAccountEntity newEntity = new UumcAccountEntity();
		newEntity.setAcntStatus(acntStatus);
		UumcAccountEntity oldEntity = new UumcAccountEntity();
		oldEntity.setAcntId(acntId);
		return this.update(newEntity, oldEntity, loginInfo);
	}
	
}
