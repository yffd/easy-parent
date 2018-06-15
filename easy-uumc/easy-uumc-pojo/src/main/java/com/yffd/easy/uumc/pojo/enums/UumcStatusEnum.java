package com.yffd.easy.uumc.pojo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:29:50 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UumcStatusEnum {
	ACTIVE("1", "有效"), 
	INACTIVE("0", "无效");
	
	private String code;
	private String desc;
	
	private UumcStatusEnum(String code, String desc) {
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
		EnumSet<UumcStatusEnum> enumSet = EnumSet.allOf(UumcStatusEnum.class);
        for (UumcStatusEnum tmp : enumSet) {
        	map.put(tmp.getCode(), tmp.getDesc());
        }
        return map;
	}
}

