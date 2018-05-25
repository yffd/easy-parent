package com.yffd.easy.framework.common.service;

import java.util.List;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月8日 下午5:04:19 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class BaseService<E> implements IBaseService<E> {

	protected abstract IMybatisCommonDao<E> getBindDao();
	
	@Override
	public Integer save(E entity) {
		return this.getBindDao().save(entity);
	}

	@Override
	public Integer save(List<E> entityList) {
		return this.getBindDao().save(entityList);
	}

	@Override
	public Integer update(E entity, E entityOld) {
		return this.getBindDao().update(entity, entityOld);
	}

	@Override
	public Integer delete(E entity) {
		return this.getBindDao().delete(entity);
	}

	@Override
	public Integer findCount(E entity) {
		return this.getBindDao().findCount(entity);
	}

	@Override
	public E findOne(E entity) {
		return this.getBindDao().findOne(entity);
	}

	@Override
	public List<E> findListWithOrder(E entity, String orderBy) {
		return this.getBindDao().findListWithOrder(entity, orderBy);
	}

	@Override
	public List<E> findList(E entity) {
		return this.getBindDao().findListWithOrder(entity, null);
	}

	@Override
	public List<E> findAll() {
		return this.getBindDao().findListWithOrder(null, null);
	}

	@Override
	public List<E> findAllWithOrder(String orderBy) {
		return this.getBindDao().findListWithOrder(null, orderBy);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, String orderBy, PageParam page) {
		return this.getBindDao().findPageWithOrder(entity, orderBy, page);
	}

	@Override
	public PageResult<E> findPage(E entity, PageParam page) {
		return this.getBindDao().findPageWithOrder(entity, null, page);
	}

	@Override
	public Boolean exsistAndUnique(E entity) {
		return this.getBindDao().exsistAndUnique(entity);
	}

	@Override
	public Boolean exsist(E entity) {
		return this.getBindDao().exsist(entity);
	}

}

