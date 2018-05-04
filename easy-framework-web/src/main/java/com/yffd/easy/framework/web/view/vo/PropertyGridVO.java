package com.yffd.easy.framework.web.view.vo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年10月13日 下午4:24:41 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PropertyGridVO {
	private String id;
	private String name;
	private String value;
	private String group;
	private String editor;
	
	public PropertyGridVO() {
	}
	
	public PropertyGridVO(String id, String name, String value, String group, String editor) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.group = group;
		this.editor = editor;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
}

