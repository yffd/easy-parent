package com.yffd.easy.common.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

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

	public static <E> E copyProperties(Object source, Class<?> targetClass) throws ReflectiveOperationException, EasyCommonException {
		if(null==source) throw new EasyCommonException("原始对象未指定"); 
		if(null==targetClass) throw new EasyCommonException("目标对象类未指定"); 
		try {
			Object obj = targetClass.newInstance();
			ConvertUtils.register(new Converter() {
				@Override
				public <T> T convert(Class<T> type, Object value) {
					if(String.class.getName().equals(type.getName())) {
						Date date = EasyDateUtils.parseToDate(value.toString());
						return (T) date;
					}
					return (T) value;
				}
	        }, Date.class);
			BeanUtils.copyProperties(obj, source);
			return (E) obj;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw e;
		}
	}
	
	
	public static Map<String, Object> copyDiffProperties(Object source, Class<?> targetClass) throws Exception {
		if(null==source) throw new EasyCommonException("原始对象未指定"); 
		if(null==targetClass) throw new EasyCommonException("目标对象类未指定"); 
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		Set<String> hashNames = EasyReflectionUtils.getAllFieldNames(targetClass);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String propName = pd.getName();
				if(hashNames.contains(propName)) continue;
				Method readMethod = pd.getReadMethod();
				Object value = readMethod.invoke(source, null);
				propertyMap.put(propName, value);
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw e;
		}
		return propertyMap;
	}
	
	
}

