package com.yffd.easy.uupm.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.uupm.entity.a.UupmOrganizationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmOrganizationDao extends DefaultMybatisDao<UupmOrganizationEntity> {

	public Integer deleteByIds(Set<String> ids) {
		return this.deleteByProps("idIter", ids);
	}
	
}
