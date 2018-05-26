package com.yffd.easy.framework.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import com.yffd.easy.common.core.exception.EasyCommonException;

public class PojoBeanUtils {

	public static Set<String> getPropsName(Class<?> pojoClazz) {
		try {
			Set<String> propsName = new HashSet<String>();
			BeanInfo beanInfo = Introspector.getBeanInfo(pojoClazz);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				propsName.add(pd.getName());
			}
			return propsName;
		} catch (IntrospectionException e) {
			throw new EasyCommonException("反射获取bean信息失败，pojoClazz:" + pojoClazz.getName(), e);
		}
	}
}
