package com.yffd.easy.uupm.web.model.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.pojo.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.web.model.UupmTreeOrganizationVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午5:15:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmTreeOrganizationVoFactory extends EasyCustomTreeBuilder<UupmOrganizationEntity, UupmTreeOrganizationVo> {

	public List<UupmTreeOrganizationVo> buildTree(List<UupmOrganizationEntity> list) {
		return this.buildTree(list, "root");
	}
	
	public List<UupmTreeOrganizationVo> buildTree(List<UupmOrganizationEntity> list, String parentCode) {
		UupmOrganizationEntity rootNode = new UupmOrganizationEntity();
		rootNode.setOrgCode(parentCode);
		UupmTreeOrganizationVo tree = this.buildTree(list, rootNode);
		return (List<UupmTreeOrganizationVo>) tree.getChildren();
	}
	
	
	@Override
	protected Object getIdValue(UupmOrganizationEntity node) {
		return node.getOrgCode();
	}

	@Override
	protected Object getPidValue(UupmOrganizationEntity node) {
		return node.getParentCode();
	}

	@Override
	protected UupmTreeOrganizationVo getTreeNode(UupmOrganizationEntity node) {
		UupmTreeOrganizationVo treeNode = new UupmTreeOrganizationVo();
		treeNode.setId(node.getId());
		treeNode.setOrgName(node.getOrgName());
		treeNode.setOrgCode(node.getOrgCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setTreeId(node.getTreeId());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}

	@Override
	protected void afterFilterNode(UupmTreeOrganizationVo child, UupmTreeOrganizationVo parent) {
		child.setParentName(parent.getOrgName());
	}

}

