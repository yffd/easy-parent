package com.yffd.easy.framework.common.persist.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yffd.easy.framework.common.persist.mybatis.constants.MybatisConstants;
import com.yffd.easy.framework.pojo.page.PageParam;

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
	
	public int updateBy(@Param(MybatisConstants.PARAM_NAME_ENTITY) E entity, 
			@Param(MybatisConstants.PARAM_NAME_ENTITY_OLD) E entityOld, 
			@Param(MybatisConstants.PARAM_NAME_PROPS_MAP) Map<String, Object> propsMap);
	
	public int deleteBy(@Param(MybatisConstants.PARAM_NAME_ENTITY) E entity, 
			@Param(MybatisConstants.PARAM_NAME_PROPS_MAP) Map<String, Object> propsMap);
	
	public E selectOneBy(@Param(MybatisConstants.PARAM_NAME_ENTITY) E entity, 
			@Param(MybatisConstants.PARAM_NAME_PROPS_MAP) Map<String, Object> propsMap);
	
	public Integer selectCountBy(@Param(MybatisConstants.PARAM_NAME_ENTITY) E entity, 
			@Param(MybatisConstants.PARAM_NAME_PROPS_MAP) Map<String, Object> propsMap);
	
	public List<E> selectListBy(@Param(MybatisConstants.PARAM_NAME_ENTITY) E entity, 
			@Param(MybatisConstants.PARAM_NAME_PROPS_MAP) Map<String, Object> propsMap, 
			@Param(MybatisConstants.PARAM_NAME_ORDER_BY) String orderBy, 
			@Param(MybatisConstants.PARAM_NAME_PAGE) PageParam page);
	
}

