package com.yffd.easy.uupm.pojo.enums;

/**
 * @Description  租户类型枚举.
 * @Date		 2018年1月29日 下午3:32:20 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UupmTenantTypeEnum {
	OC("1", "运营中心"),
	ENTERPRISE("2", "企业"),
	PERSONAL("3", "个人"),
	OTHER_GROUP("4", "其它组织"),
	;
	private String code;
	private String text;
	private UupmTenantTypeEnum(String code, String text) {
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

