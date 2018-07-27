package com.yffd.easy.bcap.workflow.service;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 上午9:52:17 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class WfBaseService {
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	public RuntimeService getRuntimeService() {
		return runtimeService;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public FormService getFormService() {
		return formService;
	}
	public HistoryService getHistoryService() {
		return historyService;
	}
	
}

