package com.yffd.easy.common.core.tree.node;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月11日 上午11:05:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface EasyTreeNode extends Serializable {

	String getIdValue();
	String getPidValue();
	
}

