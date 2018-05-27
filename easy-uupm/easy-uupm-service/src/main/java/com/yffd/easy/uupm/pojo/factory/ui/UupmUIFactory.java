package com.yffd.easy.uupm.pojo.factory.ui;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.entity.UupmUITreeEntity;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIBaseTreeVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:46:52 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmUIFactory {
	
	public List<UupmUIBaseTreeVo> buildComboTree(List<UupmUITreeEntity> list) {
		return this.buildComboTree(list, "root");
	}
	
	public List<UupmUIBaseTreeVo> buildComboTree(List<UupmUITreeEntity> list, String parentCode) {
		UupmUITreeEntity rootNode = new UupmUITreeEntity();
		rootNode.setDataCode(parentCode);
		UupmUIBaseTreeVo tree = this.buildComboTree(list, rootNode);
		return (List<UupmUIBaseTreeVo>) tree.getChildren();
	}
	
	public UupmUIBaseTreeVo buildComboTree(List<UupmUITreeEntity> list, UupmUITreeEntity rootNode) {
		return uiTreeBuilder.buildTree(list, rootNode);
	}
	
	
	public List<UupmUIBaseTreeVo> buildOrgComboTree(List<UupmOrganizationEntity> list) {
		return this.buildOrgComboTree(list, "root");
	}
	
	public List<UupmUIBaseTreeVo> buildOrgComboTree(List<UupmOrganizationEntity> list, String parentCode) {
		UupmOrganizationEntity rootNode = new UupmOrganizationEntity();
		rootNode.setOrgCode(parentCode);
		UupmUIBaseTreeVo tree = this.buildOrgComboTree(list, rootNode);
		return (List<UupmUIBaseTreeVo>) tree.getChildren();
	}
	
	public UupmUIBaseTreeVo buildOrgComboTree(List<UupmOrganizationEntity> list, UupmOrganizationEntity rootNode) {
		return orgTreeBuilder.buildTree(list, rootNode);
	}
	
	
	public EasyCustomTreeBuilder<UupmOrganizationEntity, UupmUIBaseTreeVo> orgTreeBuilder = new EasyCustomTreeBuilder<UupmOrganizationEntity, UupmUIBaseTreeVo>() {

		@Override
		protected Object getIdValue(UupmOrganizationEntity node) {
			return node.getOrgCode();
		}

		@Override
		protected Object getPidValue(UupmOrganizationEntity node) {
			return node.getParentCode();
		}

		@Override
		protected UupmUIBaseTreeVo getTreeNode(UupmOrganizationEntity node) {
			UupmUIBaseTreeVo treeNode = new UupmUIBaseTreeVo();
			treeNode.setText(node.getOrgName());
			treeNode.setId(node.getOrgCode());
			return treeNode;
		}

	};
	
	public EasyCustomTreeBuilder<UupmUITreeEntity, UupmUIBaseTreeVo> uiTreeBuilder = new EasyCustomTreeBuilder<UupmUITreeEntity, UupmUIBaseTreeVo>() {
		
		@Override
		protected Object getIdValue(UupmUITreeEntity node) {
			return node.getDataCode();
		}
		
		@Override
		protected Object getPidValue(UupmUITreeEntity node) {
			return node.getParentCode();
		}
		
		@Override
		protected UupmUIBaseTreeVo getTreeNode(UupmUITreeEntity node) {
			UupmUIBaseTreeVo treeNode = new UupmUIBaseTreeVo();
			treeNode.setId(node.getDataCode());
			treeNode.setText(node.getDataName());
			return treeNode;
		}
		
	};
	

}

