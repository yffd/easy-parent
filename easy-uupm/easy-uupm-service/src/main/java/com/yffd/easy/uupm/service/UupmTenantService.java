package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.dao.UupmTenantDao;
import com.yffd.easy.uupm.entity.UupmTenantEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月04日 17时31分10秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmTenantService extends UupmBaseService<UupmTenantEntity> {

	@Autowired
	private UupmAccountService uupmAccountService;
	@Autowired
	private UupmTenantResourceService uupmTenantResourceService;
	
	@Autowired
	private UupmTenantDao uupmTenantDao;
	
	@Override
	protected IMybatisCommonDao<UupmTenantEntity> getBindDao() {
		return this.uupmTenantDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addTenantWithAccount(UupmTenantEntity model, LoginInfo loginInfo) {
		if(null==model) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		int num = this.save(model, loginInfo);
		// 生成账号
//		UupmAccountEntity account = new UupmAccountEntity();
//		account.setAccountId(model.getTenantCode());
//		account.setAccountPwd("123456");
//		account.setAccountStatus("active");
//		account.setAccountType("default");
//		this.uupmAccountService.save(account);
		return num;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delByTenantCode(String tenantCode, LoginInfo loginInfo) {
		if(EasyStringCheckUtils.isEmpty(tenantCode)) 
			throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		UupmTenantEntity model = new UupmTenantEntity();
		model.setTenantCode(tenantCode);
		int num = this.delete(model, loginInfo);
		
		this.uupmTenantResourceService.delByTenantCode(tenantCode);
		return num;
	}
	
	

}
