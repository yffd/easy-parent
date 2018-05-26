package com.yffd.easy.framework.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.service.ICommonService;
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
public abstract class CommonServiceImpl<POJO> implements ICommonService<POJO> {

	protected abstract IMybatisCommonDao<POJO> getBindDao();
	
	public abstract void beforeSetPropertiesForQuery(POJO pojo, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForAdd(POJO pojo, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForUpdate(POJO pojo, LoginInfo loginInfo);
	public abstract void beforeSetPropertiesForDelete(POJO pojo, LoginInfo loginInfo);
	
	
	@Override
	public Integer save(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForAdd(pojo, loginInfo);
		return this.getBindDao().save(pojo);
	}

	@Override
	public Integer save(List<POJO> entityList, LoginInfo loginInfo) {
		if(null==entityList || entityList.size()==0) return 0;
		List<POJO> tmpList = new ArrayList();
		for(POJO pojo : entityList) {
			this.beforeSetPropertiesForAdd(pojo, loginInfo);
			tmpList.add(pojo);
		}
		return this.getBindDao().save(tmpList);
	}

	@Override
	public Integer update(POJO pojo, POJO entityOld, LoginInfo loginInfo) {
		this.beforeSetPropertiesForUpdate(pojo, loginInfo);
		return this.getBindDao().update(pojo, entityOld);
	}

	@Override
	public Integer delete(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForDelete(pojo, loginInfo);
		return this.getBindDao().delete(pojo);
	}

	@Override
	public Integer findCount(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().findCount(pojo);
	}

	@Override
	public POJO findOne(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().findOne(pojo);
	}

	@Override
	public List<POJO> findListWithOrder(POJO pojo, String orderBy, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().findListWithOrder(pojo, orderBy);
	}

	@Override
	public List<POJO> findList(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().findListWithOrder(pojo, null);
	}

	@Override
	public List<POJO> findAll() {
		return this.getBindDao().findListWithOrder(null, null);
	}

	@Override
	public List<POJO> findAllWithOrder(String orderBy) {
		return this.getBindDao().findListWithOrder(null, orderBy);
	}

	@Override
	public PageResult<POJO> findPageWithOrder(POJO pojo, String orderBy, PageParam page, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().findPageWithOrder(pojo, orderBy, page);
	}

	@Override
	public PageResult<POJO> findPage(POJO pojo, PageParam page, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().findPageWithOrder(pojo, null, page);
	}

	@Override
	public Boolean exsistAndUnique(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().exsistAndUnique(pojo);
	}

	@Override
	public Boolean exsist(POJO pojo, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(pojo, loginInfo);
		return this.getBindDao().exsist(pojo);
	}

}

