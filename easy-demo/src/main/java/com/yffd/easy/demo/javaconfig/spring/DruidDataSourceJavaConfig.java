package com.yffd.easy.demo.javaconfig.spring;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Description	数据源配置.
 * @Date		 2018年7月20日 上午9:39:32 <br/>
 * @author zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
// 开启注解式事务的支持
@EnableTransactionManagement
//@PropertySources({@PropertySource("classpath:/properties/jdbc.properties")})
@PropertySource("classpath:/properties/jdbc.properties")
public class DruidDataSourceJavaConfig {
	private static final Logger LOG = LoggerFactory.getLogger(DruidDataSourceJavaConfig.class);
	
	@Value("${jdbc.url}")
	private String dbUrl;
	
	@Value("${jdbc.username}")
	private String username;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	
	@Value("${jdbc.initialSize}")
	private int initialSize;
	
	@Value("${jdbc.minIdle}")
	private int minIdle;
	
	@Value("${jdbc.maxActive}")
	private int maxActive;
	
	@Value("${jdbc.maxWait}")
	private int maxWait;
	
	@Value("${jdbc.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	
	@Value("${jdbc.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	
	@Value("${jdbc.validationQuery}")
	private String validationQuery;
	
	@Value("${jdbc.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${jdbc.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${jdbc.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${jdbc.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value("${jdbc.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	
	@Value("${jdbc.filters}")
	private String filters;
	
	@Value("{jdbc.connectionProperties}")
	private String connectionProperties;
	
	/** 数据源bean */
	public @Bean DataSource dataSource() {
		LOG.info("------------------数据库配置初始化开始--------------");
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		
		//configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			LOG.error("druid configuration initialization filter----->>:", e);
		}
		datasource.setConnectionProperties(connectionProperties);
		LOG.info("------------------数据库配置初始化结束--------------");
		return datasource;
	}
	
	/** 注入 jdbcTemplate 配置bean */
	public @Bean JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}
	
	/** 注入 namedParameterJdbcTemplate 配置bean，参数名称绑定方式 */
	public @Bean NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
		return namedParameterJdbcTemplate;
	}
	
	/** 配置JDBC事务管理器  */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
	
	/** 声明式事务拦截器 */
	@Bean(name = "transactionInterceptor")
	public TransactionInterceptor transactionInterceptor() {
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		transactionInterceptor.setTransactionManager(dataSourceTransactionManager());
		
		Properties transactionAttributes = new Properties();
		transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED, readOnly");
		transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
		transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
		
		transactionInterceptor.setTransactionAttributes(transactionAttributes);
		
        return transactionInterceptor;
	}
	
    public @Bean BeanNameAutoProxyCreator proxycreate(){
        BeanNameAutoProxyCreator proxycreate = new BeanNameAutoProxyCreator();
        proxycreate.setProxyTargetClass(true);
//        proxycreate.setBeanNames("*service");
        proxycreate.setBeanNames("* com.yffd.easy.**.service.*(..)");
        proxycreate.setInterceptorNames("transactionInterceptor");
        return proxycreate;
    }
}

