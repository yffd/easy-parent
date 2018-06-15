package com.yffd.easy.uumc.pojo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:27:24 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UumcResourceTypeEnum {
	MENU("M", "菜单"),
	OPERATION("O", "操作"),
	;
	private String code;
	private String desc;
	private UumcResourceTypeEnum(String code, String desc) {
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
		EnumSet<UumcResourceTypeEnum> enumSet = EnumSet.allOf(UumcResourceTypeEnum.class);
        for (UumcResourceTypeEnum tmp : enumSet) {
        	map.put(tmp.getCode(), tmp.getDesc());
        }
        return map;
	}
}

