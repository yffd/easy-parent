package com.yffd.easy.uupm.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.uupm.base.UupmBaseDaoTest;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月10日 下午4:53:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmOrganizationDaoTest extends UupmBaseDaoTest {

	@Autowired
	private UupmOrganizationDao uupmOrganizationDao;
	
	@Test
	public void findByOrgCodesTest() {
		Set<String> orgCodes = new HashSet<String>();
		orgCodes.add("nuoyuan");
		orgCodes.add("it_1");
		List<UupmOrganizationEntity> result = this.uupmOrganizationDao.findByOrgCodes(orgCodes);
		System.out.println(result.size());
		System.out.println(result.get(0).getOrgName());
	}
	
}

