package com.yffd.easy.bcap.workflow.vo;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 上午11:01:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfDefinitionVo implements Serializable {
	private static final long serialVersionUID = -6066510564343791880L;
	private String id;
	private String name;
	private String key;
	private int verison;
	private boolean suspended;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getVerison() {
		return verison;
	}
	public void setVerison(int verison) {
		this.verison = verison;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	
}

