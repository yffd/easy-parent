package com.yffd.easy.uupm.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.uupm.base.UupmBaseDaoTest;
import com.yffd.easy.uupm.entity.a.UupmRoleResourceEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月10日 下午4:59:22 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmRoleResourceDaoTest extends UupmBaseDaoTest {

	@Autowired
	private UupmRoleResourceDao uupmRoleResourceDao;
	
	@Test
	public void findByRoleCodesTest() {
		Set<String> roleCodes = new HashSet<String>();
		roleCodes.add("cwzj");
		roleCodes.add("dev-2");
		List<UupmRoleResourceEntity> result = this.uupmRoleResourceDao.findByRoleCodes(roleCodes);
		System.out.println(result.size());
		System.out.println(result.get(0).getRsCode());
	}
}

