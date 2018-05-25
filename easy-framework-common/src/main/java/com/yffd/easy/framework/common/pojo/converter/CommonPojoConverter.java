package com.yffd.easy.framework.common.pojo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.util.EasyJavaBeanUtils;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月7日 下午2:14:24 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonPojoConverter {

	private static CommonPojoConverter instance;
	
	private CommonPojoConverter() {
		
	}
	
	public static synchronized CommonPojoConverter getInstance() {
		if(null==instance) instance = new CommonPojoConverter();
		return instance;
	}
	
	public <T> T convert2BeanWithSameProps(Object bean, Class<T> targetClazz) {
		if(null==bean || null==targetClazz) return null;
		if(targetClazz.isInstance(bean)) return (T) bean;
		
		try {
			return EasyJavaBeanUtils.copyProperties(bean, targetClazz);
		} catch (Exception e) {
			throw new EasyCommonException("copy the same properties to other bean error! ["+bean.getClass()+" -> "+targetClazz+"]", e);
		}
	}
	
	public <T> List<T> convert2BeanListWithSameProps(List<?> beanList, Class<T> targetClazz) {
		if(null==beanList || beanList.isEmpty()) return null;
		Object obj = beanList.get(0);
		if(null==obj || null==targetClazz) return null;
		if(targetClazz.isInstance(obj)) return (List<T>) beanList;
		
		List<T> targetList = new ArrayList<T>();
		for(Object bean : beanList) {
			T target = this.convert2BeanWithSameProps(bean, targetClazz);
			targetList.add(target);
		}
		return targetList;
	}
	
	public <T> Map<String, Object> convert2MapWithSamePropes(Object bean, Class<T> targetClazz) {
		if(null==bean || null==targetClazz) return null;
		try {
			return EasyJavaBeanUtils.copy2mapWithSameProperties(bean, targetClazz);
		} catch (Exception e) {
			throw new EasyCommonException("copy the same properties to map error! ["+bean.getClass()+" -> "+targetClazz+"]", e);
		}
	}
	
	public <T> Map<String, Object> convert2MapWithDiffProps(Object bean, Class<T> targetClazz) {
		if(null==bean || null==targetClazz) return null;
		try {
			return EasyJavaBeanUtils.copy2mapWithDiffProperties(bean, targetClazz);
		} catch (Exception e) {
			throw new EasyCommonException("copy the different properties to map error! ["+bean.getClass()+" -> "+targetClazz+"]", e);
		}
	}
	
	public <T> PageResult<T> convert2PaginationWithSameProps(PageResult<?> pagination, Class<T> targetClazz) {
		if(null==pagination || null==targetClazz) return null;
		List<?> recordList = pagination.getRecordList();
		if(null==recordList || recordList.size()==0) {
			PageResult<T> targetPagination = new PageResult<T>();
			targetPagination.setPageParam(pagination.getPageParam());
			return targetPagination;
		} else {
			Object tmp = recordList.get(0);
			if(targetClazz.isInstance(tmp)) {
				return (PageResult<T>) pagination;
			} else {
				List<T> targetList = this.convert2BeanListWithSameProps(recordList, targetClazz);
				PageResult<T> targetPagination = new PageResult<T>();
				targetPagination.setPageParam(pagination.getPageParam());
				targetPagination.setRecordList(targetList);
				return targetPagination;
			}
		}
	}
	
	
	
	/**
	 * Map类型内容转换到自定义数据模型，即对象属性值的复制。注：对集合类型不支持，即targetBeanClazz不能为集合类型。<br/>
	 * Map的key名称与自定义数据模型对象的属性名称映射规则，默认为：下划线格式 -》 小驼峰格式（大写）。<br/>
	 * @Date	2017年12月15日 上午11:13:06 <br/>
	 * @author  zhangST
	 * @param sourceMap			源对象
	 * @param targetBeanClazz	要转换的目标对象的类对象
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return					目标对象
	 */
	public <T> T convert2BeanWithFmtKeyName(Map<String, Object> sourceMap, Class<T> targetBeanClazz, Boolean column2property) {
		return EasyJavaBeanUtils.map2beanWithFmtKeyName(sourceMap, targetBeanClazz, column2property);
	}
	
	/**
	 * Map类型内容转换到自定义数据模型，即对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * Map的key名称与自定义数据模型对象的属性名称映射规则，默认为：下划线格式 -》 小驼峰格式（大写）。<br/>
	 * @Date	2017年12月15日 上午11:27:24 <br/>
	 * @author  zhangST
	 * @param sourceList		源对象
	 * @param targetBeanClazz	要转换的目标对象的类对象
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return					目标对象
	 */
	public <T> List<T> convert2BeanListWithFmtKeyName(List<Map<String, Object>> sourceList, Class<T> targetBeanClazz, Boolean column2property) {
		if(null==sourceList || sourceList.size()==0 || null==targetBeanClazz) return null;
		List<T> list = new ArrayList<T>();
		for(Map<String, Object> map : sourceList) {
			T obj = this.convert2BeanWithFmtKeyName(map, targetBeanClazz, column2property);
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * Map类型内容转换到自定义数据模型，即对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * Map的key名称与自定义数据模型对象的属性名称映射规则，默认为：下划线格式 -》 小驼峰格式（大写）。<br/>
	 * @Date	2017年12月15日 上午11:28:07 <br/>
	 * @author  zhangST
	 * @param sourcePagination		源对象
	 * @param targetBeanClass		要转换的目标对象的类对象
	 * @param column2property		Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 								null：	不做任何key名称的转换，<br/>
	 * 								true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 								false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return						目标对象
	 */
	public <T> PageResult<T> convert2PaginationWithSameProps(PageResult<Map<String, Object>> sourcePagination, Class<T> targetBeanClass, Boolean column2property) {
		if(null==sourcePagination || null==targetBeanClass) return null;
		List<T> list = this.convert2BeanListWithFmtKeyName(sourcePagination.getRecordList(), targetBeanClass, column2property);
		PageResult<T> _pageResult = new PageResult<T>();
		_pageResult.setRecordList(list);
		_pageResult.setPageParam(sourcePagination.getPageParam());
		return _pageResult;
	}
	
	
}

