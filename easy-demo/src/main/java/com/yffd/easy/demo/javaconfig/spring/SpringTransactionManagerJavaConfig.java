package com.yffd.easy.demo.javaconfig.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

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
public class SpringTransactionManagerJavaConfig {
	@Autowired
	private DataSource dataSource;
	
	/** 配置JDBC事务管理器  */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(this.dataSource);
		return dataSourceTransactionManager;
	}
	
	/** 声明式事务拦截器 */
//	@Bean(name = "transactionInterceptor")
//	public TransactionInterceptor transactionInterceptor() {
//		Properties transactionAttributes = new Properties();
//		transactionAttributes.setProperty("get*", "PROPAGATION_NOT_SUPPORTED, readOnly");
//		transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
//		transactionAttributes.setProperty("load*", "PROPAGATION_REQUIRED, readOnly");
//		transactionAttributes.setProperty("query*", "PROPAGATION_REQUIRED, readOnly");
//		transactionAttributes.setProperty("read*", "PROPAGATION_REQUIRED, readOnly");
//		transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("add*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("create*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("modify*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("change*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("remove*", "PROPAGATION_REQUIRED, -Exception");
//		transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED, -Exception");
//		
//		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
//		transactionInterceptor.setTransactionManager(dataSourceTransactionManager());
//		transactionInterceptor.setTransactionAttributes(transactionAttributes);
//        return transactionInterceptor;
//	}
//	
//    public @Bean BeanNameAutoProxyCreator proxyCreator(){
//        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
//        proxyCreator.setProxyTargetClass(true);
//        proxyCreator.setInterceptorNames("transactionInterceptor");
//        proxyCreator.setBeanNames("*Service", "*ServiceImpl");
//        return proxyCreator;
//    }
}

