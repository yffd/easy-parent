/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity.a;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * 
 * @Description  组织机构信息.
 * @Date		 2018年2月1日 上午9:45:35 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmOrganizationEntity extends UupmBasePojo {
	
	private static final long serialVersionUID = 1L;
	private String orgName;			//父机构编号
	private String orgCode;			//机构编号
	private String parentCode;		//父机构编号
	private String treeId;			//所属树
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