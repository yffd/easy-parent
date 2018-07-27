package com.yffd.easy.bcap.workflow.service;

import org.activiti.engine.runtime.ProcessInstance;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 下午2:40:37 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfTaskService extends WfBaseService {

	public String start(String wfDefinitonKey) {
		ProcessInstance processInstance = this.getRuntimeService()	// 
				.startProcessInstanceByKey(wfDefinitonKey);
		return processInstance.getId();
	}
}

