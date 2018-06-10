package com.yffd.easy.uupm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.uupm.exception.UupmBizException;
import com.yffd.easy.uupm.pojo.entity.UupmAppSystemEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月06日 14时08分50秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmAppSystemService extends UupmBaseService<UupmAppSystemEntity> {

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveAppCfg(UupmAppSystemEntity appSystem, LoginInfo loginInfo) {
		if (null == appSystem || EasyStringCheckUtils.isEmpty(appSystem.getAppCode())) throw UupmBizException.BIZ_PARAMS_IS_EMPTY();
		// 删除已有配置
		UupmAppSystemEntity entity = new UupmAppSystemEntity();
		entity.setAppCode(appSystem.getAppCode());
		this.delete(entity, null);
		// 添加新配置
		return this.save(appSystem, loginInfo);
	}
	
}
