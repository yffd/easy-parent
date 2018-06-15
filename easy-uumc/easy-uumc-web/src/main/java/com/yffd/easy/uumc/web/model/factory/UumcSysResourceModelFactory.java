package com.yffd.easy.uumc.web.model.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uumc.pojo.entity.UumcSysResourceEntity;
import com.yffd.easy.uumc.web.model.UumcTreeResourceModel;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:46:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UumcSysResourceModelFactory extends EasyCustomTreeBuilder<UumcSysResourceEntity, UumcTreeResourceModel> {

	public List<UumcTreeResourceModel> buildMultiTree(List<UumcSysResourceEntity> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UumcTreeResourceModel> buildMultiTree(List<UumcSysResourceEntity> list, String rootPidValue) {
		UumcSysResourceEntity rootNode = new UumcSysResourceEntity();
		rootNode.setRsCode(rootPidValue);
		UumcTreeResourceModel tree = this.buildTree(list, rootNode);
		return (List<UumcTreeResourceModel>) tree.getChildren();
	}
	
	@Override
	public Object getIdValue(UumcSysResourceEntity node) {
		return node.getRsCode();
	}

	@Override
	public Object getPidValue(UumcSysResourceEntity node) {
		return node.getParentCode();
	}

	@Override
	public UumcTreeResourceModel getTreeNode(UumcSysResourceEntity node) {
		UumcTreeResourceModel treeNode = new UumcTreeResourceModel();
		treeNode.setId(node.getId());
		treeNode.setAppCode(node.getAppCode());
		treeNode.setRsName(node.getRsName());
		treeNode.setRsCode(node.getRsCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setRsType(node.getRsType());
		treeNode.setShortUrl(node.getShortUrl());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}

	@Override
	public void afterFilterNode(UumcTreeResourceModel child, UumcTreeResourceModel parent) {
		child.setParentName(parent.getRsName());
	}
	
}

