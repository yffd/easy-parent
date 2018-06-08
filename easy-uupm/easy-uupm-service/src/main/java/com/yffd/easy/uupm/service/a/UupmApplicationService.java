package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.dao.UupmApplicationDao;
import com.yffd.easy.uupm.entity.UupmApplicationEntity;
import com.yffd.easy.uupm.exception.UupmBizException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月06日 14时08分50秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmApplicationService extends UupmBaseService<UupmApplicationEntity> {

	@Autowired
	private UupmApplicationDao uupmApplicationDao;
	
	@Override
	protected IMybatisCommonDao<UupmApplicationEntity> getBindDao() {
		return uupmApplicationDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveAppCfg(UupmApplicationEntity model, LoginInfo loginInfo) {
		if(null==model) throw UupmBizException.BIZ_PARAMS_IS_EMPTY();
		this.deleteByAppCode(model.getAppCode());
		return this.save(model, loginInfo);
	}
	
	public int deleteByAppCode(String appCode) {
		if(EasyStringCheckUtils.isEmpty(appCode)) throw UupmBizException.BIZ_PARAMS_IS_EMPTY();
		UupmApplicationEntity model = new UupmApplicationEntity();
		model.setAppCode(appCode);
		return this.delete(model, null);
	}
	
}
