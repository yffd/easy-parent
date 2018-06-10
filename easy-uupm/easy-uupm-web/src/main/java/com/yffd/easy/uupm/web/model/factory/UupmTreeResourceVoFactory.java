package com.yffd.easy.uupm.web.model.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.pojo.entity.UupmResourceEntity;
import com.yffd.easy.uupm.web.model.UupmTreeResourceVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:46:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmTreeResourceVoFactory extends EasyCustomTreeBuilder<UupmResourceEntity, UupmTreeResourceVo> {

	public List<UupmTreeResourceVo> buildMultiTree(List<UupmResourceEntity> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UupmTreeResourceVo> buildMultiTree(List<UupmResourceEntity> list, String rootPidValue) {
		UupmResourceEntity rootNode = new UupmResourceEntity();
		rootNode.setRsCode(rootPidValue);
		UupmTreeResourceVo tree = this.buildTree(list, rootNode);
		return (List<UupmTreeResourceVo>) tree.getChildren();
	}
	
	@Override
	public Object getIdValue(UupmResourceEntity node) {
		return node.getRsCode();
	}

	@Override
	public Object getPidValue(UupmResourceEntity node) {
		return node.getParentCode();
	}

	@Override
	public UupmTreeResourceVo getTreeNode(UupmResourceEntity node) {
		UupmTreeResourceVo treeNode = new UupmTreeResourceVo();
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
	public void afterFilterNode(UupmTreeResourceVo child, UupmTreeResourceVo parent) {
		child.setParentName(parent.getRsName());
	}
	
}

