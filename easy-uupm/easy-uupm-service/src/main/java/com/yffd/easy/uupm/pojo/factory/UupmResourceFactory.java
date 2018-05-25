package com.yffd.easy.uupm.pojo.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
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
public class UupmResourceFactory extends EasyCustomTreeBuilder<UupmResourceEntity, UupmUIResTreeVo> {

	public List<UupmUIResTreeVo> buildMultiTree(List<UupmResourceEntity> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UupmUIResTreeVo> buildMultiTree(List<UupmResourceEntity> list, String rootPidValue) {
		UupmResourceEntity rootNode = new UupmResourceEntity();
		rootNode.setRsCode(rootPidValue);
		UupmUIResTreeVo tree = this.buildTree(list, rootNode);
		return (List<UupmUIResTreeVo>) tree.getChildren();
	}
	
	@Override
	protected Object getIdValue(UupmResourceEntity node) {
		return node.getRsCode();
	}

	@Override
	protected Object getPidValue(UupmResourceEntity node) {
		return node.getParentCode();
	}

	@Override
	protected UupmUIResTreeVo getTreeNode(UupmResourceEntity node) {
		UupmUIResTreeVo treeNode = new UupmUIResTreeVo();
		treeNode.setId(node.getId());
		treeNode.setRsName(node.getRsName());
		treeNode.setRsCode(node.getRsCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setTreeId(node.getTreeId());
		treeNode.setRsType(node.getRsType());
		treeNode.setShortUrl(node.getShortUrl());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}

	@Override
	protected void afterFilterNode(UupmUIResTreeVo child, UupmUIResTreeVo parent) {
		child.setParentName(parent.getRsName());
	}
	
}

