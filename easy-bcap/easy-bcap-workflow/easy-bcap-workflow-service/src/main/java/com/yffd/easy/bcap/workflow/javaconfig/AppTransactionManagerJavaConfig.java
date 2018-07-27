package com.yffd.easy.bcap.workflow.javaconfig;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月23日 下午3:20:32 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
// 开启注解式事务的支持(@Transactional)
@EnableTransactionManagement
//指定使用CGLIB代理，并开启
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppTransactionManagerJavaConfig {
	@Resource(name = "wfDataSource")
	private DataSource wfDataSource;
	
	/** 配置JDBC事务管理器  */
	@Bean(name = "wfTransactionManager")
	public DataSourceTransactionManager wfTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(this.wfDataSource);
		return dataSourceTransactionManager;
	}
	
}

