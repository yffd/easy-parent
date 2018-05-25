package com.yffd.easy.uupm.pojo.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.common.core.tree.custom.EasyCustomTreeBuilder;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.uupm.pojo.vo.UupmMenuInfoVo;
import com.yffd.easy.uupm.pojo.vo.UupmTreeMenuVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:08:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmMenuInfoFactory extends EasyCustomTreeBuilder<UupmMenuInfoVo, UupmTreeMenuVo> {

	public List<UupmTreeMenuVo> buildMultiTree(List<UupmMenuInfoVo> list) {
		return this.buildMultiTree(list, "root");
	}
	
	public List<UupmTreeMenuVo> buildMultiTree(List<UupmMenuInfoVo> list, String rootPidValue) {
		UupmMenuInfoVo rootNode = new UupmMenuInfoVo();
		rootNode.setMenuCode(rootPidValue);
		UupmTreeMenuVo tree = this.buildTree(list, rootNode);
		return (List<UupmTreeMenuVo>) tree.getChildren();
	}
	
	@Override
	protected Object getIdValue(UupmMenuInfoVo node) {
		return node.getMenuCode();
	}

	@Override
	protected Object getPidValue(UupmMenuInfoVo node) {
		return node.getParentCode();
	}

	@Override
	protected UupmTreeMenuVo getTreeNode(UupmMenuInfoVo node) {
		UupmTreeMenuVo treeNode = new UupmTreeMenuVo();
		treeNode.setText(node.getMenuName());
		treeNode.setState("open");
		treeNode.setId(node.getId());
		treeNode.setMenuCode(node.getMenuCode());
		treeNode.setParentCode(node.getParentCode());
		treeNode.setMenuName(node.getMenuName());
		treeNode.setMenuIcons(node.getMenuIcons());
		
		String menuShortUrl = node.getMenuShortUrl();
		String appDomain = node.getAppDomain();
		String appPort = node.getAppPort();
		String appContextPath = node.getAppContextPath();
		
		String menuUrl = null;
		if(EasyStringCheckUtils.isEmpty(appDomain) 
				|| EasyStringCheckUtils.isEmpty(appPort) 
				|| EasyStringCheckUtils.isEmpty(appContextPath)) {
			menuUrl = menuShortUrl;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("http://").append(appDomain).append(":").append(appPort).append("/").append(appContextPath).append("/").append(menuShortUrl);
			menuUrl = sb.toString();
		}
		
		treeNode.setMenuUrl(menuUrl);
		return treeNode;
	}

}

