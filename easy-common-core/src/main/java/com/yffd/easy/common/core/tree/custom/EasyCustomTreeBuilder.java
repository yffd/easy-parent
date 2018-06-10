package com.yffd.easy.common.core.tree.custom;

import java.util.List;

import com.yffd.easy.common.core.tree.EasyTreeBuilderAbastract;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月18日 上午11:20:07 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class EasyCustomTreeBuilder<N, T extends EasyCustomTree> extends EasyTreeBuilderAbastract<N, T> {

	@Override
	public List<T> getChildren(T node) {
		return (List<T>) node.getChildren();
	}

	@Override
	public void setChildren(T node, List<T> children) {
		node.setChildren(children);
	}

}

