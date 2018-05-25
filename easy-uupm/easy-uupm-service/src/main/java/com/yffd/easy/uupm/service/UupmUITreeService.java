package com.yffd.easy.uupm.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.service.CommonService;
import com.yffd.easy.uupm.dao.UupmUITreeDao;
import com.yffd.easy.uupm.entity.UupmUITreeEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月24日 16时22分41秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmUITreeService extends CommonService<UupmUITreeEntity> {

	@Autowired
	private UupmUITreeDao uupmUITreeDao;

	@Override
	protected IMybatisCommonDao<UupmUITreeEntity> getBindDao() {
		return uupmUITreeDao;
	}
	
	public Integer deleteByIds(String idStr) {
		if(EasyStringCheckUtils.isEmpty(idStr)) return 0;
		String[] idsArr = idStr.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		Set<String> ids = new HashSet<String>(idsList);
		return this.uupmUITreeDao.deleteByIds(ids);
	}
}
