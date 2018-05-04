package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.core.common.mapper.ICommonMapper;
import com.yffd.easy.framework.core.common.service.CommonTreeServiceAbstract;
import com.yffd.easy.framework.core.exception.BizException;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.mapper.IUupmResourceMapper;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月10日 11时31分01秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmResourceService extends CommonTreeServiceAbstract<UupmResourceEntity> {

	@Autowired
	private IUupmResourceMapper uupmResourceMapper;
	
	@Override
	public ICommonMapper<UupmResourceEntity> getMapper() {
		return this.uupmResourceMapper;
	}

	@Override
	public Class<?> getMapperClass() {
		return IUupmResourceMapper.class;
	}

	@Override
	public boolean exsist(UupmResourceEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTenantCode()) 
				|| EasyStringCheckUtils.isEmpty(model.getRsCode())) throw BizException.BIZ_PARAMS_IS_EMPTY();
		UupmResourceEntity paramModel = new UupmResourceEntity();
		paramModel.setRsCode(model.getRsCode());
		return super.exsist(model);
	}

	
}
