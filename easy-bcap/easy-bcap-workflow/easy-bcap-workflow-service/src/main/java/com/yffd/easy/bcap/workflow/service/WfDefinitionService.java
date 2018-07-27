package com.yffd.easy.bcap.workflow.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.bcap.workflow.vo.WfDefinitionVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 上午10:39:56 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfDefinitionService extends WfBaseService {
	private Logger LOG = LoggerFactory.getLogger(WfDefinitionService.class);
	@Autowired
	private WfDeploymentService wfDeploymentService;
	
	public List<WfDefinitionVo> findWfDefinitonList(String wfDefinitionName, String wfDefinitionKey, 
			String resourceNameLike, boolean lastVersion, boolean suspended, int startIndex, int limit) {
		ProcessDefinitionQuery pdQuery = this.getRepositoryService().createProcessDefinitionQuery();
		if (null != wfDefinitionName && !"".equals(wfDefinitionName.trim()))
			pdQuery.processDefinitionName(wfDefinitionName);
		if (null != wfDefinitionKey && !"".equals(wfDefinitionKey.trim()))
			pdQuery.processDefinitionKey(wfDefinitionKey);
		if (null != resourceNameLike && !"".equals(resourceNameLike.trim()))
			pdQuery.processDefinitionResourceNameLike(resourceNameLike);
		if (lastVersion) 
			pdQuery.latestVersion();
		if (suspended) 
			pdQuery.suspended();
		pdQuery.orderByDeploymentId().desc();
		pdQuery.orderByProcessDefinitionVersion().desc();
		
		List<ProcessDefinition> list = pdQuery.listPage(startIndex, limit);
		if (null == list || list.size() == 0) return null;
		List<WfDefinitionVo> retList = new ArrayList<>();
		for (ProcessDefinition pd : list) {
			WfDefinitionVo vo = new WfDefinitionVo();
			vo.setId(vo.getId());
			vo.setName(pd.getName());
			vo.setKey(pd.getKey());
			vo.setVerison(pd.getVersion());
			vo.setSuspended(pd.isSuspended());
		}
		return retList;
	}
	
	public long findWfDefinitonCount(String wfDefinitionName, String wfDefinitionKey, 
			String resourceNameLike, boolean lastVersion, boolean suspended) {
		ProcessDefinitionQuery pdQuery = this.getRepositoryService().createProcessDefinitionQuery();
		if (null != wfDefinitionName && !"".equals(wfDefinitionName.trim()))
			pdQuery.processDefinitionName(wfDefinitionName);
		if (null != wfDefinitionKey && !"".equals(wfDefinitionKey.trim()))
			pdQuery.processDefinitionKey(wfDefinitionKey);
		if (null != resourceNameLike && !"".equals(resourceNameLike.trim()))
			pdQuery.processDefinitionResourceNameLike(resourceNameLike);
		if (lastVersion) 
			pdQuery.latestVersion();
		if (suspended) 
			pdQuery.suspended();
		return pdQuery.count();
	}
	
	public WfDefinitionVo findByDeploymentId(String deploymentId) {
		ProcessDefinition pd = this.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId(deploymentId)
				.singleResult();
		if (null == pd) return null;
		WfDefinitionVo vo = new WfDefinitionVo();
		vo.setId(vo.getId());
		vo.setName(pd.getName());
		vo.setKey(pd.getKey());
		vo.setVerison(pd.getVersion());
		vo.setSuspended(pd.isSuspended());
		return vo;
	}
	
	public InputStream findDgrmInputStream(String deploymentId) {
		String pngName = null;
		// 获取bpmn、png资源名称
		List<String> list = this.getRepositoryService().getDeploymentResourceNames(deploymentId);
		if (null != list) {
			for (String rsName : list) {
				if (rsName.lastIndexOf(".png") >= 0) pngName = rsName;
			}
		}
		if (null == pngName) return null;
		InputStream inputStream = this.getRepositoryService().getResourceAsStream(deploymentId, pngName);
		return inputStream;
	}
	
	/** 删除流程定义（包括相关联的发布对象），即删除key相同的所有不同版本的流程定义 */
	public void deleteByWfDefinitonKey(String wfDefinitionKey) {
		if (null == wfDefinitionKey) LOG.error("params error : processDefinitonKey is null !");
		List<ProcessDefinition> list = this.getRepositoryService().createProcessDefinitionQuery()	// 创建流程定义查询对象
									.processDefinitionKey(wfDefinitionKey)
									.list();
		if (null!=list && list.size() > 0) {
			for(ProcessDefinition pd : list) {
				this.wfDeploymentService.deleteByDeploymentId(pd.getDeploymentId(), true);
			}
		}
	}
}

