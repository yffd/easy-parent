package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.core.common.mapper.ICommonMapper;
import com.yffd.easy.framework.core.common.service.CommonTreeServiceAbstract;
import com.yffd.easy.framework.core.exception.BizException;
import com.yffd.easy.uupm.entity.UupmDictionaryEntity;
import com.yffd.easy.uupm.mapper.IUupmDictionaryMapper;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月10日 17时43分09秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmDictionaryService extends CommonTreeServiceAbstract<UupmDictionaryEntity> {

	@Autowired
	private IUupmDictionaryMapper uupmDictionaryMapper;
	
	@Override
	public ICommonMapper<UupmDictionaryEntity> getMapper() {
		return this.uupmDictionaryMapper;
	}

	@Override
	public Class<?> getMapperClass() {
		return IUupmDictionaryMapper.class;
	}

	@Override
	public boolean exsist(UupmDictionaryEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTenantCode()) 
				|| EasyStringCheckUtils.isEmpty(model.getKeyCode())) throw BizException.BIZ_PARAMS_IS_EMPTY();
		UupmDictionaryEntity paramModel = new UupmDictionaryEntity();
		paramModel.setKeyCode(model.getKeyCode());
		return super.exsist(model);
	}

}
