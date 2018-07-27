package com.yffd.easy.bcap.workflow.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.bcap.workflow.vo.WfDeploymentVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 上午9:51:47 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfDeploymentService extends WfBaseService {
	private static final Logger LOG = LoggerFactory.getLogger(WfDeploymentService.class);
	
	public List<WfDeploymentVo> findDeploymentList(String nameLike, String category, int startIndex, int limit) {
		List<Deployment> list = this.getRepositoryService().createDeploymentQuery()	// 创建流程发布查询对象
				.deploymentNameLike(nameLike)
				.deploymentCategory(category)
				.listPage(startIndex, limit);
		if (null == list || list.size() == 0) return null;
		
		List<WfDeploymentVo> returnList = new ArrayList<>();
		for (Deployment deployment : list) {
			WfDeploymentVo vo = new WfDeploymentVo();
			vo.setId(deployment.getId());
			vo.setName(deployment.getName());
			vo.setCategory(deployment.getCategory());
			vo.setDeployTime(deployment.getDeploymentTime());
			returnList.add(vo);
		}
		return returnList;
	}
	
	public long findDeploymentList(String nameLike, String category) {
		return this.getRepositoryService().createDeploymentQuery()
				.deploymentNameLike(nameLike)
				.deploymentCategory(category)
				.count();
	}
	
	public void deploy(String name, String category, File zipFile) {
		if (null == zipFile) LOG.error("params error : zipFile is null !");
		try {
			if (null == name) name = zipFile.getName();
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
			this.getRepositoryService().createDeployment()	// 创建发布对象
										.name(name)	// 设置发布名称
										.category(category)	// 设置分类
										.addZipInputStream(zipInputStream)	// 流程定义文件流
										.deploy();	// 执行发布
		} catch (FileNotFoundException e) {
			LOG.error(String.format("workflow deploy failed, [name: %s]", name), e);
		}
	}
	
	/**
	 * 删除流程发布（包括相关的流程定义）
	 * @Date	2018年7月24日 下午2:12:09 <br/>
	 * @author  zhangST
	 * @param deploymentId
	 * @param cascade	true:级联删除，不管流程是否启动，都能删除
	 * 					false:非级联删除，只能删除没有启动的流程，如果流程启动，就会抛出异常
	 */
	public void deleteByDeploymentId(String deploymentId, boolean cascade) {
		if (null == deploymentId) LOG.error("params error : deploymentId is null !");
		this.getRepositoryService().deleteDeployment(deploymentId, cascade);
	}
	
}

