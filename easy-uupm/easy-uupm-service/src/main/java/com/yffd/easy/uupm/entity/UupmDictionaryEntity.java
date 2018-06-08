/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  字典信息.
 * @Date		 2018年6月8日 下午2:38:04 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmDictionaryEntity extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String keyName;
	private String keyCode;
	private String parentCode;
	private String keyType;
	private String keyValue;
	private Integer keyNo;
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public Integer getKeyNo() {
		return keyNo;
	}
	public void setKeyNo(Integer keyNo) {
		this.keyNo = keyNo;
	}
	
}