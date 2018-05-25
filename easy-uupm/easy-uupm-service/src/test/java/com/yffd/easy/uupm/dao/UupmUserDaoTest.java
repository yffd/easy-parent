package com.yffd.easy.uupm.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.base.UupmBaseDaoTest;
import com.yffd.easy.uupm.entity.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月10日 上午10:28:24 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmUserDaoTest extends UupmBaseDaoTest {

	@Autowired
	private UupmUserDao uupmUserDao;
	
	@Test
	public void findUserInfoPageTest() {
		PageParam page = new PageParam(1, 10);
		UupmUserInfoVo entity = new UupmUserInfoVo();
		entity.setUserCode("lisi");
		entity.setUserName("李四");
		entity.setOrgCode("ceshi_1");
		PageResult<UupmUserInfoVo> result = this.uupmUserDao.findUserInfoPage(entity, page);
		System.out.println(result.getPageParam());
		System.out.println(result.getRecordList().size());
		System.out.println(result.getRecordList().get(0).getUserCode());
	}
	@Test
	public void findCountTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("xiaohei");
		int num = this.uupmUserDao.findCount(entity);
		System.out.println(num);
	}
	
	@Test
	public void exsistAndUniqueTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("xiaohei");
		boolean result = this.uupmUserDao.exsistAndUnique(entity);
		System.out.println(result);
	}
	
	@Test
	public void exsistTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("xiaohei1");
		boolean result = this.uupmUserDao.exsist(entity);
		System.out.println(result);
	}
	
	@Test
	public void findPageWithOrderTest() {
		PageParam page = new PageParam(1, 10);
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("lisi");
		entity.setUserName("李四");
		entity.setOrgCode("ceshi_1");
		String orderBy = "userCode desc";
		PageResult<UupmUserEntity> result = this.uupmUserDao.findPageWithOrder(entity, orderBy, page);
		System.out.println(result.getPageParam());
		System.out.println(result.getRecordList().size());
	}
	@Test
	public void findListWithOrderTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("lisi");
		entity.setUserName("李四");
		entity.setOrgCode("ceshi_1");
		String orderBy = "userCode desc";
		List<UupmUserEntity> result = this.uupmUserDao.findListWithOrder(entity, orderBy);
		System.out.println(result.size());
	}
	
	@Test
	public void findOneTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("lisi");
		entity.setUserName("李四");
		entity.setOrgCode("ceshi_1");
		UupmUserEntity result = this.uupmUserDao.findOne(entity);
		System.out.println(result.getId());
	}
	
	@Test
	public void saveTest() {
		UupmUserEntity entity = new UupmUserEntity();
		entity.setUserCode("lisi");
		entity.setUserName("李四");
		entity.setOrgCode("ceshi_1");
		int num = this.uupmUserDao.save(entity);
		System.out.println(num);
		System.out.println(entity.getId());
	}
}

