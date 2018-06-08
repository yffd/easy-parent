package com.yffd.easy.framework.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.framework.common.service.ICommonService;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  业务逻辑操作，直接利用MybatisCommonDao完成持久化操作.
 * @Date		 2018年5月8日 下午5:04:19 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class CustomServiceImpl<E> extends DefaultMybatisDao<E> implements ICommonService<E> {

	protected abstract void beforeSetPropertiesForQuery(Object pojo, LoginInfo loginInfo);
	protected abstract void beforeSetPropertiesForAdd(Object pojo, LoginInfo loginInfo);
	protected abstract void beforeSetPropertiesForUpdate(Object pojo, LoginInfo loginInfo);
	protected abstract void beforeSetPropertiesForDelete(Object pojo, LoginInfo loginInfo);
	
	@Override
	public Integer findCount(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return super.selectCountBy(entity, null);
	}

	@Override
	public E findOne(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return (E) super.selectOneBy(entity, null);
	}

	@Override
	public List<E> findListWithOrder(E entity, String orderBy, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return (List<E>) super.selectListBy(entity, null, orderBy);
	}

	@Override
	public List<E> findList(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return (List<E>) super.selectListBy(entity, null, null);
	}

	@Override
	public List<E> findAll() {
		return (List<E>) super.selectListBy(null, null, null);
	}

	@Override
	public List<E> findAllWithOrder(String orderBy) {
		return (List<E>) super.selectListBy(null, null, orderBy);
	}

	@Override
	public PageResult<E> findPageWithOrder(E entity, String orderBy, PageParam page, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return (PageResult<E>) super.selectPageBy(entity, null, orderBy, page);
	}

	@Override
	public PageResult<E> findPage(E entity, PageParam page, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return (PageResult<E>) super.selectPageBy(entity, null, null, page);
	}
	
	@Override
	public Integer save(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForAdd(entity, loginInfo);
		return super.insertOneBy(entity);
	}

	@Override
	public Integer save(List<E> entityList, LoginInfo loginInfo) {
		if(null==entityList || entityList.size()==0) return 0;
		List<E> tmpList = new ArrayList<>();
		for(E entity : entityList) {
			this.beforeSetPropertiesForAdd(entity, loginInfo);
			tmpList.add(entity);
		}
		return super.insertListBy(tmpList);
	}

	@Override
	public Integer update(E entity, E entityOld, LoginInfo loginInfo) {
		this.beforeSetPropertiesForUpdate(entity, loginInfo);
		return super.updateBy(entity, entityOld, null);
	}

	@Override
	public Integer delete(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForDelete(entity, loginInfo);
		return super.deleteBy(entity, null);
	}

	@Override
	public Boolean exsistAndUnique(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return super.exsistAndUniqueBy(entity, null);
	}

	@Override
	public Boolean exsist(E entity, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(entity, loginInfo);
		return super.exsistBy(entity, null);
	}

}

