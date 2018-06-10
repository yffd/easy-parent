package com.yffd.easy.uupm.pojo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  租户类型枚举.
 * @Date		 2018年1月29日 下午3:32:20 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UupmResourceTypeEnum {
	ENTERPRISE("M", "菜单"),
	PERSONAL("O", "操作"),
	OTHER_GROUP("A", "应用"),
	;
	private String code;
	private String desc;
	private UupmResourceTypeEnum(String code, String desc) {
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
		EnumSet<UupmResourceTypeEnum> enumSet = EnumSet.allOf(UupmResourceTypeEnum.class);
        for (UupmResourceTypeEnum tmp : enumSet) {
        	map.put(tmp.getCode(), tmp.getDesc());
        }
        return map;
	}
}

