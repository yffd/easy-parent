package com.yffd.easy.uupm.pojo.vo;

import com.yffd.easy.uupm.pojo.base.UupmBasePojo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月28日 上午11:54:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmMenuInfoVo extends UupmBasePojo {
	private static final long serialVersionUID = -129579540759851462L;

	private String id;
	private String accessType;
	private String menuName;
	private String menuCode;
	private String parentCode;
	private String menuIcons;
	private String menuSeqNo;
	private String menuType;
	private String menuShortUrl;
	private String appContextPath;
	private String appDomain;
	private String appPort;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
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
	public String getMenuSeqNo() {
		return menuSeqNo;
	}
	public void setMenuSeqNo(String menuSeqNo) {
		this.menuSeqNo = menuSeqNo;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getMenuShortUrl() {
		return menuShortUrl;
	}
	public void setMenuShortUrl(String menuShortUrl) {
		this.menuShortUrl = menuShortUrl;
	}
	public String getAppContextPath() {
		return appContextPath;
	}
	public void setAppContextPath(String appContextPath) {
		this.appContextPath = appContextPath;
	}
	public String getAppDomain() {
		return appDomain;
	}
	public void setAppDomain(String appDomain) {
		this.appDomain = appDomain;
	}
	public String getAppPort() {
		return appPort;
	}
	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}
	
}

