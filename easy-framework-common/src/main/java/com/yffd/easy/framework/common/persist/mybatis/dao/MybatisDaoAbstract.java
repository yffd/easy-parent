package com.yffd.easy.framework.common.persist.mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.common.persist.mybatis.constants.MybatisConstants;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  mybatis持久化操作.
 * @Date		 2018年4月28日 下午2:57:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class MybatisDaoAbstract<E> {
	private static final Logger LOG = LoggerFactory.getLogger(MybatisDaoAbstract.class);

	@Autowired
	private SqlSession sqlSession;
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	protected abstract String getSqlNamespace();
	
	protected String getStatement(String sqlId) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getSqlNamespace()).append(".").append(sqlId);
        return sb.toString();
	}
	
	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_INSERT_ONE_BY}
	 * @Date	2018年4月19日 下午2:20:26 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	protected Integer insertOneBy(E entity) {
		if(null==entity) return 0;
		return this.getSqlSession().insert(this.getStatement(MybatisConstants.SQL_ID_INSERT_ONE_BY), entity);
	}

	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_INSERT_LIST_BY}
	 * @Date	2018年4月19日 下午2:23:07 <br/>
	 * @author  zhangST
	 * @param entityList
	 * @return
	 */
	protected Integer insertListBy(List<E> entityList) {
		if(null==entityList || entityList.isEmpty()) return 0;
		return this.getSqlSession().insert(this.getStatement(MybatisConstants.SQL_ID_INSERT_LIST_BY), entityList);
	}

	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_UPDATE_BY}
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
		params.put(MybatisConstants.PARAM_NAME_ENTITY, entity);
		params.put(MybatisConstants.PARAM_NAME_ENTITY_OLD, entityOld);
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getSqlSession().update(this.getStatement(MybatisConstants.SQL_ID_UPDATE_BY), params);
	}
	
	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_DELETE_BY}
	 * @Date	2018年4月19日 下午2:26:17 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Integer deleteBy(E entity, Map<String, Object> propsMap) {
		if(null==entity && (null==propsMap || propsMap.isEmpty())) return 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisConstants.PARAM_NAME_ENTITY, entity);
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getSqlSession().delete(this.getStatement(MybatisConstants.SQL_ID_DELETE_BY), params);
	}

	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年4月19日 下午2:28:17 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected Integer selectCountBy(E entity, Map<String, Object> propsMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisConstants.PARAM_NAME_ENTITY, entity);
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getSqlSession().selectOne(this.getStatement(MybatisConstants.SQL_ID_SELECT_COUNT_BY), params);
	}
	
	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_SELECT_ONE_BY}
	 * @Date	2018年4月19日 下午2:27:34 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	protected E selectOneBy(E entity, Map<String, Object> propsMap) {
		if(null==entity && (null==propsMap || propsMap.isEmpty())) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisConstants.PARAM_NAME_ENTITY, entity);
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, propsMap);
		return this.getSqlSession().selectOne(this.getStatement(MybatisConstants.SQL_ID_SELECT_ONE_BY), params);
	}

	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_SELECT_LIST_BY}
	 * @Date	2018年4月19日 下午2:28:59 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderBy
	 * @return
	 */
	protected List<E> selectListBy(E entity, Map<String, Object> propsMap, String orderBy) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisConstants.PARAM_NAME_ENTITY, entity);
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, propsMap);
		params.put(MybatisConstants.PARAM_NAME_ORDER_BY, orderBy);
		return this.getSqlSession().selectList(this.getStatement(MybatisConstants.SQL_ID_SELECT_LIST_BY), params);
	}
	
	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_SELECT_LIST_BY}</br>
	 * sqlid count : {@link MybatisConstants#SQL_ID_SELECT_COUNT_BY}
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
		params.put(MybatisConstants.PARAM_NAME_ENTITY, entity);
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, propsMap);
		Integer totalRecord = this.getSqlSession().selectOne(this.getStatement(MybatisConstants.SQL_ID_SELECT_COUNT_BY), params);
		page.setTotalRecord(totalRecord);
		if(totalRecord==0) return null;
		params.put(MybatisConstants.PARAM_NAME_ORDER_BY, orderBy);
		params.put(MybatisConstants.PARAM_NAME_PAGE, page);
		List<E> recordList = this.getSqlSession().selectList(this.getStatement(MybatisConstants.SQL_ID_SELECT_LIST_BY), params);
		return new PageResult<E>(page, recordList);
	}

	/**
	 * sqlid : {@link MybatisConstants#SQL_ID_SELECT_COUNT_BY}
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
	 * sqlid : {@link MybatisConstants#SQL_ID_SELECT_COUNT_BY}
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

	/////////////// property ////////////////////
	
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
	
	/////////////// sqlId ////////////////////
	protected Integer selectCountByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#selectCountByCustom]:%s", this.getClass().getName(), sqlId));
        return this.getSqlSession().selectOne(sqlId, parameter);
	}

	protected <T> List<T> selectListByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#selectListByCustom]:%s", this.getClass().getName(), sqlId));
        return this.getSqlSession().selectList(sqlId, parameter);
	}

	protected <T> T selectOneByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#selectOneByCustom]:%s", this.getClass().getName(), sqlId));
        return this.getSqlSession().selectOne(sqlId, parameter);
	}

	protected Integer insertByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#insertByCustom]:%s", this.getClass().getName(), sqlId));
        return this.getSqlSession().insert(sqlId, parameter);
	}

	protected Integer updateByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#updateByCustom]:%s", this.getClass().getName(), sqlId));
        return this.getSqlSession().update(sqlId, parameter);
	}

	protected Integer deleteByCustom(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#deleteByCustom]:%s", this.getClass().getName(), sqlId));
        return this.getSqlSession().delete(sqlId, parameter);
	}

	protected <T> PageResult<T> selectPageByCustom(String sqlId, String countSqlId, Map<String, Object> paramMap, 
			PageParam pageParam, boolean shortName) {
		if(null==paramMap) paramMap = new HashMap<String, Object>();
		if(null!=pageParam) {
			if(EasyStringCheckUtils.isEmpty(countSqlId)) throw CommonBizException.newInstance("countSqlId 不能为空");
			if(shortName) countSqlId = this.getStatement(countSqlId);
			if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#selectPageByCustom]:%s", this.getClass().getName(), countSqlId));
			Integer totalRecord = this.getSqlSession().selectOne(countSqlId, paramMap); // 统计总记录数
			pageParam.setTotalRecord(totalRecord);
			paramMap.put("page", pageParam); // 根据页面传来的分页参数构造SQL分页参数
		}
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		if(LOG.isInfoEnabled()) LOG.info(String.format("===sqlId[%s#selectPageByCustom]:%s", this.getClass().getName(), sqlId));
		List<T> recordList = this.getSqlSession().selectList(sqlId, paramMap); // 获取分页数据集
		return new PageResult<T>(pageParam, recordList);
	}
	
}

