package com.yffd.easy.framework.common.persist.dao.impl;

import java.util.List;

import com.yffd.easy.framework.common.persist.dao.ICommonDao;
import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  持久化常用操作接口，以实体类（entity）全名为sql mapper 的 命名空间.
 * @Date		 2018年4月28日 下午2:57:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MybatisCommonDaoImpl<E> extends DefaultMybatisDao<E> implements ICommonDao<E> {
	
	@Override
	public Integer selectCount(E entity) {
		return super.selectCountBy(entity, null);
	}
	
	@Override
	public E selectOne(E entity) {
		return super.selectOneBy(entity, null);
	}

	@Override
	public List<E> selectListWithOrder(E entity, String orderBy) {
		return super.selectListBy(entity, null, orderBy);
	}

	@Override
	public PageResult<E> selectPageWithOrder(E entity, String orderBy, PageParam page) {
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
	
	@Override
	public Integer insertOne(E entity) {
		return super.insertOneBy(entity);
	}

	@Override
	public Integer insertList(List<E> entityList) {
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

}

