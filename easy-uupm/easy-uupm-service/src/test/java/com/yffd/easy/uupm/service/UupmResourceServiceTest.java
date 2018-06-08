package com.yffd.easy.uupm.service;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yffd.easy.uupm.base.UupmBaseServiceTest;
import com.yffd.easy.uupm.entity.a.UupmResourceEntity;
import com.yffd.easy.uupm.service.a.UupmResourceService;

public class UupmResourceServiceTest extends UupmBaseServiceTest {

	@Autowired
	private UupmResourceService uupmResourceService;
	
	@Test
	public void findRsCodesByTreeIdTest() {
		String treeId = "q";
		Set<String> rsCodes = this.uupmResourceService.findRsCodesByTreeId(treeId );
		System.out.println(rsCodes);
	}
	
	@Test
	public void findListTest() {
		String treeId = "q";
		UupmResourceEntity model = new UupmResourceEntity();
		model.setTreeId(treeId);
		List<UupmResourceEntity> list = this.uupmResourceService.findList(model, null);
		System.out.println(list.size());
	}
	
}
