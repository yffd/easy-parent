package com.yffd.easy.uupm.pojo.vo.easyui;

import com.yffd.easy.common.core.tree.custom.EasyCustomTree;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年10月18日 上午11:53:05 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmUIBaseTreeVo extends EasyCustomTree {

	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String iconCls = "icon-save";
	private String state = "open";	//closed、open
	private boolean selected = false;
	private boolean checked = false;
	
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
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}

