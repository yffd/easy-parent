package com.yffd.easy.uupm.pojo.enums;

/**
 * @Description  租户服务类型枚举.
 * @Date		 2018年1月29日 下午3:40:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UupmCommonEnum {
	ACTIVE("1", "激活"),
	INACTIVE("0", "冻结"),
	;
	private String code;
	private String text;
	private UupmCommonEnum(String code, String text) {
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

