package com.yffd.easy.framework.common.service.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.common.core.util.EasyJavaBeanUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月26日 下午3:35:22 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonServiceSupport {
	
	protected <E> E createEntityWithSameProperties(Object bean, Class<E> entityClazz) {
		if(null==bean || null==entityClazz) return null;
		if(entityClazz.isInstance(bean)) return (E) bean;
		
		try {
			return EasyJavaBeanUtils.copyProperties(bean, entityClazz);
		} catch (Exception e) {
			throw new EasyCommonException("copy the same properties to entity error!", e);
		}
	}
	
	protected <E> E createEntityWithSameProperties(Object bean, Map<String, Object> diffPropertiesMap, Class<E> entityClazz) {
		if(null==bean || null==entityClazz) return null;
		if(entityClazz.isInstance(bean)) return (E) bean;
		
		Map<String, Object> diffProperties = this.createMapWithDiffProperties(bean, entityClazz);
		if(null!=diffProperties && !diffProperties.isEmpty()) {
			if(null==diffPropertiesMap) diffPropertiesMap = new HashMap<String, Object>();
			diffPropertiesMap.putAll(diffProperties);
		}
		return this.createEntityWithSameProperties(bean, entityClazz);
	}
	
	protected <E> List<E> createEntityWithSameProperties(List<?> beanList, Class<E> entityClazz) {
		if(null==beanList || beanList.isEmpty()) return null;
		Object bean = beanList.get(0);
		if(null==bean || null==entityClazz) return null;
		if(entityClazz.isInstance(bean)) return (List<E>) beanList;
		
		List<E> entityList = new ArrayList<E>();
		for(Object tmpObj : beanList) {
			E obj = this.createEntityWithSameProperties(tmpObj, entityClazz);
			entityList.add(obj);
		}
		return entityList;
	}
	
	private <E> Map<String, Object> createMapWithDiffProperties(Object bean, Class<E> entityClazz) {
		if(null==bean || null==entityClazz) return null;
		try {
			return EasyJavaBeanUtils.copyDiffProperties(bean, entityClazz);
		} catch (Exception e) {
			throw new EasyCommonException("copy the different properties to map error!", e);
		}
	}
	
	
	
	protected <V> V createVoWithSameProperties(Object bean, Class<V> voClazz) {
		if(null==bean || null==voClazz) return null;
		if(voClazz.isInstance(bean)) return (V) bean;
		
		try {
			if(voClazz.isInstance(bean)) return (V) bean;
			return EasyJavaBeanUtils.copyProperties(bean, voClazz);
		} catch (Exception e) {
			throw new EasyCommonException("po convert to vo error!", e);
		}
	}
	
	protected <V> List<V> createVoListWithSameProperties(List<?> beanList, Class<V> voClazz) {
		if(null==beanList || beanList.isEmpty()) return null;
		Object bean = beanList.get(0);
		if(null==bean || null==voClazz) return null;
		if(voClazz.isInstance(bean)) return (List<V>) beanList;
		
		List<V> voList = new ArrayList<V>();
		for(Object tmpObj : beanList) {
			V obj = this.createEntityWithSameProperties(tmpObj, voClazz);
			voList.add(obj);
		}
		return voList;
	}
	
	protected <V> PageResult<V> createVoPageWithSameProperties(PageResult<?> objPage, Class<V> voClazz) {
		if(null==objPage || null==voClazz) return null;
		List<?> recordList = objPage.getRecordList();
		if(null==recordList || recordList.size()==0) {
			PageResult<V> voPage = new PageResult<V>();
			voPage.setPageParam(objPage.getPageParam());
			return voPage;
		} else {
			Object tmp = recordList.get(0);
			if(voClazz.isInstance(tmp)) {
				return (PageResult<V>) objPage;
			} else {
				List<V> voList = this.createVoListWithSameProperties(recordList, voClazz);
				PageResult<V> voPage = new PageResult<V>();
				voPage.setPageParam(objPage.getPageParam());
				voPage.setRecordList(voList);
				return voPage;
			}
		}
	}
	
		
}

