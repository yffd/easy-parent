package com.yffd.easy.bcap.workflow.vo;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月19日 下午1:42:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfDefinitionVo implements Serializable {
	private static final long serialVersionUID = -4964151186870741254L;
	private String id;
	private String key;
	private String name;
	private String version;
	private String deployId;
	private String resourceBpmn;
	private String resourcePng;
	private boolean suspensionState;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDeployId() {
		return deployId;
	}
	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}
	public String getResourceBpmn() {
		return resourceBpmn;
	}
	public void setResourceBpmn(String resourceBpmn) {
		this.resourceBpmn = resourceBpmn;
	}
	public String getResourcePng() {
		return resourcePng;
	}
	public void setResourcePng(String resourcePng) {
		this.resourcePng = resourcePng;
	}
	public boolean isSuspensionState() {
		return suspensionState;
	}
	public void setSuspensionState(boolean suspensionState) {
		this.suspensionState = suspensionState;
	}
	
}

