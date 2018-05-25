package com.yffd.easy.common.core.tree.node;

import java.util.List;

import com.yffd.easy.common.core.tree.EasyTreeBuilderAbastract;
import com.yffd.easy.common.core.tree.custom.EasyCustomTree;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月11日 上午10:43:19 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class EasyTreeNodeBuilder<N extends EasyTreeNode, T extends EasyCustomTree> extends EasyTreeBuilderAbastract<N, T> {

	@Override
	protected Object getIdValue(N node) {
		return node.getIdValue();
	}

	@Override
	protected Object getPidValue(N node) {
		return node.getPidValue();
	}

	@Override
	protected List<T> getChildren(T node) {
		return (List<T>) node.getChildren();
	}

	@Override
	protected void setChildren(T node, List<T> children) {
		node.setChildren(children);
	}


}

