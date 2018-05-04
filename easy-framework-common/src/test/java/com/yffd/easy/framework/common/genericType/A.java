package com.yffd.easy.framework.common.genericType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description简单描述该类的功能（可选）.
 * @Date		 2018年4月26日 上午11:41:20 <br/>
 * @author zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class A<T> {
	private T obj;
	
	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	/** 
	 * 获取T的实际类型 
	 */
	public void testClassA() throws NoSuchFieldException, SecurityException {
		System.out.print("getSuperclass:");
		System.out.println(this.getClass().getSuperclass().getName());
		System.out.print("getGenericSuperclass:");
		Type t = this.getClass().getGenericSuperclass();
		System.out.println(t);
		if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
			System.out.print("getActualTypeArguments:");
			for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
				System.out.print(t1 + ",");
			}
			System.out.println();
		}
		
		System.out.println("----------------------");
		
		Type[] tArrs = this.getClass().getGenericInterfaces();
		for(Type tOne : tArrs) {
			for (Type t2 : ((ParameterizedType) tOne).getActualTypeArguments()) {
				System.out.print(t2 + ",");
			}
		}
		System.out.println();
	}
}

