package com.yffd.easy.bcap.workflow.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yffd.easy.bcap.workflow.vo.WfDefinitionVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月23日 下午6:15:19 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class WorkflowService extends WfBaseService {
	private static final Logger LOG = LoggerFactory.getLogger(WorkflowService.class);
	
	public void deploy(String deployName, String deployCategory, File zipFile) {
		if (null == zipFile) LOG.error("params error : zipFile is null !");
		try {
			if (null == deployName) deployName = zipFile.getName();
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
			this.getRepositoryService().createDeployment()	// 创建发布对象
										.name(deployName)	// 设置发布名称
										.category(deployCategory)	// 设置分类
										.addZipInputStream(zipInputStream)	// 流程定义文件流
										.deploy();	// 执行发布
		} catch (FileNotFoundException e) {
			LOG.error(String.format("workflow deploy failed, [deployName:%s]", deployName), e);
		}
	}
	
	public void delDeployById(String deployId) {
		if (null == deployId) LOG.error("params error : deployId is null !");
		this.getRepositoryService().deleteDeployment(deployId, true);
	}
	
	public void delDeployByKey(String processDefinitionKey) {
		if (null == processDefinitionKey) LOG.error("params error : processDefinitonKey is null !");
		List<ProcessDefinition> list = this.getRepositoryService().createProcessDefinitionQuery()	// 创建流程定义查询对象
									.processDefinitionKey(processDefinitionKey)
									.list();
		if (null!=list && list.size() > 0) {
			for(ProcessDefinition pd : list) {
				this.delDeployById(pd.getDeploymentId());
			}
		}
	}
	
	public List<WfDefinitionVo> findWfDefinition(String wfDefinitonName, String wfDefinitonKey, int startIndex, int limit) {
		List<ProcessDefinition> list = this.getRepositoryService().createProcessDefinitionQuery()	// 创建流程定义查询对象
				.processDefinitionName(wfDefinitonName)
				.processDefinitionKey(wfDefinitonKey)
				.orderByDeploymentId().desc()
				.orderByProcessDefinitionVersion().desc()
				.listPage(startIndex, limit);
		if (null == list || list.size() == 0) return null;
		List<WfDefinitionVo> resultList = new ArrayList<>();
		for (ProcessDefinition pd : list) {
			WfDefinitionVo vo = new WfDefinitionVo();
			vo.setId(pd.getId());
			vo.setName(pd.getName());
			vo.setKey(pd.getKey());
			vo.setVerison(pd.getVersion());
			vo.setSuspended(pd.isSuspended());
			resultList.add(vo);
		}
		return resultList;
	}
	
	public WfDefinitionVo findWfDefiniton(String wfDefinitionId, String wfDeploymentId) {
		ProcessDefinition pd = this.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionId(wfDefinitionId)
				.deploymentId(wfDeploymentId)
				.singleResult();
		if (null == pd) return null;
		WfDefinitionVo vo = new WfDefinitionVo();
		vo.setId(pd.getId());
		vo.setName(pd.getName());
		vo.setKey(pd.getKey());
		vo.setVerison(pd.getVersion());
		vo.setSuspended(pd.isSuspended());
		return vo;
	}
}

