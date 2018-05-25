package com.yffd.easy.common.core.tree.custom;

import java.io.Serializable;
import java.util.List;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月18日 上午11:21:01 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyCustomTree implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<? extends EasyCustomTree> children;
	
	public List<? extends EasyCustomTree> getChildren() {
		return children;
	}
	public void setChildren(List<? extends EasyCustomTree> children) {
		this.children = children;
	}
}

