package com.yffd.easy.common.core.util;

import java.lang.reflect.Type;
import java.util.Set;

import org.junit.Test;

import com.yffd.easy.common.core.model.A;
import com.yffd.easy.common.core.model.B;
import com.yffd.easy.common.core.model.DemoPO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月24日 下午1:56:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyReflectionUtilsTest {

	
	@Test
	public void test() {
		A a = new A();
		B b = new B();
		DemoPO po = new DemoPO();
		System.out.println(A.class.isInstance(po));
		
		Type type = B.class.getGenericSuperclass();
		System.out.println(type.getClass());
		System.out.println(B.class.isAssignableFrom(type.getClass()));
	}
	
	@Test
	public void invokePublicMethodTest() {
		try {
			A a = new A();
			B b = new B();
			EasyReflectionUtils.invokeMethod(b, "m5", null);
//			EasyReflectionUtils.invokePublicMethod(b, "m11", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void setPublicFieldValueTest() {
		try {
			A a = new A();
			B b = new B();
//			EasyReflectionUtils.setFieldValue(b, "ee", "qwe");
			EasyReflectionUtils.setPublicFieldValue(b, "bb", "qwe");
			System.out.println(b);
			System.out.println(b.bb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void getFieldValueTest() {
		try {
			A a = new A();
			B b = new B();
			Object value = EasyReflectionUtils.getPublicFieldValue(b, "bb1");
//			Object value = EasyReflectionUtils.getFieldValue(b, "bb1");
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFieldsTest() {
//		Set<String> names = EasyReflectionUtils.getAllPublicFieldNames(B.class);
		Set<String> names = EasyReflectionUtils.getAllFieldNames(B.class);
		for(String name : names) {
			System.out.println(name);
		}
	}
}

