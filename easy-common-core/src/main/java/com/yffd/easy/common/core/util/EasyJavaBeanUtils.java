package com.yffd.easy.common.core.util;

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

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月12日 下午2:44:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyJavaBeanUtils {

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
//        }, Date.class);
//	}
	
	public static <T> T copyProperties(Object orig, Class<T> destBeanClazz) {
		try {
			Object obj = destBeanClazz.newInstance();
			BeanUtils.copyProperties(obj, orig);
			return (T) obj;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new EasyCommonException("反射复制属性值失败["+orig.getClass()+" -> "+destBeanClazz+"]", e);
		}
	}
	
	public static <T> T bean2bean(Object origBean, Class<T> destBeanClazz) {
		return copyProperties(origBean, destBeanClazz);
	}
	
	public static <T> T map2bean(Map<String, Object> origMap, Class<T> destBeanClazz) {
		return copyProperties(origMap, destBeanClazz);
	}
	
	public static Map<String, Object> bean2map(Object origBean) {
		return bean2mapWithFmtKeyName(origBean, null);
	}
	
	
	public static Map<String, Object> copy2mapWithDiffProperties(Object origBean, Class<?> destBeanClazz) {
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		Set<String> hashNames = EasyReflectionUtils.getAllFieldNames(destBeanClazz);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(origBean.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String propName = pd.getName();
				if(hashNames.contains(propName)) continue;	// 属性名相同跳过
				Method readMethod = pd.getReadMethod();
				Object value = null;
				try {
					value = readMethod.invoke(origBean, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new EasyCommonException("反射获取属性值失败，name:" + pd.getName(), e);
				}
				propertyMap.put(propName, value);
			}
		} catch (IntrospectionException e) {
			throw new EasyCommonException("反射获取bean信息失败，origBean:" + origBean.getClass().getName(), e);
		}
		return propertyMap;
	}
	
	public static Map<String, Object> copy2mapWithSameProperties(Object origBean, Class<?> destBeanClazz) {
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		Set<String> hashNames = EasyReflectionUtils.getAllFieldNames(destBeanClazz);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(origBean.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String propName = pd.getName();
				if(!hashNames.contains(propName)) continue;	// 属性名不相同跳过
				Method readMethod = pd.getReadMethod();
				Object value = null;
				try {
					value = readMethod.invoke(origBean, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new EasyCommonException("反射获取属性值失败，name:" + pd.getName(), e);
				}
				propertyMap.put(propName, value);
			}
		} catch (IntrospectionException e) {
			throw new EasyCommonException("反射获取bean信息失败，origBean:" + origBean.getClass().getName(), e);
		}
		return propertyMap;
	}
	
	/**
	 * 
	 * @Date	2018年5月8日 下午1:58:35 <br/>
	 * @author  zhangST
	 * @param origMap
	 * @param destBeanClazz
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return
	 */
	public static <T> T map2beanWithFmtKeyName(Map<String, Object> origMap, Class<T> destBeanClazz, Boolean column2property) {
		Map<String, Object> formatMap = new HashMap<String, Object>();
		Set<Entry<String, Object>> sets = origMap.entrySet();
		for(Entry<String, Object> set : sets) {
			String key = set.getKey();
			Object value = set.getValue();
			String name = column2property(key);
			String tmpName = null;
			if(null==column2property) {
				tmpName = name;
			} else if(column2property) {
				tmpName = column2property(name);
			} else {
				tmpName = column2property(name);
			}
			formatMap.put(tmpName, value);
		}
		try {
			return EasyJavaBeanUtils.copyProperties(formatMap, destBeanClazz);
		} catch (EasyCommonException e) {
			throw new EasyCommonException("反射获取类信息失败，origMap:" + origMap.getClass().getName(), e);
		}
	}
	
	/**
	 * 标准javabean转换到Map类型，即对象属性值的复制。<br/>
	 * @Date	2018年5月8日 下午1:56:30 <br/>
	 * @author  zhangST
	 * @param origBean
	 * @param column2property	Map对象的key名称转换方式，默认是小驼峰格式-下划线格式（大写）间的转换，<br/>
	 * 							null：	不做任何key名称的转换，<br/>
	 * 							true：	下划线 -> 驼峰，例如：【数据库表中字段的名称】转换成【实体对象的属性名称】，<br/>
	 * 							false：	驼峰 -> 下划线，例如：【实体对象的属性名称】转换成【数据库表中字段的名称】，<br/>
	 * @return
	 */
	public static Map<String, Object> bean2mapWithFmtKeyName(Object origBean, Boolean column2property) {
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(origBean.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if("class".equals(name)) continue;
				Method method = pd.getReadMethod();
				try {
					Object value = method.invoke(origBean);
					String tmpName = null;
					if(null==column2property) {
						tmpName = name;
					} else if(column2property) {
						tmpName = column2property(name);
					} else {
						tmpName = property2column(name);
					}
					retMap.put(tmpName, value);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new EasyCommonException("反射获取属性值失败，name:" + pd.getName(), e);
				}
			}
			return retMap;
		} catch (IntrospectionException e) {
			throw new EasyCommonException("反射获取类信息失败，origBean:" + origBean, e);
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
	public static Map<String, Object> map2mapWithFmtKeyName(Map<String, Object> origMap, Boolean column2property) {
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
	 * 【实体对象的属性名称】转换成【数据库表中字段的名称】，默认是小驼峰格式-下划线格式（大写）。<br/>
	 * @Date	2017年12月14日 下午2:08:21 <br/>
	 * @author  zhangST
	 * @param attributeName		实体对象的属性名称
	 * @param objects
	 * @return
	 */
	public static String property2column(String attributeName, Object...objects) {
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
	public static String column2property(String columnName, Object...objects) {
		return EasyNamingFormatUtils.underline2camel(columnName, true, null, null);
	}
	
	
}

