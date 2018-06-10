package com.yffd.easy.uupm.pojo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  租户状态枚举.
 * @Date		 2018年1月29日 下午3:40:26 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UupmTenantServeStatusEnum {
	INIT("1", "初始化"),
	TRIALING("2", "试用中"),
	PRODUCING("3", "生产中"),
	EXPIRE("4", "已过期"),
	;
	private String code;
	private String desc;
	private UupmTenantServeStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		EnumSet<UupmAppSystemTypeEnum> enumSet = EnumSet.allOf(UupmAppSystemTypeEnum.class);
        for (UupmAppSystemTypeEnum tmp : enumSet) {
        	map.put(tmp.getCode(), tmp.getDesc());
        }
        return map;
	}
}

