package com.yffd.easy.uupm.entity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月3日 下午2:00:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmResourceEntity extends UupmCommonTreeEntity {

	private static final long serialVersionUID = 392536829366303556L;
	private String tenantCode = "dft";	//租户编号
	private String rsCode;
	private String rsName;
	private String rsStatus;
	private String rsType;
	private String shortUrl;
	private Integer seqNo;
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.setNodeCode(rsCode);	// 设置nodeCode=rsCode
		this.rsCode = rsCode;
	}
	public String getRsName() {
		return rsName;
	}
	public void setRsName(String rsName) {
		this.rsName = rsName;
	}
	public String getRsStatus() {
		return rsStatus;
	}
	public void setRsStatus(String rsStatus) {
		this.rsStatus = rsStatus;
	}
	public String getRsType() {
		return rsType;
	}
	public void setRsType(String rsType) {
		this.rsType = rsType;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
}

