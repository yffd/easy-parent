package com.yffd.easy.uupm.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.uupm.entity.a.UupmUITreeEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月24日 16时22分41秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmUITreeDao extends DefaultMybatisDao<UupmUITreeEntity> {

	public Integer deleteByIds(Set<String> ids) {
		return this.deleteByProps("idIter", ids);
	}
	
}
