package com.yffd.easy.bcap.workflow.javaconfig;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月23日 下午5:42:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
public class AppBeansJavaConfig {

	private static final Logger LOG = LoggerFactory.getLogger(AppContextJavaConfig.class);
	
	@Resource(name = "wfDataSource")
	private DataSource wfDataSource;
	@Resource(name = "wfTransactionManager")
	private DataSourceTransactionManager wfTransactionManager;
	
	/** spring负责创建流程引擎 */
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() {
		SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
		springProcessEngineConfiguration.setDataSource(this.wfDataSource);
		springProcessEngineConfiguration.setTransactionManager(this.wfTransactionManager);
		springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
		springProcessEngineConfiguration.setJobExecutorActivate(false);
		return springProcessEngineConfiguration;
	}
	
	/** 创建流程引擎对象 */
	@Bean
	public ProcessEngineFactoryBean processEngine() {
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setProcessEngineConfiguration(this.processEngineConfiguration());
		return processEngineFactoryBean;
	}
	
	@Bean
	public RepositoryService repositoryService() {
		try {
			return this.processEngine().getObject().getRepositoryService();
		} catch (Exception e) {
			LOG.error("获取activiti的【RepositoryService】失败", e);
		}
		return null;
	}

	@Bean
	public RuntimeService runtimeService() {
		try {
			return this.processEngine().getObject().getRuntimeService();
		} catch (Exception e) {
			LOG.error("获取activiti的【RuntimeService】失败", e);
		}
		return null;
	}

	@Bean
	public TaskService taskService() {
		try {
			return this.processEngine().getObject().getTaskService();
		} catch (Exception e) {
			LOG.error("获取activiti的【TaskService】失败", e);
		}
		return null;
	}

	@Bean
	public HistoryService historyService() {
		try {
			return this.processEngine().getObject().getHistoryService();
		} catch (Exception e) {
			LOG.error("获取activiti的【HistoryService】失败", e);
		}
		return null;
	}
	
	@Bean
	public ManagementService managementService() {
		try {
			return this.processEngine().getObject().getManagementService();
		} catch (Exception e) {
			LOG.error("获取activiti的【ManagementService】失败", e);
		}
		return null;
	}
	
}

