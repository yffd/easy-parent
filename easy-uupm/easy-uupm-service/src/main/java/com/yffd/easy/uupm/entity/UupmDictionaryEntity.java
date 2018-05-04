/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

/**
 * 
 * @Description  字典信息.
 * @Date		 2018年2月1日 上午9:33:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmDictionaryEntity extends UupmCommonTreeEntity {
	private static final long serialVersionUID = -1776588479450143490L;
	private String tenantCode = "dft";	//租户编号
	private String keyCode;
	private String keyName;
	private String valueContent;
	private String valueStatus;
	private Integer seqNo;
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.setNodeCode(keyCode);	// 设置nodeCode=keyCode
		this.keyCode = keyCode;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getValueContent() {
		return valueContent;
	}
	public void setValueContent(String valueContent) {
		this.valueContent = valueContent;
	}
	public String getValueStatus() {
		return valueStatus;
	}
	public void setValueStatus(String valueStatus) {
		this.valueStatus = valueStatus;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
//	@Override
//	public String getNodeCode() {
//		return keyCode;
//	}
	
	
}