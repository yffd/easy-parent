package com.yffd.easy.uupm.pojo.enums;

/**
 * @Description  应用系统类型枚举.
 * @Date		 2018年1月29日 下午3:40:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UupmApplicationTypeEnum {
	BASE("0", "基础系统"),
	INNER("1", "内部系统"),
	OUTER("2", "外部系统"),
	;
	private String code;
	private String text;
	private UupmApplicationTypeEnum(String code, String text) {
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

