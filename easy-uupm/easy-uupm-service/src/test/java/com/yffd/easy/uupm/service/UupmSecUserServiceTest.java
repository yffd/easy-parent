package com.yffd.easy.uupm.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.uupm.base.UupmBaseServiceTest;
import com.yffd.easy.uupm.pojo.entity.UupmSecUserEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 上午11:28:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmSecUserServiceTest extends UupmBaseServiceTest {

	@Autowired
	private UupmSecUserService uupmSecUserService;
	
	@Test
	public void exsistTest() {
		UupmSecUserEntity entity = new UupmSecUserEntity();
		entity.setUserCode("zhangsan");
		boolean result = this.uupmSecUserService.exsist(entity, null);
		System.out.println(result);
	}
	
	@Test
	public void saveTest() {
		UupmSecUserEntity entity = new UupmSecUserEntity();
		entity.setUserCode("zhangsan");
		int result = this.uupmSecUserService.save(entity, null);
		System.out.println(result);
	}
}

