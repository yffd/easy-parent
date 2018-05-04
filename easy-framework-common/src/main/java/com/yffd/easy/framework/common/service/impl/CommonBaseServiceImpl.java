package com.yffd.easy.framework.common.service.impl;

import java.util.List;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.framework.common.persist.dao.ICommonBaseDao;
import com.yffd.easy.framework.common.service.ICommonBaseService;
import com.yffd.easy.framework.common.service.support.CommonServiceSupport;

/**
 * @Description  VO = PO.
 * @Date		 2018年5月2日 下午2:37:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class CommonBaseServiceImpl<VO> extends CommonServiceSupport implements ICommonBaseService<VO> {
	
	protected abstract ICommonBaseDao<VO> getBindDao();
	
	@Override
	public Integer save(VO vo) {
		return this.getBindDao().save(vo);
	}

	@Override
	public Integer save(List<VO> voList) {
		return this.getBindDao().save(voList);
	}

	@Override
	public Integer update(VO vo, VO oldVo) {
		return this.getBindDao().update(vo, oldVo);
	}

	@Override
	public Integer delete(VO vo) {
		return this.getBindDao().delete(vo);
	}

	@Override
	public Integer findCount(VO vo) {
		return this.getBindDao().findCount(vo);
	}

	@Override
	public VO findOne(VO vo) {
		return this.getBindDao().findOne(vo);
	}

	@Override
	public List<VO> findListWithOrder(VO vo, String orderBy) {
		return this.getBindDao().findListWithOrder(vo, orderBy);
	}

	@Override
	public List<VO> findList(VO vo) {
		return this.getBindDao().findListWithOrder(vo, null);
	}

	@Override
	public List<VO> findAll() {
		return this.getBindDao().findListWithOrder(null, null);
	}

	@Override
	public List<VO> findAllWithOrder(String orderBy) {
		return this.getBindDao().findListWithOrder(null, orderBy);
	}

	@Override
	public PageResult<VO> findPageWithOrder(VO vo, String orderBy, PageParam page) {
		return this.getBindDao().findPageWithOrder(vo, orderBy, page);
	}

	@Override
	public PageResult<VO> findPage(VO vo, PageParam page) {
		return this.getBindDao().findPageWithOrder(vo, null, page);
	}

	@Override
	public Boolean exsistAndUnique(VO vo) {
		return this.getBindDao().exsistAndUnique(vo);
	}

	@Override
	public Boolean exsist(VO vo) {
		return this.getBindDao().exsist(vo);
	}

}

