package com.yffd.easy.framework.common.service;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.service.support.CommonServiceSupport;
import com.yffd.easy.framework.pojo.login.LoginInfo;
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
public abstract class CommonService<E> implements ICommonService<E> {

	private CommonServiceSupport<E> serviceSupport;
	
	public CommonServiceSupport<E> getServiceSupport() {
		if(null==serviceSupport) 
			serviceSupport = new CommonServiceSupport<E>(this, this.getBindDao());
		return serviceSupport;
	}

	protected abstract IMybatisCommonDao<E> getBindDao();
	
	public abstract void beforeSetPropertiesForQuery(E entity, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForAdd(E entity, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForUpdate(E entity, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForDelete(E entity, LoginInfo loginInfo);
	
	
	@Override
	public Integer save(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForAdd(entity, loginInfo);
		return this.getBindDao().save(entity);
	}

	@Override
	public Integer save(List<E> entityList, LoginInfo loginInfo) {
		if(null==entityList || entityList.size()==0) return 0;
		List<E> tmpList = new ArrayList();
		for(E entity : entityList) {
			this.beforeSetPropertiesForAdd(entity, loginInfo);
			tmpList.add(entity);
		}
		return this.getBindDao().save(tmpList);
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
	public Integer findCount(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().findCount(entity);
	}

	@Override
	public E findOne(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().findOne(entity);
	}

	@Override
	public List<E> findListWithOrder(E entity, String orderBy, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().findListWithOrder(entity, orderBy);
	}

	@Override
	public List<E> findList(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return this.getBindDao().findListWithOrder(entity, null);
	}

	@Override
	public List<E> findAll() {
		this.beforeSetPropertiesForQuery(null, null);
		return this.getBindDao().findListWithOrder(null, null);
	}
	
	
	@Override
	public List<E> findAll(LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(null, loginInfo);
		return this.getBindDao().findListWithOrder(null, null);
	}

	@Override
	public List<E> findAllWithOrder(String orderBy, LoginInfo loginInfo) {
		return this.getBindDao().findListWithOrder(null, orderBy);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, String orderBy, LoginInfo loginInfo, PageParam page) {
		return this.getBindDao().findPageWithOrder(entity, orderBy, page);
	}

	@Override
	public PageResult<E> findPage(E entity, LoginInfo loginInfo, PageParam page) {
		return this.getBindDao().findPageWithOrder(entity, null, page);
	}

	@Override
	public Boolean exsistAndUnique(E entity, LoginInfo loginInfo) {
		return this.getBindDao().exsistAndUnique(entity);
	}

	@Override
	public Boolean exsist(E entity, LoginInfo loginInfo) {
		return this.getBindDao().exsist(entity);
	}

}

