package com.yffd.easy.demo.javaconfig.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月20日 下午1:33:55 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
//指定测试类的运行者
@RunWith(SpringJUnit4ClassRunner.class)
//指定spring配置类
@ContextConfiguration(classes={SpringApplicationContextJavaConfig.class})
//@WebAppConfiguration is a class-level annotation that is used to declare
//that the ApplicationContext loaded for an integration test should be a WebApplicationContext.
//@WebAppConfiguration
public class SpringJunitJavaConfig {

}

