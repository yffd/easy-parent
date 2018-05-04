package com.yffd.easy.uupm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.uupm.dao.UupmRoleDao;
import com.yffd.easy.uupm.entity.UupmRoleEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月04日 16时46分57秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmRoleService extends CommonSimpleServiceImpl<UupmRoleEntity> {

	@Autowired
	private UupmRoleDao uupmRoleDao;

	@Override
	protected BakICommonExtDao<UupmRoleEntity> getBindDao() {
		return uupmRoleDao;
	}

}
