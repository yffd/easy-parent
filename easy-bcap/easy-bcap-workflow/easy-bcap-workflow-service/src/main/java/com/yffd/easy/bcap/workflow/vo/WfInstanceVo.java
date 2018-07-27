package com.yffd.easy.bcap.workflow.vo;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 下午3:02:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfInstanceVo implements Serializable {
	private static final long serialVersionUID = 7297897226362870783L;
	private String id;
	private String wfInstanceId;
	private String wfDefinitionId;
	private String activityId;
	private String businessKey;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWfInstanceId() {
		return wfInstanceId;
	}
	public void setWfInstanceId(String wfInstanceId) {
		this.wfInstanceId = wfInstanceId;
	}
	public String getWfDefinitionId() {
		return wfDefinitionId;
	}
	public void setWfDefinitionId(String wfDefinitionId) {
		this.wfDefinitionId = wfDefinitionId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
}

