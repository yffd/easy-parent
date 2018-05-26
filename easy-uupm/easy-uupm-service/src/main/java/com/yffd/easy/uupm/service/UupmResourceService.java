package com.yffd.easy.uupm.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.uupm.dao.UupmResourceDao;
import com.yffd.easy.uupm.entity.UupmResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月10日 11时31分01秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmResourceService extends UupmBaseService<UupmResourceEntity> {

	@Autowired
	private UupmResourceDao uupmResourceDao;
	
	@Override
	protected IMybatisCommonDao<UupmResourceEntity> getBindDao() {
		return this.uupmResourceDao;
	}
	
	public Integer deleteByIds(String idStr) {
		if(EasyStringCheckUtils.isEmpty(idStr)) return 0;
		String[] idsArr = idStr.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		Set<String> ids = new HashSet<String>(idsList);
		return this.uupmResourceDao.deleteByIds(ids);
	}
	
}
