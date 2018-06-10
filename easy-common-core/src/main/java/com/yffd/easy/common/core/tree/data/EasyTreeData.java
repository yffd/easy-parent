package com.yffd.easy.common.core.tree.data;

import java.io.Serializable;
import java.util.List;

import com.yffd.easy.common.core.tree.custom.EasyCustomTree;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月11日 上午11:05:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface EasyTreeData extends Serializable {

	String getIdValue();
	
	String getPidValue();
	
	List<?> getChildren();
	
	void setChildren(List<?> children);
	
}

