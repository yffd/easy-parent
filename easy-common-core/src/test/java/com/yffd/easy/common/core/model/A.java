package com.yffd.easy.common.core.model;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月24日 下午1:51:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class A {

	public static String aa = "aa -> A.class";
	public static String bb = "bb -> A.class";
	
	public String cc = "cc -> A.class";
	public String dd = "dd -> A.class";
	
	protected String ee = "ee -> A.class";
	protected String ff = "ff -> A.class";
	
	private String gg = "gg -> A.class";
	private String hh = "hh -> A.class";
	
	String ii = "ii -> A.class";
	String jj = "jj -> A.class";
	
	public static String m1() {
		System.out.println("m1 -> A.class");
		return "m1 -> A.class";
	}
	public static String m11() {
		System.out.println("m11 -> A.class");
		return "m11 -> A.class";
	}
	private static String m22() {
		System.out.println("m22 -> A.class");
		return "m22 -> A.class";
	}
	public String m3() {
		System.out.println("m3 -> A.class");
		return "m3 -> A.class";
	}
	private String m44() {
		System.out.println("m44 -> A.class");
		return "m44 -> A.class";
	}
	public String m5() {
		System.out.println("m5 -> A.class");
		return "m5 -> A.class";
	}
	
	@Override
	public String toString() {
		return "A [cc=" + cc + ", dd=" + dd + ", ee=" + ee + ", ff=" + ff + ", gg=" + gg + ", hh=" + hh + ", ii=" + ii
				+ ", jj=" + jj + "]";
	}
	
	
}

