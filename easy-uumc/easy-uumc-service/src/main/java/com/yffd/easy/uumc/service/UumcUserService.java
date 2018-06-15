package com.yffd.easy.uumc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uumc.exception.UumcBizException;
import com.yffd.easy.uumc.pojo.entity.UumcAccountEntity;
import com.yffd.easy.uumc.pojo.entity.UumcUserEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月11日 15时23分00秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UumcUserService extends UupmBaseService<UumcUserEntity> {

	@Autowired
	private UumcRelationRoleUserService uumcRelationRoleUserService;
	@Autowired
	private UumcAccountService uumcAccountService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserWithAccount(UumcUserEntity user, UumcAccountEntity account, LoginInfo loginInfo) {
		int result = this.save(user, loginInfo);
		this.uumcAccountService.addUserAccount(account, loginInfo);	// 生成用户账号
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateStatus(UumcUserEntity user, LoginInfo loginInfo) {
		String newStatus = user.getUserStatus();
		// 修改用户状态
		UumcUserEntity newModel = new UumcUserEntity();
		newModel.setUserStatus(newStatus);
		UumcUserEntity oldModel = new UumcUserEntity();
		oldModel.setUserCode(user.getUserCode());
		int result = this.update(newModel, oldModel, loginInfo);
		// 修改账号状态
		String acntId = user.getUserCode();
		this.uumcAccountService.updateStatus(acntId, newStatus, loginInfo);
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByUserCode(String userCode) {
		if (EasyStringCheckUtils.isEmpty(userCode)) throw UumcBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除用户
		UumcUserEntity entity = new UumcUserEntity();
		entity.setUserCode(userCode);
		int num = this.delete(entity, null);
		if (!EasyStringCheckUtils.isEmpty(userCode)) {
			// 删除关联关系：用户-角色
			this.uumcRelationRoleUserService.delByUserCode(userCode);
			// 删除关联关系：用户-账号
			
			UumcAccountEntity account = new UumcAccountEntity();
			account.setAcntId(userCode);
			this.uumcAccountService.delete(account , null);
		}
		return num;
	}
}
