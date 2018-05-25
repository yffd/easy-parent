package com.yffd.easy.uupm.pojo.factory.ui;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.entity.UupmUITreeEntity;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIComboTreeVo;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIResTreeVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:46:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmUITreeFactory extends EasyCustomTreeBuilder<UupmUITreeEntity, UupmUIComboTreeVo> {

	public List<UupmUIComboTreeVo> buildMultiTree(List<UupmUITreeEntity> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UupmUIComboTreeVo> buildMultiTree(List<UupmUITreeEntity> list, String parentCode) {
		UupmUITreeEntity rootNode = new UupmUITreeEntity();
		rootNode.setDataCode(parentCode);
		UupmUIComboTreeVo tree = this.buildTree(list, rootNode);
		return (List<UupmUIComboTreeVo>) tree.getChildren();
	}
	
	@Override
	protected Object getIdValue(UupmUITreeEntity node) {
		return node.getDataCode();
	}

	@Override
	protected Object getPidValue(UupmUITreeEntity node) {
		return node.getParentCode();
	}

	@Override
	protected UupmUIComboTreeVo getTreeNode(UupmUITreeEntity node) {
		UupmUIComboTreeVo treeNode = new UupmUIComboTreeVo();
		treeNode.setId(node.getId());
		treeNode.setDataName(node.getDataName());
		treeNode.setDataCode(node.getDataCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setTreeId(node.getTreeId());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}
	
	@Override
	protected void afterFilterNode(UupmUIComboTreeVo child, UupmUIComboTreeVo parent) {
		child.setParentName(parent.getDataName());
	}
}

