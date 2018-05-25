package com.yffd.easy.common.core.tree.sample;

import java.util.List;

import com.yffd.easy.common.core.tree.EasyTreeBuilderAbastract;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月17日 下午2:36:45 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class EasySampleTreeBuilder<N> extends EasyTreeBuilderAbastract<N, EasySampleTree> {

	@Override
	protected List<EasySampleTree> getChildren(EasySampleTree node) {
		return node.getChildren();
	}

	@Override
	protected void setChildren(EasySampleTree node, List<EasySampleTree> children) {
		node.setChildren(children);
	}
	
}

