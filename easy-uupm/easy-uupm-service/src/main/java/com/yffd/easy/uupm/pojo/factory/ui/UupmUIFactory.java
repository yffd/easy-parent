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
	
	
//	public List<UupmTreeBaseVo> buildTreeForOrg(List<UupmOrganizationEntity> list) {
//		return this.buildTreeForOrg(list, "root");
//	}
//	
//	public List<UupmTreeBaseVo> buildTreeForOrg(List<UupmOrganizationEntity> list, String rootPidValue) {
//		UupmOrganizationEntity rootNode = new UupmOrganizationEntity();
//		rootNode.setOrgCode(rootPidValue);
//		UupmTreeBaseVo tree = this.buildTreeForOrg(list, rootNode);
//		return (List<UupmTreeBaseVo>) tree.getChildren();
//	}
//	
//	public UupmTreeBaseVo buildTreeForOrg(List<UupmOrganizationEntity> list, UupmOrganizationEntity rootNode) {
//		return orgTreeBuilder.buildTree(list, rootNode);
//	}
	
	
//	public EasyCustomTreeBuilder<UupmOrganizationEntity, UupmTreeBaseVo> orgTreeBuilder = new EasyCustomTreeBuilder<UupmOrganizationEntity, UupmTreeBaseVo>() {
//
//		@Override
//		protected Object getIdValue(UupmOrganizationEntity node) {
//			return node.getOrgCode();
//		}
//
//		@Override
//		protected Object getPidValue(UupmOrganizationEntity node) {
//			return node.getParentCode();
//		}
//
//		@Override
//		protected UupmTreeBaseVo getTreeNode(UupmOrganizationEntity node) {
//			UupmTreeBaseVo treeNode = new UupmTreeBaseVo();
//			treeNode.setState("open");
//			treeNode.setText(node.getOrgName());
//			treeNode.setId(node.getOrgCode());
//			return treeNode;
//		}
//
//	};
	
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

