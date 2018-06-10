package com.yffd.easy.uupm.web.model;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午5:05:59 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmTreeOrganizationVo extends UupmTreeVo {
	
	private static final long serialVersionUID = 1L;
	private String orgName;			//父机构编号
	private String orgCode;			//机构编号
	private String parentCode;		//父机构编号
	private String parentName;		//父机构名称
	private String treeId;
	private Integer seqNo;			//序号
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
}

