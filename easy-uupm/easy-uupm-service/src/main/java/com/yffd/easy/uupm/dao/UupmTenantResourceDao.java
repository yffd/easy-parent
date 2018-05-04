package com.yffd.easy.uupm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

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
public class UupmTenantResourceDao extends UupmCommonDao<UupmTenantResourceEntity> {

	public List<Map<String, Object>> findTenantResource(Map<String, Object> paramMap) {
		return this.customSelectListBy("selectTenantResource", paramMap, true);
	}
}
