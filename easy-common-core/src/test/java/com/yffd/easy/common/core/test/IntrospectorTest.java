package com.yffd.easy.common.core.test;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.yffd.easy.common.core.model.DemoPO;
import com.yffd.easy.common.core.util.EasyNamingFormatUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月14日 上午10:21:41 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class IntrospectorTest {

	@Test
	public void test123() throws IntrospectionException {
		Map<String, String> map = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(DemoPO.class);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor pd : pds) {
			map.put(pd.getName(), pd.getPropertyType().getName());
		}
		System.out.println(map);
	}
	
	@Test
	public void test() throws IntrospectionException {
		DemoPO po = new DemoPO();
//		po.setName("白牛");
//		po.setAge(3);
		
		BeanInfo beanInfo = Introspector.getBeanInfo(DemoPO.class);
		System.out.println(beanInfo);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		System.out.println(pds);
		EventSetDescriptor[] esds = beanInfo.getEventSetDescriptors();
		System.out.println(esds);
		BeanInfo[] bis = beanInfo.getAdditionalBeanInfo();
		System.out.println(bis);
		BeanDescriptor bd = beanInfo.getBeanDescriptor();
		System.out.println(bd);
		int num = beanInfo.getDefaultEventIndex();
		System.out.println(num);
		MethodDescriptor[] mds = beanInfo.getMethodDescriptors();
		System.out.println(mds);
		
		for(PropertyDescriptor pd : pds) {
			System.out.println("。。。。。。name:" + pd.getName());
			System.out.println("propertyType:" + pd.getPropertyType());
//			if("name".equals(pd.getName())) {
			if(this.nameFormat("NAME_").equals(pd.getName())) {
				Method method = pd.getWriteMethod();
				try {
					method.invoke(po, "小黑");
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(po.getUserName());
	}
	
	public String nameFormat(String attributeName, Object...objects) {
		return EasyNamingFormatUtils.underline2camel(attributeName, true, null, null);
	}
}

