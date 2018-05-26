package com.yffd.easy.framework.common.pojo.factory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.util.EasyNamingFormatUtils;
import com.yffd.easy.framework.common.util.PojoBeanUtils;

public class CommonPojoFactory {
	
	public <T> T pojo2pojo(Object origPojo, Class<T> destPojoClazz) {
		return this.copyProperties(origPojo, destPojoClazz);
	}
	
	public <T> T map2pojo(Map<String, Object> origMap, Class<T> destPojoClazz) {
		return this.map2pojo(origMap, destPojoClazz, null);
	}
	
	/**
	 * 
	 * @Date	2018年5月8日 下午1:58:35 <br/>
	 * @author  zhangST
	 * @param origMap
	 * @param destPojoClazz
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return
	 */
	public <T> T map2pojo(Map<String, Object> origMap, Class<T> destPojoClazz, Boolean column2property) {
		Map<String, Object> formatMap = new HashMap<String, Object>();
		Set<Entry<String, Object>> sets = origMap.entrySet();
		for(Entry<String, Object> set : sets) {
			String key = set.getKey();
			Object value = set.getValue();
			String propName = column2property(key);
			String tmpName = null;
			if(null==column2property) {
				tmpName = propName;
			} else if(column2property) {
				tmpName = column2property(propName);
			} else {
				tmpName = column2property(propName);
			}
			formatMap.put(tmpName, value);
		}
		return copyProperties(formatMap, destPojoClazz);
	}
	
	public Map<String, Object> pojo2map(Object origPojo) {
		return this.pojo2map(origPojo, null);
	}
	
	/**
	 * 标准javabean转换到Map类型，即对象属性值的复制。<br/>
	 * @Date	2018年5月8日 下午1:56:30 <br/>
	 * @author  zhangST
	 * @param origPojo
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return
	 */
	public Map<String, Object> pojo2map(Object origPojo, Boolean column2property) {
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(origPojo.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String propName = pd.getName();
				if("class".equals(propName)) continue;
				Method method = pd.getReadMethod();
				try {
					Object value = method.invoke(origPojo);
					String tmpName = null;
					if(null==column2property) {
						tmpName = propName;
					} else if(column2property) {
						tmpName = column2property(propName);
					} else {
						tmpName = property2column(propName);
					}
					retMap.put(tmpName, value);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new EasyCommonException("反射获取属性值失败，propName:" + propName, e);
				}
			}
			return retMap;
		} catch (IntrospectionException e) {
			throw new EasyCommonException("反射获取类信息失败，origPojo:" + origPojo, e);
		}
	}
	
	/**
	 * 
	 * @Date	2018年5月8日 下午1:57:53 <br/>
	 * @author  zhangST
	 * @param origMap
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return
	 */
	public Map<String, Object> map2map(Map<String, Object> origMap, Boolean column2property) {
		if(null==origMap || origMap.size()==0) return null;
		Map<String, Object> target = new HashMap<String, Object>();
		Set<String> keySet = origMap.keySet();
		for(String key : keySet) {
			String name = "";
			if(null==column2property) {
				name = key;
			} else if(column2property) {
				name = column2property(key);
			} else {
				name = column2property(key);
			}
			if(null==name || "".equals(name)) {
				continue;
			}
			target.put(name, origMap.get(key));
		}
		return target;
	}
	
	/**
	 * 标准javabean转换到Map类型，即对象属性值的复制。<br/>
	 * @Date	2018年5月8日 下午1:56:30 <br/>
	 * @author  zhangST
	 * @param origPojo
	 * @param destPojoClazz
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return
	 */
	public Map<String, Object> pojo2mapWithDiffProps(Object origPojo, Class<?> diffPojoClazz, Boolean column2property) {
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		Set<String> hashNames = PojoBeanUtils.getPropsName(diffPojoClazz);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(origPojo.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String propName = pd.getName();
				if(hashNames.contains(propName)) continue;	// 属性名相同跳过
				Method readMethod = pd.getReadMethod();
				Object value = null;
				try {
					value = readMethod.invoke(origPojo, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new EasyCommonException("反射获取属性值失败，propName:" + propName, e);
				}
				String tmpName = null;
				if(null==column2property) {
					tmpName = propName;
				} else if(column2property) {
					tmpName = column2property(propName);
				} else {
					tmpName = property2column(propName);
				}
				propertyMap.put(tmpName, value);
			}
		} catch (IntrospectionException e) {
			throw new EasyCommonException("反射获取bean信息失败，origPojo:" + origPojo.getClass().getName(), e);
		}
		return propertyMap;
	}
	
	
	/**
	 * 【实体对象的属性名称】转换成【数据库表中字段的名称】，默认是小驼峰格式-下划线格式（大写）。<br/>
	 * @Date	2017年12月14日 下午2:08:21 <br/>
	 * @author  zhangST
	 * @param attributeName		实体对象的属性名称
	 * @param objects
	 * @return
	 */
	protected String property2column(String attributeName, Object...objects) {
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
	protected String column2property(String columnName, Object...objects) {
		return EasyNamingFormatUtils.underline2camel(columnName, true, null, null);
	}
	
	protected <T> T copyProperties(Object orig, Class<T> destBeanClazz) {
		try {
			Object obj = destBeanClazz.newInstance();
			BeanUtils.copyProperties(obj, orig);
			return (T) obj;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new EasyCommonException("反射复制属性值失败["+orig.getClass()+" -> "+destBeanClazz.getName()+"]", e);
		}
	}
	
//	static {
//		ConvertUtils.register(new Converter() {
//			@Override
//			public <M> M convert(Class<M> type, Object value) {
//				if(Date.class.getName().equals(type.getName())
//						&& String.class.getName().equals(value.getClass().getName())) {
//					Date date = EasyDateUtils.parseToDate(value.toString());
//					return (M) date;
//				}
//				return (M) value;
//			}
//	    }, Date.class);
//	}
}
