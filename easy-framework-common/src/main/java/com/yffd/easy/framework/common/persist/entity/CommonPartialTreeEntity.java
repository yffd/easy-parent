/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.framework.common.persist.entity;

import java.io.Serializable;
import java.util.List;

import com.yffd.easy.common.core.pojo.IPOJO;

/**
 * 
 * @Description  偏序树信息.
 * @Date		 2018年2月1日 上午9:28:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class CommonPartialTreeEntity implements IPOJO, Serializable {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = -8675090571816330315L;
	private String treeId;			// 树ID
	private Integer nodeLayer;		// 节点层号
	private Integer nodeLeft;		// 节点左编号，主要用于偏序遍历子节点
	private Integer nodeRight;		// 节点右编号，主要用于偏序遍历子节点
	private String nodeCode;		// 节点编号
	private String parentCode;		// 父节点编号
	
	private List<CommonPartialTreeEntity> children;
	
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public Integer getNodeLayer() {
		return nodeLayer;
	}
	public void setNodeLayer(Integer nodeLayer) {
		this.nodeLayer = nodeLayer;
	}
	public Integer getNodeLeft() {
		return nodeLeft;
	}
	public void setNodeLeft(Integer nodeLeft) {
		this.nodeLeft = nodeLeft;
	}
	public Integer getNodeRight() {
		return nodeRight;
	}
	public void setNodeRight(Integer nodeRight) {
		this.nodeRight = nodeRight;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public List<CommonPartialTreeEntity> getChildren() {
		return children;
	}
	public void setChildren(List<CommonPartialTreeEntity> children) {
		this.children = children;
	}
	
}