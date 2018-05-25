package com.yffd.easy.framework.common.persist.dao.impl;

import java.util.List;

import com.yffd.easy.framework.common.persist.dao.ICommonBaseDao;
import com.yffd.easy.framework.common.persist.dao.support.MybatisCommonGenericDaoSupport;
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
public class MybatisCommonBaseDaoImpl<E> extends MybatisCommonGenericDaoSupport<E> implements ICommonBaseDao<E> {

	@Override
	public Integer save(E entity) {
		return super.insertOneBy(entity);
	}

	@Override
	public Integer save(List<E> entityList) {
		return super.insertListBy(entityList);
	}

	@Override
	public Integer update(E entity, E entityOld) {
		return super.updateBy(entity, entityOld, null);
	}
	
	@Override
	public Integer delete(E entity) {
		return super.deleteBy(entity, null);
	}

	@Override
	public Integer findCount(E entity) {
		return super.selectCountBy(entity, null);
	}
	
	@Override
	public E findOne(E entity) {
		return super.selectOneBy(entity, null);
	}

	@Override
	public List<E> findListWithOrder(E entity, String orderBy) {
		return super.selectListBy(entity, null, orderBy);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, String orderBy, PageParam page) {
		return super.selectPageBy(entity, null, orderBy, page);
	}

	@Override
	public Boolean exsistAndUnique(E entity) {
		return super.exsistAndUniqueBy(entity, null);
	}

	@Override
	public Boolean exsist(E entity) {
		return super.exsistBy(entity, null);
	}

}

