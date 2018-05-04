package com.yffd.easy.framework.common.persist.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.framework.common.persist.dao.ICommonGenericDao;

/**
 * @Description  dao常用操作类，公开更多的方法.
 * @Date		 2018年4月28日 下午5:42:39 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Deprecated
public class MybatisCommonGenericDaoImpl<E> extends MybatisCommonBaseDaoImpl<E> implements ICommonGenericDao<E> {

	@Override
	public Integer update(E entity, E entityOld, Map<String, Object> propsMap) {
		return super.updateBy(entity, entityOld, propsMap);
	}

	@Override
	public Integer delete(E entity, Map<String, Object> propsMap) {
		return super.deleteBy(entity, propsMap);
	}

	@Override
	public Integer findCount(E entity, Map<String, Object> propsMap) {
		return super.selectCountBy(entity, propsMap);
	}

	@Override
	public E findOne(E entity, Map<String, Object> propsMap) {
		return super.selectOneBy(entity, propsMap);
	}

	@Override
	public List<E> findListWithOrder(E entity, Map<String, Object> propsMap, String orderBy) {
		return super.selectListBy(entity, propsMap, orderBy);
	}

	@Override
	public List<E> findListWithOrder(E entity, Map<String, Object> propsMap, String[] orderPropertyNames,
			String[] orderByTypes) {
		String orderBy = this.makeOrderBy(orderPropertyNames, orderByTypes);
		return super.selectListBy(entity, propsMap, orderBy);
	}

	@Override
	public List<E> findListWithOrder(E entity, Map<String, Object> propsMap, String orderPropertyName,
			String orderByType) {
		String orderBy = this.makeOrderBy(orderPropertyName, orderByType);
		return super.selectListBy(entity, propsMap, orderBy);
	}

	@Override
	public List<E> findList(E entity, Map<String, Object> propsMap) {
		return super.selectListBy(entity, propsMap, null);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, Map<String, Object> propsMap, String orderBy, PageParam page) {
		return super.selectPageBy(entity, propsMap, orderBy, page);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, Map<String, Object> propsMap, String[] orderPropertyNames,
			String[] orderByTypes, PageParam page) {
		String orderBy = this.makeOrderBy(orderPropertyNames, orderByTypes);
		return super.selectPageBy(entity, propsMap, orderBy, page);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, Map<String, Object> propsMap, String orderPropertyName,
			String orderByType, PageParam page) {
		String orderBy = this.makeOrderBy(orderPropertyName, orderByType);
		return super.selectPageBy(entity, propsMap, orderBy, page);
	}

	@Override
	public PageResult<E> findPage(E entity, Map<String, Object> propsMap, PageParam page) {
		return super.selectPageBy(entity, propsMap, null, page);
	}

	@Override
	public Boolean exsistAndUnique(E entity, Map<String, Object> propsMap) {
		return super.exsistAndUniqueBy(entity, propsMap);
	}

	@Override
	public Boolean exsist(E entity, Map<String, Object> propsMap) {
		return super.exsistBy(entity, propsMap);
	}

	///////////////// 属性 /////////////////////////////////////////
	
	@Override
	public Integer updateByProperty(E entity, String propertyName, Object value) {
		return super.updateByProps(entity, propertyName, value);
	}

	@Override
	public Integer deleteByProperty(String propertyName, Object value) {
		return super.deleteByProps(propertyName, value);
	}

	@Override
	public Integer findCountByProperty(String propertyName, Object value) {
		return super.selectCountByProps(propertyName, value);
	}

	@Override
	public E findOneByProperty(String propertyName, Object value) {
		return super.selectOneByProps(propertyName, value);
	}

	@Override
	public List<E> findListWithOrderByProperty(String propertyName, Object value, String orderBy) {
		return super.selectListByProps(propertyName, value, orderBy);
	}

	@Override
	public List<E> findListWithOrderByProperty(String propertyName, Object value, String[] orderPropertyNames,
			String[] orderByTypes) {
		String orderBy = this.makeOrderBy(orderPropertyNames, orderByTypes);
		return super.selectListByProps(propertyName, value, orderBy);
	}

	@Override
	public List<E> findListWithOrderByProperty(String propertyName, Object value, String orderPropertyName,
			String orderByType) {
		String orderBy = this.makeOrderBy(orderPropertyName, orderByType);
		return super.selectListByProps(propertyName, value, orderBy);
	}

	@Override
	public List<E> findListByProperty(String propertyName, Object value) {
		return super.selectListByProps(propertyName, value, null);
	}

	@Override
	public PageResult<E> findPageWithOrderByProperty(String propertyName, Object value, String orderBy,
			PageParam page) {
		return super.selectPageProps(propertyName, value, orderBy, page);
	}

	@Override
	public PageResult<E> findPageWithOrderByProperty(String propertyName, Object value, String[] orderPropertyNames,
			String[] orderByTypes, PageParam page) {
		String orderBy = this.makeOrderBy(orderPropertyNames, orderByTypes);
		return super.selectPageProps(propertyName, value, orderBy, page);
	}

	@Override
	public PageResult<E> findPageWithOrderByProperty(String propertyName, Object value, String orderPropertyName,
			String orderByType, PageParam page) {
		String orderBy = this.makeOrderBy(orderPropertyName, orderByType);
		return super.selectPageProps(propertyName, value, orderBy, page);
	}

	@Override
	public PageResult<E> findPageByProperty(String propertyName, Object value, PageParam page) {
		return super.selectPageProps(propertyName, value, null, page);
	}


}

