package com.yffd.easy.uumc.pojo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:26:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public enum UumcAppSystemTypeEnum {
	LOCAL("0", "本地系统"),
	INNER("1", "内部系统"),
	OUTER("2", "外部系统"),
	;
	private String code;
	private String desc;
	private UumcAppSystemTypeEnum(String code, String desc) {
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
		EnumSet<UumcAppSystemTypeEnum> enumSet = EnumSet.allOf(UumcAppSystemTypeEnum.class);
        for (UumcAppSystemTypeEnum tmp : enumSet) {
        	map.put(tmp.getCode(), tmp.getDesc());
        }
        return map;
	}
}

