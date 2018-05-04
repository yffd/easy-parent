package com.yffd.easy.common.core.model;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月24日 下午1:53:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class B extends A {
	
	public static String aa = "aa -> B.class";
	protected static String bb1 = "bb1 -> B.class";
	
	public String cc = "cc -> B.class";
	public String dd1 = "dd1 -> B.class";
	
	protected String ee = "ee -> B.class";
	protected String ff1 = "ff1 -> B.class";
	
	private String gg = "gg -> B.class";
	private String hh1 = "hh1 -> B.class";
	
	String ii = "ii -> B.class";
	String jj1 = "jj1 -> B.class";
	
	public static String m1() {
		System.out.println("m1 -> B.class");
		return "m1 -> A.class";
	}
	private static String m2() {
		System.out.println("m2 -> B.class");
		return "m2 -> A.class";
	}
	public String m3() {
		System.out.println("m3 -> B.class");
		return "m3 -> A.class";
	}
	private String m4() {
		System.out.println("m4 -> B.class");
		return "m4 -> B.class";
	}
	
	@Override
	public String toString() {
		return "B [cc=" + cc + ", dd1=" + dd1 + ", ee=" + ee + ", ff1=" + ff1 + ", gg=" + gg + ", hh1=" + hh1 + ", ii="
				+ ii + ", jj1=" + jj1 + "]";
	}
	
	
	
}

