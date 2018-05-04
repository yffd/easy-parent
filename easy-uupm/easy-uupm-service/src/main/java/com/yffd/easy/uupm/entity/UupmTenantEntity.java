/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import java.util.Date;

/**
 * 
 * @Description  租户信息.
 * @Date		 2018年2月1日 上午10:06:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmTenantEntity extends UupmCommonEntity {
	
	private static final long serialVersionUID = -1757336898289794470L;
	
	private String tenantCode;		//租户编号
	private String tenantName;		//租户名称
	private String tenantType;		//租户类型：1=运营中心、2=企业、3=个人、4=其它
	private String tenantStatus;	//租户状态：1=初始化、2=试用中、3=生产中、4=已过期
	private String serveType;		//服务方式：1=收费、0=免费
	private Date startTime;			//租赁服务开始时间
	private Date endTime;			//租赁服务结束时间
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantType() {
		return tenantType;
	}
	public void setTenantType(String tenantType) {
		this.tenantType = tenantType;
	}
	public String getTenantStatus() {
		return tenantStatus;
	}
	public void setTenantStatus(String tenantStatus) {
		this.tenantStatus = tenantStatus;
	}
	public String getServeType() {
		return serveType;
	}
	public void setServeType(String serveType) {
		this.serveType = serveType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
   
}