package com.yffd.easy.framework.common.persist.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yffd.easy.common.core.page.PageParam;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年3月27日 下午6:30:47 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICommonMapper<E> {
	
	public int insertOneBy(E entity);
	
	public int insertListBy(List<E> entityList);
	
	public int updateBy(@Param("entity") E entity, @Param("entityOld") E entityOld, @Param("propsMap") Map<String, Object> propsMap);
	
	public int deleteBy(@Param("entity") E entity, @Param("propsMap") Map<String, Object> propsMap);
	
	public E selectOneBy(@Param("entity") E entity, @Param("propsMap") Map<String, Object> propsMap);
	
	public Integer selectCountBy(@Param("entity") E entity, @Param("propsMap") Map<String, Object> propsMap);
	
	public List<E> selectListBy(@Param("entity") E entity, @Param("propsMap") Map<String, Object> propsMap, @Param("orderBy") String orderBy, @Param("page") PageParam page);
	
}

