/**
 * @Copyright: Copyright (c) 2018
 * @Author:  ZhangST
 * @version 1.0
*/

package com.yffd.easy.uupm.entity;

import com.yffd.easy.framework.common.persist.entity.CommonEntity;

/**
 * 
 * @Description  租户信息.
 * @Date		 2018年2月1日 上午10:06:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class UupmTenantEntity extends CommonEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String ttName;			//租户名称
	private String ttCode;			//租户编号
	private String pinyin;			//租户全拼
	private String shortPinyin;		//租户简拼
	private String ttType;			//租户类型：1=运营中心、2=企业、3=个人、4=其它
	private String ttStatus;		//租户状态
	private String serveType;		//服务方式：1=收费、0=免费
	private String serveStatus;		//服务状态
	
	public String getTtName() {
		return ttName;
	}
	public void setTtName(String ttName) {
		this.ttName = ttName;
	}
	public String getTtCode() {
		return ttCode;
	}
	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getShortPinyin() {
		return shortPinyin;
	}
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}
	public String getTtStatus() {
		return ttStatus;
	}
	public void setTtStatus(String ttStatus) {
		this.ttStatus = ttStatus;
	}
	public String getTtType() {
		return ttType;
	}
	public void setTtType(String ttType) {
		this.ttType = ttType;
	}
	public String getServeStatus() {
		return serveStatus;
	}
	public void setServeStatus(String serveStatus) {
		this.serveStatus = serveStatus;
	}
	public String getServeType() {
		return serveType;
	}
	public void setServeType(String serveType) {
		this.serveType = serveType;
	}
	
}