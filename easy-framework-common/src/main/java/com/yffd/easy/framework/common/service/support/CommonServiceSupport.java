package com.yffd.easy.framework.common.service.support;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.pojo.converter.CommonPojoConverter;
import com.yffd.easy.framework.common.service.CommonService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月15日 下午5:55:48 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonServiceSupport<E> {

	private CommonService<E> commonService;
	private IMybatisCommonDao<E> bindDao;
	
	public CommonServiceSupport(CommonService<E> commonService, IMybatisCommonDao<E> bindDao) {
		this.commonService = commonService;
		this.bindDao = bindDao;
	}
	
	private CommonServiceTreeSupport treeSupport;
	private CommonServiceVoSupport<E> VoSupport;
	 
	
	public CommonPojoConverter getPojoConverter() {
		return CommonPojoConverter.getInstance();
	}

	public CommonServiceTreeSupport getTreeSupport() {
		if(null==treeSupport) 
			treeSupport = new CommonServiceTreeSupport(this.bindDao);
		return treeSupport;
	}
	
	public CommonServiceVoSupport<E> getVoSupport() {
		if(null==VoSupport) 
			VoSupport = new CommonServiceVoSupport<E>(this.bindDao);
		return VoSupport;
	}

	
}

