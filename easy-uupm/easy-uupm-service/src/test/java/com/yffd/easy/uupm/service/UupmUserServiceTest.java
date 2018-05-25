package com.yffd.easy.uupm.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.uupm.base.UupmBaseServiceTest;
import com.yffd.easy.uupm.entity.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月10日 上午11:31:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmUserServiceTest extends UupmBaseServiceTest {

	@Autowired
	private UupmUserServiceDemo uupmUserService;
	
	@Test
	public void findOneUserTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("lisi123");
		entity.setUserName("李四123");
		entity.setOrgCode("ceshi_1");
		UupmUserEntity result = this.uupmUserService.findOneUser(entity);
		System.out.println(result.getId());
	}
	@Test
	public void findOneUser123Test() {
		UupmUserInfoVo vo = new UupmUserInfoVo();
		vo.setUserCode("lisi123");
		vo.setUserName("李四123");
		vo.setOrgName("部门");
		UupmUserEntity result = this.uupmUserService.findOneUser123(vo);
		System.out.println(result.getId());
	}
	@Test
	public void findOneUser124Test() {
		UupmUserInfoVo vo = new UupmUserInfoVo();
		vo.setUserCode("lisi123");
		vo.setUserName("李四123");
		vo.setOrgName("部门");
		UupmUserInfoVo result = this.uupmUserService.findOneUser124(vo);
		System.out.println(result.getOrgCode());
	}
	@Test
	public void findOneUser125Test() {
		UupmUserInfoVo vo = new UupmUserInfoVo();
		vo.setUserCode("lisi123");
		vo.setUserName("李四123");
		vo.setOrgName("部门");
		UupmUserEntity result = this.uupmUserService.findOneUser125(vo);
		System.out.println(result.getOrgCode());
	}
}

