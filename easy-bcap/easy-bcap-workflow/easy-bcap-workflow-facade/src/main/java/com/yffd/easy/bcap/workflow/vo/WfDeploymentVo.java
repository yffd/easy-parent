package com.yffd.easy.bcap.workflow.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月18日 下午5:51:49 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfDeploymentVo implements Serializable {
	private static final long serialVersionUID = 5180499803465731212L;
	private String id;
	private String name;
	private String category;
	private Date deploymentTime;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	
}

