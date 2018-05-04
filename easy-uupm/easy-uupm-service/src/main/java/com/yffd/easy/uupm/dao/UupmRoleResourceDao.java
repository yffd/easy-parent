package com.yffd.easy.uupm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmRoleResourceDao extends UupmCommonDao<UupmRoleResourceEntity> {

	public List<UupmRoleResourceEntity> findByRoleCodes(Set<String> roleCodes) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rsCodeList", roleCodes);
		return this.findList(paramMap);
	}
	
	public List<UupmRoleResourceEntity> findByResourceCodes(List<String> resourceCodes) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rsCodeList", resourceCodes);
		return this.findList(paramMap);
	}
}
