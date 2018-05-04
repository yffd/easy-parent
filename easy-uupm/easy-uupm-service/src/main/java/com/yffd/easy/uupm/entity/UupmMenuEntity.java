/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

/**
 * 
 * @Description  菜单信息.
 * @Date		 2018年2月1日 上午9:43:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmMenuEntity extends UupmCommonEntity {
	
	private static final long serialVersionUID = -7089319244476271520L;
	private String menuCode;
	private String parentCode;
	private String menuName;
	private String menuIcons;
	private String menuType;			//菜单类型：上导航菜单=nav_top、上导航菜单=nav_left
	private String accessType;			//菜单访问方式：url访问、新窗口=_blank
	private int menuSeqNo;				//菜单序号
	
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
	public String getMenuIcons() {
		return menuIcons;
	}
	public void setMenuIcons(String menuIcons) {
		this.menuIcons = menuIcons;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public int getMenuSeqNo() {
		return menuSeqNo;
	}
	public void setMenuSeqNo(int menuSeqNo) {
		this.menuSeqNo = menuSeqNo;
	}
	
}