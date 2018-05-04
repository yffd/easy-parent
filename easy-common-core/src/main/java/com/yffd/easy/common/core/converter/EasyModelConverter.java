package com.yffd.easy.common.core.converter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.common.core.util.EasyJavaBeanUtils;
import com.yffd.easy.common.core.util.EasyNamingFormatUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月12日 下午5:28:55 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyModelConverter {

	/**************************** model to model start ******************************************************/

	/**
	 * 自定义数据模型间内容的转换，即自定义对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * @Date	2017年12月14日 下午2:02:04 <br/>
	 * @author  zhangST
	 * @param obj				源对象
	 * @param targetClass		要转换的目标对象的类对象
	 * @return					目标对象
	 */
	public <T> T model2model(Object obj, Class<?> targetClass) {
		if(null==obj || null==targetClass) return null;
		if(Map.class.isAssignableFrom(targetClass) || Collection.class.isAssignableFrom(targetClass)) {
			throw new EasyCommonException("数据模型转换失败，targetClass为集合类型不支持"); 
		}
		try {
			return EasyJavaBeanUtils.copyProperties(obj, targetClass);
		} catch (EasyCommonException | ReflectiveOperationException e) {
			throw new EasyCommonException("数据模型转换失败", e); 
		}
	}
	
	/**
	 * 自定义数据模型间内容的转换，即自定义对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * @Date	2017年12月14日 下午2:02:32 <br/>
	 * @author  zhangST
	 * @param listResult		源对象
	 * @param targetClass		要转换的目标对象的类对象
	 * @return					目标对象
	 */
	public <T> List<T> model2model(List<?> listResult, Class<?> targetClass) {
		if(null==listResult || listResult.size()==0 || null==targetClass) return null;
		if(Map.class.isAssignableFrom(targetClass) || Collection.class.isAssignableFrom(targetClass)) {
			throw new EasyCommonException("数据模型转换失败，targetClass为集合类型不支持"); 
		}
		List<T> list = new ArrayList<T>();
		for(Object obj : listResult) {
			T dto = this.model2model(obj, targetClass);
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 自定义数据模型间内容的转换，即自定义对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * @Date	2017年12月14日 下午2:02:55 <br/>
	 * @author  zhangST
	 * @param pageResult		源对象
	 * @param targetClass		要转换的目标对象的类对象
	 * @return					目标对象
	 */
	public <T> PageResult<T> model2model(PageResult<?> pageResult, Class<?> targetClass) {
		if(null==pageResult || null==targetClass) return null;
		if(Map.class.isAssignableFrom(targetClass) || Collection.class.isAssignableFrom(targetClass)) {
			throw new EasyCommonException("数据模型转换失败，targetClass为集合类型不支持"); 
		}
		List<T> list = this.model2model(pageResult.getRecordList(), targetClass);
		PageResult<T> _pageResult = new PageResult<T>();
		_pageResult.setRecordList(list);
		_pageResult.setPageParam(pageResult.getPageParam());
		return _pageResult;
	}
	
	/**************************** model to model end ******************************************************/
	/**************************** model to map end ******************************************************/
	/**
	 * 自定义数据模型内容转换到Map类型，即对象属性值的复制。注：对集合类型不支持，即obj不能为集合类型。<br/>
	 * 如果想改变映射规则，可重写方法：{@link EasyModelConverter#column2name} 和 {@link EasyModelConverter#name2column}
	 * @Date	2017年12月15日 下午1:57:24 <br/>
	 * @author  zhangST
	 * @param obj		源对象
	 * @param column2name		Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return			目标对象
	 */
	public Map<String, Object> model2map(Object obj, Boolean column2name) {
		if(null==obj) return null;
		if(obj instanceof Map || obj instanceof Collection) {
			throw new EasyCommonException("数据模型转换失败，obj为集合类型不支持"); 
		}
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if("class".equals(name)) continue;
				Method method = pd.getReadMethod();
				Object value;
				try {
					value = method.invoke(obj);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new EasyCommonException("反射属性获取值失败[getXxx()]，name:" + pd.getName(), e);
				}
				String tmpName = null;
				if(null==column2name) {
					tmpName = name;
				} else if(column2name) {
					tmpName = this.column2name(name);
				} else {
					tmpName = this.name2column(name);
				}
				retMap.put(tmpName, value);
			}
			return retMap;
		} catch (IntrospectionException e) {
			throw new EasyCommonException("Model到Map的属性值复制失败，obj:" + obj, e);
		}
	}
	
	/**************************** model to map end ******************************************************/
	/**************************** map to model start ******************************************************/
	
	/**
	 * Map类型内容转换到自定义数据模型，即对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * Map的key名称与自定义数据模型对象的属性名称映射规则，默认为：下划线格式 -》 小驼峰格式（大写）。<br/>
	 * 如果想改变映射规则，可重写方法：{@link EasyModelConverter#column2name} 和 {@link EasyModelConverter#name2column}
	 * @Date	2017年12月15日 上午11:13:06 <br/>
	 * @author  zhangST
	 * @param mapResult			源对象
	 * @param targetClass		要转换的目标对象的类对象
	 * @param column2name		Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return					目标对象
	 */
	public <T> T map2model(Map<String, Object> mapResult, Class<?> targetClass, Boolean column2name) {
		if(null==mapResult || null==targetClass) return null;
		if(Map.class.isAssignableFrom(targetClass) || Collection.class.isAssignableFrom(targetClass)) {
			throw new EasyCommonException("数据模型转换失败，targetClass为集合类型不支持"); 
		}
		Map<String, Object> formatMap = new HashMap<String, Object>();
		Set<Entry<String, Object>> sets = mapResult.entrySet();
		for(Entry<String, Object> set : sets) {
			String key = set.getKey();
			Object value = set.getValue();
			String name = this.column2name(key);
			String tmpName = null;
			if(null==column2name) {
				tmpName = name;
			} else if(column2name) {
				tmpName = this.column2name(name);
			} else {
				tmpName = this.name2column(name);
			}
			formatMap.put(tmpName, value);
		}
		try {
			return EasyJavaBeanUtils.copyProperties(formatMap, targetClass);
		} catch (EasyCommonException | ReflectiveOperationException e) {
			throw new EasyCommonException("数据模型转换失败", e); 
		}
	}
	
	/**
	 * Map类型内容转换到自定义数据模型，即对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * Map的key名称与自定义数据模型对象的属性名称映射规则，默认为：下划线格式 -》 小驼峰格式（大写）。<br/>
	 * 如果想改变映射规则，可重写方法：{@link EasyModelConverter#column2name} 和 {@link EasyModelConverter#name2column}
	 * @Date	2017年12月15日 上午11:27:24 <br/>
	 * @author  zhangST
	 * @param listResult		源对象
	 * @param targetClass		要转换的目标对象的类对象
	 * @param column2name		Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return					目标对象
	 */
	public <T> List<T> map2model(List<Map<String, Object>> listResult, Class<?> targetClass, Boolean column2name) {
		if(null==listResult || listResult.size()==0 || null==targetClass) return null;
		List<T> list = new ArrayList<T>();
		for(Map<String, Object> map : listResult) {
			T obj = this.map2model(map, targetClass, column2name);
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * Map类型内容转换到自定义数据模型，即对象属性值的复制。注：对集合类型不支持，即targetClass不能为集合类型。<br/>
	 * Map的key名称与自定义数据模型对象的属性名称映射规则，默认为：下划线格式 -》 小驼峰格式（大写）。<br/>
	 * 如果想改变映射规则，可重写方法：{@link EasyModelConverter#column2name} 和 {@link EasyModelConverter#name2column}
	 * @Date	2017年12月15日 上午11:28:07 <br/>
	 * @author  zhangST
	 * @param pageResult		源对象
	 * @param targetClass		要转换的目标对象的类对象
	 * @param column2name		Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return					目标对象
	 */
	public <T> PageResult<T> map2model(PageResult<Map<String, Object>> pageResult, Class<?> targetClass, Boolean column2name) {
		if(null==pageResult || null==targetClass) return null;
		List<T> list = this.map2model(pageResult.getRecordList(), targetClass, column2name);
		PageResult<T> _pageResult = new PageResult<T>();
		_pageResult.setRecordList(list);
		_pageResult.setPageParam(pageResult.getPageParam());
		return _pageResult;
	}
	
	
	/**************************** map to model end ******************************************************/
	/**************************** map to map end ******************************************************/
	/**
	 * Map类型间内容转换，即对象属性值的复制，主要用于Map的key名称的转换。<br/>
	 * 如果想改变映射规则，可重写方法：{@link EasyModelConverter#column2name} 和 {@link EasyModelConverter#name2column}
	 * @Date	2017年12月15日 上午11:31:55 <br/>
	 * @author  zhangST
	 * @param source		源对象
	 * @param column2name		Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return				目标对象
	 */
	public Map<String, Object> map2map(Map<String, Object> source, Boolean column2name) {
		if(null==source || source.size()==0) return null;
		Map<String, Object> target = new HashMap<String, Object>();
		Set<String> keySet = source.keySet();
		for(String key : keySet) {
			String name = "";
			if(null==column2name) {
				name = key;
			} else if(column2name) {
				name = this.column2name(key);
			} else {
				name = this.name2column(key);
			}
			if(null==name || "".equals(name)) {
				continue;
			}
			target.put(name, source.get(key));
		}
		return target;
	}
	
	/**
	 * 
	 * @Date	2018年3月15日 下午3:47:03 <br/>
	 * @author  zhangST
	 * @param source
	 * @param column2name
	 * @return
	 * @see #map2map(Map, Boolean)
	 */
	public List<Map<String, Object>> map2map(List<Map<String, Object>> source, Boolean column2name) {
		if(null==source || source.size()==0) return source;
		for(Map<String, Object> map : source) {
			map = this.map2map(map, column2name);
		}
		return source;
	}
	
	/**************************** map to map end ******************************************************/
	
	/**
	 * 【实体对象的属性名称】转换成【数据库表中字段的名称】，默认是小驼峰格式-下划线格式（大写）。<br/>
	 * @Date	2017年12月14日 下午2:08:21 <br/>
	 * @author  zhangST
	 * @param attributeName		实体对象的属性名称
	 * @param objects
	 * @return
	 */
	protected String name2column(String attributeName, Object...objects) {
		return EasyNamingFormatUtils.camel2underline(attributeName, true, null, null);
	}
	
	/**
	 * 【数据库表中字段的名称】转换成【实体对象的属性名称】，默认是小驼峰格式-下划线格式（大写）
	 * @Date	2017年12月14日 下午3:53:43 <br/>
	 * @author  zhangST
	 * @param columnName		数据库中表的字段名称
	 * @param objects
	 * @return
	 */
	protected String column2name(String columnName, Object...objects) {
		return EasyNamingFormatUtils.underline2camel(columnName, true, null, null);
	}
	
	
}

