package com.yffd.easy.common.core.tree.data;

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
public class EasyTreeDataBuilder<N extends EasyTreeData> extends EasyTreeBuilderAbastract<N, N> {

	@Override
	public Object getIdValue(N data) {
		return data.getIdValue();
	}

	@Override
	public Object getPidValue(N data) {
		return data.getPidValue();
	}

	@Override
	protected N getTreeNode(N data) {
		return data;
	}

	@Override
	public List<N> getChildren(N data) {
		return (List<N>) data.getChildren();
	}

	@Override
	public void setChildren(N data, List<N> children) {
		data.setChildren(children);
	}


}

