package com.yffd.easy.uupm.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yffd.easy.uupm.entity.UupmOrganizationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmOrganizationDao extends UupmCommonDao<UupmOrganizationEntity> {

	public List<UupmOrganizationEntity> findByOrgCodes(List<String> orgCodes) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCodeList", Arrays.asList(orgCodes));
		return this.findList(paramMap);
	}
}
