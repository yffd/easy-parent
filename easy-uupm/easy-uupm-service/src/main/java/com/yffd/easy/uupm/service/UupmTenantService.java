package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.framework.core.exception.BizException;
import com.yffd.easy.uupm.dao.UupmTenantDao;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
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
public class UupmTenantService extends CommonSimpleServiceImpl<UupmTenantEntity> {

	@Autowired
	private UupmAccountService uupmAccountService;
	
	@Autowired
	private UupmTenantDao uupmTenantDao;
	
	@Override
	protected BakICommonExtDao<UupmTenantEntity> getBindDao() {
		return this.uupmTenantDao;
	}


	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addTenantWithAccount(UupmTenantEntity model) {
		if(null==model) throw BizException.BIZ_PARAMS_IS_EMPTY();
		int num = this.getBindDao().save(model);
		// 生成账号
		UupmAccountEntity account = new UupmAccountEntity();
		account.setAccountId(model.getTenantCode());
		account.setAccountPwd("123456");
		account.setAccountStatus("active");
		account.setAccountType("default");
		this.uupmAccountService.save(account);
		return num;
	}

}
