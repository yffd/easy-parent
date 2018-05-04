package com.yffd.easy.uupm.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月5日 上午10:54:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring/uupm-spring-context.xml"
})
public abstract class UupmBaseServiceTest {

}

