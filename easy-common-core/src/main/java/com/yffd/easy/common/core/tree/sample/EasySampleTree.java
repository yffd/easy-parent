package com.yffd.easy.common.core.tree.sample;

import java.io.Serializable;
import java.util.List;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月14日 下午3:11:06 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasySampleTree implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object idValue;
	private Object pidValue;
	private Object nodeValue;
	private List<EasySampleTree> children;
	public Object getIdValue() {
		return idValue;
	}
	public void setIdValue(Object idValue) {
		this.idValue = idValue;
	}
	public Object getPidValue() {
		return pidValue;
	}
	public void setPidValue(Object pidValue) {
		this.pidValue = pidValue;
	}
	public Object getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(Object nodeValue) {
		this.nodeValue = nodeValue;
	}
	public List<EasySampleTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasySampleTree> children) {
		this.children = children;
	}
	
}

