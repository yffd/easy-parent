package com.yffd.easy.uupm.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.tree.sample.EasySampleTree;
import com.yffd.easy.common.core.tree.sample.EasySampleTreeJson;
import com.yffd.easy.uupm.base.UupmBaseServiceTest;
import com.yffd.easy.uupm.entity.a.UupmDictionaryEntity;
import com.yffd.easy.uupm.service.a.UupmDictionaryService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月14日 上午10:47:17 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmDictionaryServiceTest extends UupmBaseServiceTest {

	@Autowired
	private UupmDictionaryService uupmDictionaryService;
	
	@Test
	public void buildTreeTest() {
//		UupmDictionaryEntity node = new UupmDictionaryEntity();
//		node.setTreeId("combo");
//		node.setNodeCode("combo");
//		EasySampleTree tree = this.uupmDictionaryService.getServiceSupport().getTreeSupport()
//		.findTree(node);
//		System.out.println(tree);
//		System.out.println(JSON.toJSON(tree));
//		
//		System.out.println(EasySampleTreeJson.getTreeJson(tree));
	}
}

