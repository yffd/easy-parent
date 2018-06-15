package com.yffd.easy.uumc.web.model.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yffd.easy.uumc.pojo.entity.UumcOrganizationEntity;
import com.yffd.easy.uumc.service.UumcOrganizationService;
import com.yffd.easy.uumc.web.model.CombotreeModel;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午5:45:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UumcCombotreeModelFactory extends UumcCommonTreeModelFactory {
	@Autowired
	private UumcOrganizationService uumcOrganizationService;
	
	public Map<String, List<CombotreeModel>> getCombotreeData(String keyCodes) {
		Map<String, List<CombotreeModel>> map = new HashMap<>();
		String[] array = keyCodes.split(",");
		for (String keyCode : array) {
			List<CombotreeModel> modelList = this.switchModel(keyCode);
			map.put(keyCode, modelList);
		}
		return map;
	}
	
	public List<CombotreeModel> switchModel(String keyCode) {
		List<CombotreeModel> modelList = null;
		switch (keyCode) {
		case "org" : modelList = this.fmtOrgCombotree(); break;
		
		default : break;
		}
		return modelList;
	}
	
	public List<CombotreeModel> fmtOrgCombotree() {
		List<UumcOrganizationEntity> list = this.uumcOrganizationService.findAll();
		return this.fmtOrgCombotree(list, "root");
	}
	public List<CombotreeModel> fmtOrgCombotree(List<UumcOrganizationEntity> list, String parentCode) {
		UumcOrganizationEntity rootNode = new UumcOrganizationEntity();
		rootNode.setOrgCode(parentCode);
		CombotreeModel tree = orgTreeBuilder.buildTree(list, rootNode);
		return (List<CombotreeModel>) tree.getChildren();
	}
	
}

