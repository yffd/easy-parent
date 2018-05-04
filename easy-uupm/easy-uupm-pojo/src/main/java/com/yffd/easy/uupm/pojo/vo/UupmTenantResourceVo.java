package com.yffd.easy.uupm.pojo.vo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月28日 上午10:28:44 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmTenantResourceVo extends UupmCommonVo {
	private static final long serialVersionUID = -59873092927524621L;

	private String rsCode;
	private String rsName;
	private String rsStatus;
	private String shortUrl;
	private String rsType;
	private String parentCode;
	private String seqNo;
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
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
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getRsType() {
		return rsType;
	}
	public void setRsType(String rsType) {
		this.rsType = rsType;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
}

