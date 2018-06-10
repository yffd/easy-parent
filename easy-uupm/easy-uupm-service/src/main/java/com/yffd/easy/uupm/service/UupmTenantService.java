package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.pojo.entity.UupmAccountEntity;
import com.yffd.easy.uupm.pojo.entity.UupmSecPermissionEntity;
import com.yffd.easy.uupm.pojo.entity.UupmTenantEntity;
/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午1:39:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
@Service
public class UupmTenantService extends UupmBaseService<UupmTenantEntity> {

	@Autowired
	private UupmAccountService uupmAccountService;
	@Autowired
	private UupmSecPermissionService uupmSecPermissionService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addTenantWithAccount(UupmTenantEntity tenant, UupmAccountEntity account, LoginInfo loginInfo) {
		if (null == tenant || EasyStringCheckUtils.isEmpty(tenant.getTtCode())) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		int num = this.save(tenant, loginInfo);
		// 生成账号
		if (null != account && !EasyStringCheckUtils.isEmpty(account.getAcntId()))
			this.uupmAccountService.save(account, loginInfo);
		return num;
	}

	/**
	 * 删除租户以及关联的权限
	 * @param tenant
	 * @param loginInfo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delCascadeByTtCode(UupmTenantEntity tenant, LoginInfo loginInfo) {
		if (null == tenant || EasyStringCheckUtils.isEmpty(tenant.getTtCode())) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除租户
		int num = this.delete(tenant, loginInfo);
		// 删除关联关系：租户-权限
		UupmSecPermissionEntity permission = new UupmSecPermissionEntity();
		permission.setTtCode(tenant.getTtCode());
		this.uupmSecPermissionService.delete(permission, loginInfo);
		return num;
	}
	
	

}
