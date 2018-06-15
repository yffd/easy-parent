package com.yffd.easy.uumc.web.model;

import com.yffd.easy.common.core.tree.custom.EasyCustomTree;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月13日 下午4:24:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CombotreeModel extends EasyCustomTree {
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String state;
	private String checked;
	private String attributes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
}

