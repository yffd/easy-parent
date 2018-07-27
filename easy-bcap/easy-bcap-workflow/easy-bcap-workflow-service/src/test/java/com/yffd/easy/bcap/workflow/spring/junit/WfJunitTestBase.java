package com.yffd.easy.bcap.workflow.spring.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.yffd.easy.bcap.workflow.javaconfig.AppContextJavaConfig;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月23日 下午5:52:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WfJunitTestBase {

	public AbstractApplicationContext context;
	
	@Before
	public void initContiner() {
		context = new AnnotationConfigApplicationContext(AppContextJavaConfig.class);
		System.out.println("============spring 容器【初始化】完毕============");
	}
	
	@After
	public void destroyContiner() {
		if (null != this.context) {
			this.context.close();
			System.out.println("============spring 容器【销毁】完毕============");
		}
	}
	
	@Test
	public void myTest() {
		
	}
	
}

