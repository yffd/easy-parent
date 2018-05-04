package com.yffd.easy.framework.common.persist.mybatis.dao.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;

/**
 * @Description  mybatis dao常用操作类.
 * @Date		 2018年4月18日 下午5:39:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MybatisCommonMapDaoSupport<E> extends MybatisCommonCustomDaoSupport {
	/** mapper xml 中的SQL ID，即statement=insertOneBy */
	public static final String SQL_ID_INSERT_ONE_BY = "insertOneBy";
	/** mapper xml 中的SQL ID，即statement=insertListBy */
	public static final String SQL_ID_INSERT_LIST_BY = "insertListBy";
	/** mapper xml 中的SQL ID，即statement=updateBy */
	public static final String SQL_ID_UPDATE_BY = "updateBy";
	/** mapper xml 中的SQL ID，即statement=deleteBy */
	public static final String SQL_ID_DELETE_BY = "deleteBy";
	/** mapper xml 中的SQL ID，即statement=selectOneBy */
	public static final String SQL_ID_SELECT_ONE_BY = "selectOneBy";
	/** mapper xml 中的SQL ID，即statement=selectCountBy */
	public static final String SQL_ID_SELECT_COUNT_BY = "selectCountBy";
	/** mapper xml 中的SQL ID，即statement=selectListBy */
	public static final String SQL_ID_SELECT_LIST_BY = "selectListBy";
	// 多参数时，参数名称
	public static final String PARAM_NAME_ENTITY = "entity";
	public static final String PARAM_NAME_ENTITY_OLD = "entityOld";
	public static final String PARAM_NAME_PROPS_MAP = "propsMap";
	public static final String PARAM_NAME_PAGE = "page";
	public static final String PARAM_NAME_ORDER_BY = "orderBy";
	
	
	/**
	 * sqlid : {@link #SQL_ID_INSERT_ONE_BY}
	 * @Date	2018年4月19日 下午2:20:26 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	protected Integer insertOneBy(E entity) {
		if(null==entity) return 0;
		return super.customInsertBy(SQL_ID_INSERT_ONE_BY, entity, true);
	}

	/**
	 * sqlid : {@link #SQL_ID_INSERT_LIST_BY}
	 * @Date	2018年4月19日 下午2:23:07 <br/>
	 * @author  zhangST
	 * @param entityList
	 * @return
	 */
	protected Integer insertListBy(List<E> entityList) {
		if(null==entityList || entityList.isEmpty()) return 0;
		return super.customInsertBy(SQL_ID_INSERT_LIST_BY, entityList, true);
	}

	/**
	 * sqlid : {@link #SQL_ID_UPDATE_BY}
	 * @Date	2018年4月19日 下午2:25:11 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param entityOld
	 * @param propsMap
	 * @return
	 */
	protected Integer updateBy(E entity, E entityOld, Map<String, Object> propsMap) {
		if(null==entity) return 0;
		if(null==entityOld && (null==propsMap || propsMap.isEmpty())) return 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_NAME_ENTITY, entity);
		params.put(PARAM_NAME_ENTITY_OLD, entityOld);
		params.put(PARAM_NAME_PROPS_MAP, propsMap);
		return super.customUpdateBy(SQL_ID_UPDATE_BY, params, true);
	}
	
	/**
	 * sqlid : {@link #SQL_ID_DELETE_BY}
	 * @Date	2018年4月19日 下午2:26:17 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Integer deleteBy(E entity, Map<String, Object> propsMap) {
		if(null==entity && (null==propsMap || propsMap.isEmpty())) return 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_NAME_ENTITY, entity);
		params.put(PARAM_NAME_PROPS_MAP, propsMap);
		return super.customDeleteBy(SQL_ID_DELETE_BY, params, true);
	}

	/**
	 * sqlid : {@link #SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:28:17 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Integer selectCountBy(E entity, Map<String, Object> propsMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_NAME_ENTITY, entity);
		params.put(PARAM_NAME_PROPS_MAP, propsMap);
		return super.customSelectCountBy(SQL_ID_SELECT_COUNT_BY, params, true);
	}
	
	/**
	 * sqlid : {@link #SQL_ID_SELECT_ONE_BY}
	 * @Date	2018年4月19日 下午2:27:34 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected E selectOneBy(E entity, Map<String, Object> propsMap) {
		if(null==entity && (null==propsMap || propsMap.isEmpty())) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_NAME_ENTITY, entity);
		params.put(PARAM_NAME_PROPS_MAP, propsMap);
		return super.customSelectOneBy(SQL_ID_SELECT_ONE_BY, params, true);
	}

	/**
	 * sqlid : {@link #SQL_ID_SELECT_LIST_BY}
	 * @Date	2018年4月19日 下午2:28:59 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderBy
	 * @return
	 */
	protected List<E> selectListBy(E entity, Map<String, Object> propsMap, String orderBy) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_NAME_ENTITY, entity);
		params.put(PARAM_NAME_PROPS_MAP, propsMap);
		params.put(PARAM_NAME_ORDER_BY, orderBy);
		return super.customSelectListBy(SQL_ID_SELECT_LIST_BY, params, true);
	}

	/**
	 * sqlid : {@link #SQL_ID_SELECT_LIST_BY}</br>
	 * sqlid count : {@link #SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:31:16 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderBy
	 * @param page
	 * @return
	 */
	protected PageResult<E> selectPageBy(E entity, Map<String, Object> propsMap, String orderBy, PageParam page) {
		if(null==page) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_NAME_ENTITY, entity);
		params.put(PARAM_NAME_PROPS_MAP, propsMap);
		Integer totalRecord = super.customSelectCountBy(SQL_ID_SELECT_COUNT_BY, params, true);
		page.setTotalRecord(totalRecord);
		if(totalRecord==0) return null;
		params.put(PARAM_NAME_ORDER_BY, orderBy);
		params.put(PARAM_NAME_PAGE, page);
		List<E> recordList = super.customSelectListBy(SQL_ID_SELECT_LIST_BY, params, true);
		return new PageResult<E>(page, recordList);
	}

	/**
	 * sqlid : {@link #SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:35:29 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Boolean exsistAndUniqueBy(E entity, Map<String, Object> propsMap) {
		Integer count = this.selectCountBy(entity, propsMap);
		return count==0;
	}

	/**
	 * sqlid : {@link #SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:36:04 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Boolean exsistBy(E entity, Map<String, Object> propsMap) {
		Integer count = this.selectCountBy(entity, propsMap);
		return count>0;
	}

	///////////////属性////////////////////
	
	protected Integer updateByProps(E entity, String propertyName, Object value) {
		if(null==entity) return 0;
		if(EasyStringCheckUtils.isEmpty(propertyName) || null==value) return 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(propertyName, value);
		return this.updateBy(entity, null, paramMap);
	}
	
	protected Integer deleteByProps(String propertyName, Object value) {
		if(EasyStringCheckUtils.isEmpty(propertyName) || null==value) return 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(propertyName, value);
		return this.deleteBy(null, paramMap);
	}

	protected Integer selectCountByProps(String propertyName, Object value) {
		if(EasyStringCheckUtils.isEmpty(propertyName) || null==value) return 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(propertyName, value);
		return this.selectCountBy(null, paramMap);
	}

	protected E selectOneByProps(String propertyName, Object value) {
		if(EasyStringCheckUtils.isEmpty(propertyName) || null==value) return null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(propertyName, value);
		return this.selectOneBy(null, paramMap);
	}

	protected List<E> selectListByProps(String propertyName, Object value, String orderBy) {
		if(EasyStringCheckUtils.isEmpty(propertyName) || null==value) return null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(propertyName, value);
		return this.selectListBy(null, paramMap, orderBy);
	}

	protected PageResult<E> selectPageProps(String propertyName, Object value, String orderBy,
			PageParam page) {
		if(EasyStringCheckUtils.isEmpty(propertyName) || null==value) return null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(propertyName, value);
		return this.selectPageBy(null, paramMap, orderBy, page);
	}

	
}

