package com.yffd.easy.framework.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.service.ICommonGenericService;
import com.yffd.easy.framework.common.service.support.CommonServiceSupport;

/**
 * @Description  service常用操作工具，VO与PO不一致情况下，会自动完成默认方式的转换.
 * @Date		 2018年4月25日 上午10:41:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class CommonGenericServiceImpl<VO> extends CommonServiceSupport implements ICommonGenericService<VO> {

	protected abstract <T> ICommonGenericService<T> getBindDao();
	
	protected abstract void afterPropertiesSet(VO vo);
	
	protected void afterPropertiesSet(List<VO> voList) {
		if(null==voList || voList.size()==0) return;
		for(VO vo : voList) {
			this.afterPropertiesSet(vo);
		}
	}
	
	protected void afterPropertiesSet(PageResult<VO> voPage) {
		if(null==voPage) return;
		List<VO> voList = voPage.getRecordList();
		if(null==voList || voList.size()==0) return;
		for(VO vo : voList) {
			this.afterPropertiesSet(vo);
		}
	}
	
	protected Class<?> getEntityClazz() {
		Class<?> daoGenericTypeClazz = this.getBindDao().getClass().getGenericInterfaces()[0].getClass();
		return daoGenericTypeClazz;
	}
	protected Class<VO> getVoClazz() {
		Class<VO> daoGenericTypeClazz = (Class<VO>) this.getClass().getGenericInterfaces()[0].getClass();
		return daoGenericTypeClazz;
	}


	@Override
	public Integer save(VO vo) {
		Object entity = this.createEntityWithSameProperties(vo, getEntityClazz());
		if(null==entity) return 0;
		return this.getBindDao().save(entity);
	}

	@Override
	public Integer save(List<VO> voList) {
		List<?> entityList = this.createEntityWithSameProperties(voList, getEntityClazz());
		if(null==entityList || entityList.isEmpty()) return 0;
		return this.getBindDao().save(entityList);
	}

	@Override
	public Integer update(VO vo, VO oldVo, Map<String, Object> map) {
		Object entity = this.createEntityWithSameProperties(vo, getEntityClazz());
		Object entityOld = this.createEntityWithSameProperties(oldVo, map, getEntityClazz());
		if(null==entity || (null==entityOld && (null==map || map.isEmpty()))) return 0;
		return this.getBindDao().update(entity, entityOld, map);
	}

	@Override
	public Integer update(VO vo, VO oldVo) {
		return this.update(vo, oldVo, null);
	}

	@Override
	public Integer delete(VO vo, Map<String, Object> map) {
		Object entity = this.createEntityWithSameProperties(vo, map, getEntityClazz());
		if(null==entity || (null==map || map.isEmpty())) return 0;
		return this.getBindDao().delete(entity, map);
	}

	@Override
	public Integer delete(VO vo) {
		return this.delete(vo, null);
	}

	@Override
	public Integer findCount(VO vo, Map<String, Object> map) {
		Object entity = this.createEntityWithSameProperties(vo, map, getEntityClazz());
		return this.getBindDao().findCount(entity, map);
	}

	@Override
	public Integer findCount(VO vo) {
		return this.findCount(vo, null);
	}

	@Override
	public VO findOne(VO vo, Map<String, Object> map) {
		Object entity = this.createEntityWithSameProperties(vo, map, getEntityClazz());
		Object obj = this.getBindDao().findOne(entity, map);
		VO result = this.createVoWithSameProperties(obj, getVoClazz());
		this.afterPropertiesSet(result);
		return result;
	}

	@Override
	public VO findOne(VO vo) {
		return this.findOne(vo, null);
	}

	@Override
	public List<VO> findListWithOrder(VO vo, Map<String, Object> map, String orderBy) {
		Object entity = this.createEntityWithSameProperties(vo, map, getEntityClazz());
		List<?> objList = this.getBindDao().findListWithOrder(entity, map, orderBy);
		List<VO> result = this.createVoListWithSameProperties(objList, getVoClazz());
		this.afterPropertiesSet(result);
		return result;
	}

	@Override
	public List<VO> findListWithOrder(VO vo, String orderBy) {
		return this.findListWithOrder(vo, null, orderBy);
	}

	@Override
	public List<VO> findList(VO vo, Map<String, Object> map) {
		return this.findListWithOrder(vo, map, null);
	}
	
	@Override
	public List<VO> findList(VO vo) {
		return this.findListWithOrder(vo, null, null);
	}
	
	@Override
	public List<VO> findAll() {
		return this.findList(null);
	}
	
	@Override
	public List<VO> findAllWithOrder(String orderBy) {
		return this.findListWithOrder(null, orderBy);
	}
	
	@Override
	public PageResult<VO> findPageWithOrder(VO vo, Map<String, Object> map, String orderBy, PageParam page) {
		Object entity = this.createEntityWithSameProperties(vo, map, getEntityClazz());
		PageResult<?> objList = this.getBindDao().findPageWithOrder(entity, map, orderBy, page);
		PageResult<VO> result = this.createVoPageWithSameProperties(objList, getVoClazz());
		this.afterPropertiesSet(result);
		return result;
	}

	@Override
	public PageResult<VO> findPageWithOrder(VO vo, String orderBy, PageParam page) {
		return this.findPageWithOrder(vo, null, orderBy, page);
	}

	@Override
	public PageResult<VO> findPage(VO vo, Map<String, Object> map, PageParam page) {
		return this.findPageWithOrder(vo, map, null, page);
	}
	
	@Override
	public PageResult<VO> findPage(VO vo, PageParam page) {
		return this.findPageWithOrder(vo, null, null, page);
	}
	
	@Override
	public Boolean exsistAndUnique(VO vo, Map<String, Object> map) {
		Integer result = this.getBindDao().findCount(vo, map);
		return result == 1;
	}

	@Override
	public Boolean exsistAndUnique(VO vo) {
		return this.exsistAndUnique(vo, null);
	}

	@Override
	public Boolean exsist(VO vo, Map<String, Object> map) {
		Integer result = this.getBindDao().findCount(vo, map);
		return result > 0;
	}

	@Override
	public Boolean exsist(VO vo) {
		return this.exsist(vo, null);
	}
	
	
	
	@Override
	public Integer deleteByProperty(String propertyName, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(propertyName, value);
		return this.delete(null, map);
	}

	@Override
	public Integer deleteByPrimaryIds(String[] primaryIds) {
		return this.deleteByProperty("idList", primaryIds);
	}

	@Override
	public Integer deleteByPrimaryId(String primaryId) {
		return this.deleteByProperty("id", primaryId);
	}

	@Override
	public Integer findCountByProperty(String propertyName, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(propertyName, value);
		return this.findCount(null, map);
	}

	@Override
	public VO findOneByProperty(String propertyName, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(propertyName, value);
		return this.findOne(null, map);
	}

	@Override
	public VO findOneByPrimaryId(String primaryId) {
		return this.findOneByProperty("id", primaryId);
	}

	@Override
	public List<VO> findListWithOrderByProperty(String propertyName, Object value, String orderBy) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(propertyName, value);
		return this.findListWithOrder(null, map, orderBy);
	}

	@Override
	public List<VO> findListByProperty(String propertyName, Object value) {
		return this.findListWithOrderByProperty(propertyName, value, null);
	}

	@Override
	public PageResult<VO> findPageWithOrderByProperty(String propertyName, Object value, String orderBy,
			PageParam page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(propertyName, value);
		return this.findPageWithOrder(null, map, orderBy, page);
	}

	@Override
	public PageResult<VO> findPageWithOrderByProperty(String propertyName, Object value, String orderPropertyName,
			String orderByType, PageParam page) {
		String orderBy = this.makeOrderBy(orderPropertyName, orderByType);
		return this.findPageWithOrderByProperty(orderPropertyName, value, orderBy, page);
	}

	@Override
	public PageResult<VO> findPageByProperty(String propertyName, Object value, PageParam page) {
		return this.findPageWithOrderByProperty(propertyName, value, null, null, page);
	}

	protected String makeOrderBy(String[] orderPropertyNames, String[] orderByTypes) {
		if(null==orderPropertyNames) return null;
		String defaultType = "asc";
		StringBuilder sb = new StringBuilder();
		if(null==orderByTypes) {
			for(String name : orderPropertyNames) {
				sb.append(name).append(" ").append(defaultType).append(", ");
			}
		} else {
			Integer len1 = orderPropertyNames.length;
			Integer len2 = orderByTypes.length;
			for(Integer i=0;i<len1;i++) {
				sb.append(orderPropertyNames[i]).append(" ");
				if(i<len2) {
					sb.append(orderByTypes[i]).append(", ");
				} else {
					sb.append(defaultType).append(", ");
				}
			}
		}
		return sb.length()>0 ? sb.substring(0, sb.lastIndexOf(",")) : null;
	}
	
	protected String makeOrderBy(String orderPropertyName, String orderByType) {
		String orderBy = null;
		if(!EasyStringCheckUtils.isEmpty(orderPropertyName)) {
			if(!EasyStringCheckUtils.isEmpty(orderByType)) {
				orderBy = orderPropertyName + " " + orderByType;
			} else {
				orderBy = orderPropertyName + " asc";
			}
		}
		return orderBy;
	}
}

