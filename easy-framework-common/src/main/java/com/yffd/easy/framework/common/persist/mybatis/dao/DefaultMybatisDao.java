package com.yffd.easy.framework.common.persist.mybatis.dao;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.constants.CommonConstants;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月28日 下午2:57:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MybatisCommonDao<E> implements IMybatisCommonDao<E> {
	
	@Autowired
	private MybatisCustomDao mybatisCustomDao;
	
	private MybatisCustomDao getMybatisCustomDao() {
		return mybatisCustomDao;
	}

	private String getStatement(String sqlId) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getSqlNamespace()).append(".").append(sqlId);
        return sb.toString();
	}
	
	@Override
	public Class<?> getGenericType() {
		return (Class<?>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public String getSqlNamespace() {
		return this.getClass().getName();
	}

	@Override
	public Integer save(E entity) {
		return this.insertOneBy(entity);
	}

	@Override
	public Integer save(List<E> entityList) {
		return this.insertListBy(entityList);
	}

	@Override
	public Integer update(E entity, E entityOld) {
		return this.updateBy(entity, entityOld, null);
	}
	
	@Override
	public Integer delete(E entity) {
		return this.deleteBy(entity, null);
	}

	@Override
	public Integer findCount(E entity) {
		return this.selectCountBy(entity, null);
	}
	
	@Override
	public E findOne(E entity) {
		return this.selectOneBy(entity, null);
	}

	@Override
	public List<E> findListWithOrder(E entity, String orderBy) {
		return this.selectListBy(entity, null, orderBy);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, String orderBy, PageParam page) {
		return this.selectPageBy(entity, null, orderBy, page);
	}

	@Override
	public Boolean exsistAndUnique(E entity) {
		return this.exsistAndUniqueBy(entity, null);
	}

	@Override
	public Boolean exsist(E entity) {
		return this.exsistBy(entity, null);
	}
	
	
	///////////////////// protected /////////////////////////////////////////////////////
	
	/**
	 * sqlid : {@link CommonConstants#SQL_ID_INSERT_ONE_BY}
	 * @Date	2018年4月19日 下午2:20:26 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	protected Integer insertOneBy(E entity) {
		if(null==entity) return 0;
		return this.getMybatisCustomDao()
				.customInsertBy(this.getStatement(CommonConstants.SQL_ID_INSERT_ONE_BY), entity);
	}

	/**
	 * sqlid : {@link CommonConstants#SQL_ID_INSERT_LIST_BY}
	 * @Date	2018年4月19日 下午2:23:07 <br/>
	 * @author  zhangST
	 * @param entityList
	 * @return
	 */
	protected Integer insertListBy(List<E> entityList) {
		if(null==entityList || entityList.isEmpty()) return 0;
		return this.getMybatisCustomDao()
				.customInsertBy(this.getStatement(CommonConstants.SQL_ID_INSERT_LIST_BY), entityList);
	}

	/**
	 * sqlid : {@link CommonConstants#SQL_ID_UPDATE_BY}
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
		params.put(CommonConstants.PARAM_NAME_ENTITY, entity);
		params.put(CommonConstants.PARAM_NAME_ENTITY_OLD, entityOld);
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getMybatisCustomDao()
				.customUpdateBy(this.getStatement(CommonConstants.SQL_ID_UPDATE_BY), params);
	}
	
	/**
	 * sqlid : {@link CommonConstants#SQL_ID_DELETE_BY}
	 * @Date	2018年4月19日 下午2:26:17 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Integer deleteBy(E entity, Map<String, Object> propsMap) {
		if(null==entity && (null==propsMap || propsMap.isEmpty())) return 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, entity);
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getMybatisCustomDao()
				.customDeleteBy(this.getStatement(CommonConstants.SQL_ID_DELETE_BY), params);
	}

	/**
	 * sqlid : {@link CommonConstants#SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:28:17 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Integer selectCountBy(E entity, Map<String, Object> propsMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, entity);
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getMybatisCustomDao()
				.customSelectCountBy(this.getStatement(CommonConstants.SQL_ID_SELECT_COUNT_BY), params);
	}
	
	/**
	 * sqlid : {@link CommonConstants#SQL_ID_SELECT_ONE_BY}
	 * @Date	2018年4月19日 下午2:27:34 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected E selectOneBy(E entity, Map<String, Object> propsMap) {
		if(null==entity && (null==propsMap || propsMap.isEmpty())) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, entity);
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getMybatisCustomDao()
				.customSelectOneBy(this.getStatement(CommonConstants.SQL_ID_SELECT_ONE_BY), params);
	}

	/**
	 * sqlid : {@link CommonConstants#SQL_ID_SELECT_LIST_BY}
	 * @Date	2018年4月19日 下午2:28:59 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderBy
	 * @return
	 */
	protected List<E> selectListBy(E entity, Map<String, Object> propsMap, String orderBy) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, entity);
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, propsMap);
		params.put(CommonConstants.PARAM_NAME_ORDER_BY, orderBy);
		return this.getMybatisCustomDao()
				.customSelectListBy(this.getStatement(CommonConstants.SQL_ID_SELECT_LIST_BY), params);
	}
	
	/**
	 * sqlid : {@link CommonConstants#SQL_ID_SELECT_LIST_BY}</br>
	 * sqlid count : {@link CommonConstants#SQL_ID_SELECT_COUNT_BY}
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
		params.put(CommonConstants.PARAM_NAME_ENTITY, entity);
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, propsMap);
		Integer totalRecord = this.getMybatisCustomDao()
				.customSelectCountBy(this.getStatement(CommonConstants.SQL_ID_SELECT_COUNT_BY), params);
		page.setTotalRecord(totalRecord);
		if(totalRecord==0) return null;
		params.put(CommonConstants.PARAM_NAME_ORDER_BY, orderBy);
		params.put(CommonConstants.PARAM_NAME_PAGE, page);
		List<E> recordList = this.getMybatisCustomDao()
				.customSelectListBy(this.getStatement(CommonConstants.SQL_ID_SELECT_LIST_BY), params);
		return new PageResult<E>(page, recordList);
	}

	/**
	 * sqlid : {@link CommonConstants#SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:35:29 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Boolean exsistAndUniqueBy(E entity, Map<String, Object> propsMap) {
		Integer count = this.selectCountBy(entity, propsMap);
		return count==1;
	}

	/**
	 * sqlid : {@link CommonConstants#SQL_ID_SELECT_COUNT_BY}
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
	
	// 自定义查询
	protected Integer selectCountByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
        return this.getMybatisCustomDao().customSelectCountBy(sqlId, parameter);
	}

	protected <T> List<T> selectListByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
        return this.getMybatisCustomDao().customSelectListBy(sqlId, parameter);
	}

	protected <T> T selectOneByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
        return this.getMybatisCustomDao().customSelectOneBy(sqlId, parameter);
	}

	protected Integer insertByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
        return this.getMybatisCustomDao().customInsertBy(sqlId, parameter);
	}

	protected Integer updateByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
        return this.getMybatisCustomDao().customUpdateBy(sqlId, parameter);
	}

	protected Integer deleteByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
        return this.getMybatisCustomDao().customDeleteBy(sqlId, parameter);
	}

	protected <T> PageResult<T> selectPaginationByCustom(String sqlId, String countSqlId, Map<String, Object> paramMap, 
			PageParam pageParam, boolean shortName) {
		if(null==paramMap) paramMap = new HashMap<String, Object>();
		if(null!=pageParam) {
			if(EasyStringCheckUtils.isEmpty(countSqlId)) throw CommonBizException.newInstance("countSqlId 不能为空");
			if(shortName) countSqlId = this.getStatement(countSqlId);
			Integer totalRecord = this.getMybatisCustomDao().customSelectOneBy(countSqlId, paramMap); // 统计总记录数
			pageParam.setTotalRecord(totalRecord);
			paramMap.put("page", pageParam); // 根据页面传来的分页参数构造SQL分页参数
		}
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		List<T> recordList = this.getMybatisCustomDao().customSelectListBy(sqlId, paramMap); // 获取分页数据集
		return new PageResult<T>(pageParam, recordList);
	}
	
}

