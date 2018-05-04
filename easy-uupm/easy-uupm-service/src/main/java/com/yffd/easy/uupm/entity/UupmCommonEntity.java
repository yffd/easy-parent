package com.yffd.easy.uupm.entity;

import java.io.Serializable;
import java.util.Date;

import com.yffd.easy.common.core.pojo.IPOJO;

/**
 * @Description  自定义持久化类基类.
 * @Date		 2018年3月26日 上午11:44:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmCommonEntity implements IPOJO, Serializable {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = 4635049042528833163L;
	private String id;
	private Integer version;	// 版本号
	private String createBy;	// 创建人
	private Date createTime;	// 创建时间
	private String updateBy;	// 最后修改人
	private Date updateTime;	// 最后修改时间
	private String delFlag;		//逻辑删除标识：0=未删除、1=已删除
	private String tenantCode = "dft";	//租户编号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	
}

