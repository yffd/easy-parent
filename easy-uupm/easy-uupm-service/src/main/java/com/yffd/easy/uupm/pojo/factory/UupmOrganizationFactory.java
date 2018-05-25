package com.yffd.easy.uupm.pojo.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIOrgTreeVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午5:15:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmOrganizationFactory extends EasyCustomTreeBuilder<UupmOrganizationEntity, UupmUIOrgTreeVo> {

	public List<UupmUIOrgTreeVo> buildMultiTree(List<UupmOrganizationEntity> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UupmUIOrgTreeVo> buildMultiTree(List<UupmOrganizationEntity> list, String parentCode) {
		UupmOrganizationEntity rootNode = new UupmOrganizationEntity();
		rootNode.setOrgCode(parentCode);
		UupmUIOrgTreeVo tree = this.buildTree(list, rootNode);
		return (List<UupmUIOrgTreeVo>) tree.getChildren();
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
	protected UupmUIOrgTreeVo getTreeNode(UupmOrganizationEntity node) {
		UupmUIOrgTreeVo treeNode = new UupmUIOrgTreeVo();
		treeNode.setId(node.getId());
		treeNode.setOrgName(node.getOrgName());
		treeNode.setOrgCode(node.getOrgCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setTreeId(node.getTreeId());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}

	@Override
	protected void afterFilterNode(UupmUIOrgTreeVo child, UupmUIOrgTreeVo parent) {
		child.setParentName(parent.getOrgName());
	}

}

