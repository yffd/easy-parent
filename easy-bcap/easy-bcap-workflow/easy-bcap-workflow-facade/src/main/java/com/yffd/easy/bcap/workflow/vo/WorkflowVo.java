package com.yffd.easy.bcap.workflow.vo;

import java.io.File;
import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月19日 下午2:15:57 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WorkflowVo implements Serializable {
	private static final long serialVersionUID = 8973112946760141622L;
	
	/** 流程定义文件 */
	private File zipFile;
	/** 流程部署名称 */
	private String deployName;
	/** 流程部署分类 */
	private String deployCategory;
	/** 流程部署ID */
	private String deploymentId;//部署对象ID
	private String imageName;	//资源文件名称
	private String taskId;		//任务ID
	private String outcome;		//连线名称
	private String comment;		//备注
	private Long id;//申请单ID
}

