package com.yffd.easy.uupm.pojo.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.entity.UupmDictionaryEntity;
import com.yffd.easy.uupm.pojo.vo.UupmTreeDictVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:46:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmDictionaryFactory extends EasyCustomTreeBuilder<UupmDictionaryEntity, UupmTreeDictVo> {

	public List<UupmTreeDictVo> buildMultiTree(List<UupmDictionaryEntity> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UupmTreeDictVo> buildMultiTree(List<UupmDictionaryEntity> list, String rootPidValue) {
		UupmDictionaryEntity rootNode = new UupmDictionaryEntity();
		rootNode.setKeyCode(rootPidValue);
		UupmTreeDictVo tree = this.buildTree(list, rootNode);
		return (List<UupmTreeDictVo>) tree.getChildren();
	}
	
	@Override
	protected Object getIdValue(UupmDictionaryEntity node) {
		return node.getKeyCode();
	}

	@Override
	protected Object getPidValue(UupmDictionaryEntity node) {
		return node.getParentCode();
	}

	@Override
	protected UupmTreeDictVo getTreeNode(UupmDictionaryEntity node) {
		UupmTreeDictVo treeNode = new UupmTreeDictVo();
		treeNode.setState("open");
		treeNode.setText(node.getKeyName());
		treeNode.setId(node.getId());
		treeNode.setKeyName(node.getKeyName());
		treeNode.setKeyCode(node.getKeyCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setCategory(node.getCategory());
		treeNode.setKeyValue(node.getKeyValue());
		treeNode.setSeqNo(node.getSeqNo());
		return treeNode;
	}
	
}

