package com.yffd.easy.uumc.web.model.factory;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.uumc.pojo.entity.UumcOrganizationEntity;
import com.yffd.easy.uumc.web.model.CombotreeModel;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午5:15:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UumcCommonTreeModelFactory {

	public EasyCustomTreeBuilder<UumcOrganizationEntity, CombotreeModel> orgTreeBuilder = new EasyCustomTreeBuilder<UumcOrganizationEntity, CombotreeModel>() {

		@Override
		protected Object getIdValue(UumcOrganizationEntity node) {
			return node.getOrgCode();
		}

		@Override
		protected Object getPidValue(UumcOrganizationEntity node) {
			return node.getParentCode();
		}

		@Override
		protected CombotreeModel getTreeNode(UumcOrganizationEntity node) {
			CombotreeModel treeNode = new CombotreeModel();
			treeNode.setId(node.getOrgCode());
			treeNode.setText(node.getOrgName());
			return treeNode;
		}
		
	};

}

