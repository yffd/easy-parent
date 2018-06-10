package com.yffd.easy.uupm.pojo.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月24日 下午2:57:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmTreeSegmentDataEntity extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String treeId;			// 树ID
	private transient Integer dataLeft;			// 节点左编号，主要用于偏序遍历子节点
	private transient Integer dataRight;		// 节点右编号，主要用于偏序遍历子节点
	private transient Integer dataLayer;		// 节点层号
	private String dataName;		// 节点名称
	private String dataCode;		// 节点编号
	private String parentCode;		// 父节点编号
	private String dataNo;			// 节点序号
	private String dataContent;		// 节点内容
	
	private transient Integer dataStep;		// 节点步移
	private transient Integer startLayer;		// 节点开始层号
	private transient Integer endLayer;		// 节点结束层号
	
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public Integer getDataLeft() {
		return dataLeft;
	}
	public void setDataLeft(Integer dataLeft) {
		this.dataLeft = dataLeft;
	}
	public Integer getDataRight() {
		return dataRight;
	}
	public void setDataRight(Integer dataRight) {
		this.dataRight = dataRight;
	}
	public Integer getDataLayer() {
		return dataLayer;
	}
	public void setDataLayer(Integer dataLayer) {
		this.dataLayer = dataLayer;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getDataContent() {
		return dataContent;
	}
	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}
	public Integer getDataStep() {
		return dataStep;
	}
	public void setDataStep(Integer dataStep) {
		this.dataStep = dataStep;
	}
	public Integer getStartLayer() {
		return startLayer;
	}
	public void setStartLayer(Integer startLayer) {
		this.startLayer = startLayer;
	}
	public Integer getEndLayer() {
		return endLayer;
	}
	public void setEndLayer(Integer endLayer) {
		this.endLayer = endLayer;
	}
	
}

