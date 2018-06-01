package com.yffd.easy.uupm.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.yffd.easy.framework.common.persist.mybatis.dao.MybatisCommonDao;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmTenantResourceDao extends MybatisCommonDao<UupmTenantResourceEntity> {

	public List<UupmResourceEntity> selectTenantResource(String ttCode) {
		UupmTenantResourceEntity entity = new UupmTenantResourceEntity();
		entity.setTtCode(ttCode);
		return this.selectListByCustom("selectTenantResource", entity, true);
	}
	
	public int delByRsCodes(Set<String> rsCodes) {
		return this.deleteByProps("rsCodeIter", rsCodes);
	}
	
}
