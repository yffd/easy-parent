package com.yffd.easy.common.core.tree.model;

import java.util.List;

import com.yffd.easy.common.core.tree.data.EasyTreeData;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月11日 上午11:05:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyTestNode implements EasyTreeData {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	private List<EasyTestNode> children;
	
	public EasyTestNode() {
	}
	public EasyTestNode(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getIdValue() {
		return this.id;
	}
	@Override
	public String getPidValue() {
		return this.name;
	}
	@Override
	public List<?> getChildren() {
		return this.children;
	}
	@Override
	public void setChildren(List<?> children) {
		this.children = (List<EasyTestNode>) children;
		
	}
	
}

