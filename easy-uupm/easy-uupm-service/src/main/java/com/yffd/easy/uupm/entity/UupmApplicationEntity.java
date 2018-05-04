/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

/**
 * 
 * @Description  应用系统信息.
 * @Date		 2018年2月1日 上午9:28:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmApplicationEntity extends UupmCommonEntity {
	
	private static final long serialVersionUID = -3316333314800573791L;
	
	private String appCode;			//应用系统编号
	private String appDomain;		//应用系统域名
	private String appPort;			//应用系统端口
	private String appContextPath;	//应用系统上下文路径
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
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
	public String getAppContextPath() {
		return appContextPath;
	}
	public void setAppContextPath(String appContextPath) {
		this.appContextPath = appContextPath;
	}
}