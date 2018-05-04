package com.yffd.easy.framework.common.genericType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @Description简单描述该类的功能（可选）.
 * @Date		 2018年4月26日 上午11:42:38 <br/>
 * @author zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Test extends A<String> implements IA<B> {
	private List<String> list;
	private Map<String, Object> map;

	/***
	 * 获取List中的泛型
	 */
	public static void testList() throws NoSuchFieldException, SecurityException {
		Type t = Test.class.getDeclaredField("list").getGenericType();
		if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
			for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
				System.out.print(t1 + ",");
			}
			System.out.println();
		}
	}

	/***
	 * 获取Map中的泛型
	 */
	public static void testMap() throws NoSuchFieldException, SecurityException {
		Type t = Test.class.getDeclaredField("map").getGenericType();
		if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
			for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
				System.out.print(t1 + ",");
			}
			System.out.println();
		}
	}
	
	public static void test123() throws NoSuchFieldException, SecurityException {
		A<?> aa = new A<B>();
		Type t = aa.getClass().getGenericSuperclass();
		System.out.println(t);
		if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
			for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
				System.out.print(t1 + ",");
			}
			System.out.println();
		}
	}

	public static void main(String args[]) throws Exception {
		System.out.println(">>>>>>>>>>>testList>>>>>>>>>>>");
		testList();
		System.out.println("<<<<<<<<<<<testList<<<<<<<<<<<\n");
		System.out.println(">>>>>>>>>>>testMap>>>>>>>>>>>");
		testMap();
		System.out.println("<<<<<<<<<<<testMap<<<<<<<<<<<\n");
		System.out.println(">>>>>>>>>>>testClassA>>>>>>>>>>>");
		new Test().testClassA();
		System.out.println("<<<<<<<<<<<testClassA<<<<<<<<<<<");
		System.out.println(">>>>>>>>>>>test123>>>>>>>>>>>");
		new Test().test123();
		System.out.println("<<<<<<<<<<<test123<<<<<<<<<<<");
	}
}

