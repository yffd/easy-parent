package com.yffd.easy.framework.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.persist.dao.ICommonDao;
import com.yffd.easy.framework.common.service.ICommonService;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  业务逻辑操作，需要注入自己实现的Dao类.
 * @Date		 2018年5月8日 下午5:04:19 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class CommonServiceImpl<E> implements ICommonService<E> {

	protected abstract ICommonDao<E> getBindDao();
	
	public abstract void beforeSetPropertiesForQuery(E entity, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForAdd(E entity, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForUpdate(E entity, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForDelete(E entity, LoginInfo loginInfo);
	
	@Override
	public Integer findCount(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().selectCount(entity);
	}

	@Override
	public E findOne(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().selectOne(entity);
	}

	@Override
	public List<E> findListWithOrder(E entity, String orderBy, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().selectListWithOrder(entity, orderBy);
	}

	@Override
	public List<E> findList(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().selectListWithOrder(entity, null);
	}

	@Override
	public List<E> findAll() {
		return this.getBindDao().selectListWithOrder(null, null);
	}

	@Override
	public List<E> findAllWithOrder(String orderBy) {
		return this.getBindDao().selectListWithOrder(null, orderBy);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, String orderBy, PageParam page, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().selectPageWithOrder(entity, orderBy, page);
	}

	@Override
	public PageResult<E> findPage(E entity, PageParam page, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().selectPageWithOrder(entity, null, page);
	}
	
	@Override
	public Integer save(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForAdd(entity, loginInfo);
		return this.getBindDao().insertOne(entity);
	}

	@Override
	public Integer save(List<E> entityList, LoginInfo loginInfo) {
		if(null==entityList || entityList.size()==0) return 0;
		List<E> tmpList = new ArrayList<E>();
		for(E entity : entityList) {
			this.beforeSetPropertiesForAdd(entity, loginInfo);
			tmpList.add(entity);
		}
		return this.getBindDao().insertList(tmpList);
	}

	@Override
	public Integer update(E entity, E entityOld, LoginInfo loginInfo) {
		this.beforeSetPropertiesForUpdate(entity, loginInfo);
		return this.getBindDao().update(entity, entityOld);
	}

	@Override
	public Integer delete(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForDelete(entity, loginInfo);
		return this.getBindDao().delete(entity);
	}

	@Override
	public Boolean exsistAndUnique(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().exsistAndUnique(entity);
	}

	@Override
	public Boolean exsist(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().exsist(entity);
	}

}

