package com.yffd.easy.uumc.web.model.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uumc.pojo.entity.UumcOrganizationEntity;
import com.yffd.easy.uumc.web.model.UumcTreeOrganizationModel;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午5:15:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UumcOrganizationModelFactory extends EasyCustomTreeBuilder<UumcOrganizationEntity, UumcTreeOrganizationModel> {

	public List<UumcTreeOrganizationModel> buildTree(List<UumcOrganizationEntity> list) {
		return this.buildTree(list, "root");
	}
	
	public List<UumcTreeOrganizationModel> buildTree(List<UumcOrganizationEntity> list, String parentCode) {
		UumcOrganizationEntity rootNode = new UumcOrganizationEntity();
		rootNode.setOrgCode(parentCode);
		UumcTreeOrganizationModel tree = this.buildTree(list, rootNode);
		return (List<UumcTreeOrganizationModel>) tree.getChildren();
	}
	
	
	@Override
	protected Object getIdValue(UumcOrganizationEntity node) {
		return node.getOrgCode();
	}

	@Override
	protected Object getPidValue(UumcOrganizationEntity node) {
		return node.getParentCode();
	}

	@Override
	protected UumcTreeOrganizationModel getTreeNode(UumcOrganizationEntity node) {
		UumcTreeOrganizationModel treeNode = new UumcTreeOrganizationModel();
		treeNode.setId(node.getId());
		treeNode.setOrgName(node.getOrgName());
		treeNode.setOrgCode(node.getOrgCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}

	@Override
	protected void afterFilterNode(UumcTreeOrganizationModel child, UumcTreeOrganizationModel parent) {
		child.setParentName(parent.getOrgName());
	}

}

