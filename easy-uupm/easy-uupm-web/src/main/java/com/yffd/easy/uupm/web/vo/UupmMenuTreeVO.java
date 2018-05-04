package com.yffd.easy.uupm.web.vo;

import com.yffd.easy.framework.web.view.vo.ComboTreeVO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年3月9日 上午11:46:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmMenuTreeVO extends ComboTreeVO {
	private String nodeLabel;		// 节点标签，目前主要用于多棵树的区分
	private String menuCode;		// 编号
	private String menuName;		// 名称
	private String parentCode;		// 父编号
	private String parentName;		// 父名称
	private String menuUrl;			// 菜单链接
	private String menuIcons;		// 菜单图标
	
	public String getNodeLabel() {
		return nodeLabel;
	}
	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuIcons() {
		return menuIcons;
	}
	public void setMenuIcons(String menuIcons) {
		this.menuIcons = menuIcons;
	}
	
}

