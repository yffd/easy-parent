package com.yffd.easy.uupm.pojo.enums;

/**
 * @Description  租户状态枚举.
 * @Date		 2018年1月29日 下午3:40:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UupmTenantStatusEnum {
	INIT("1", "初始化"),
	TEST("2", "试用中"),
	PRODUCING("3", "生产中"),
	EXPIRE("4", "已过期"),
	;
	private String code;
	private String text;
	private UupmTenantStatusEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}

