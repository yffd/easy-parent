package com.yffd.easy.uupm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.uupm.entity.UupmUserEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月25日 17时59分59秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmUserDao extends UupmCommonDao<UupmUserEntity> {

	public PageResult<Map<String, Object>> findUserInfo(Map<String, Object> paramMap, PageParam pageParam) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("map", paramMap);
		params.put("page", pageParam);
		return this.customSelectPageBy(paramMap, pageParam, "selectUserInfo", "selectUserInfoCount", true);
	}
	
	public List<Map<String, Object>> findUserInfo(Map<String, Object> paramMap) {
		return this.customSelectListBy("selectUserInfo", paramMap, true);
	}
}
