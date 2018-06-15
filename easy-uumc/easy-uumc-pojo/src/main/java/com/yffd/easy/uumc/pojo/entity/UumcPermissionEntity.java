package com.yffd.easy.uumc.pojo.entity;

import com.yffd.easy.framework.pojo.entity.CommonEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月11日 下午2:01:11 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UumcPermissionEntity extends CommonEntity {
	private static final long serialVersionUID = 1L;
	private String pmsName;		//权限名称
	private String pmsCode;		//权限编号
	private String appCode;
	private String rsCode;
	public String getPmsName() {
		return pmsName;
	}
	public void setPmsName(String pmsName) {
		this.pmsName = pmsName;
	}
	public String getPmsCode() {
		return pmsCode;
	}
	public void setPmsCode(String pmsCode) {
		this.pmsCode = pmsCode;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	
}

