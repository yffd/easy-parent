package com.yffd.easy.common.core.tree.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.tree.model.EasyTestNode;
import com.yffd.easy.common.core.tree.sample.EasySampleTree;
import com.yffd.easy.common.core.tree.sample.EasySampleTreeBuilder;
import com.yffd.easy.common.core.tree.sample.EasySampleTreeJson;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月18日 上午9:50:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasySampleTreeJsonTest {

	@Test
	public void getTreeJsonTest() {
		EasyTestNode root = new EasyTestNode("root", "-1");
		EasyTestNode treeNode1 = new EasyTestNode("1", "root");  
		EasyTestNode treeNode2 = new EasyTestNode("2", "root");  
  
        EasyTestNode treeNode3 = new EasyTestNode("3", "1");  
        EasyTestNode treeNode4 = new EasyTestNode("4", "1");  
        EasyTestNode treeNode5 = new EasyTestNode("5", "1");  
  
  
        EasyTestNode treeNode6 = new EasyTestNode("6", "2");  
        EasyTestNode treeNode7 = new EasyTestNode("7", "2");  
        EasyTestNode treeNode8 = new EasyTestNode("8", "2");  
  
        List<EasyTestNode> list = new ArrayList<EasyTestNode>();  
  
        list.add(root);  
        list.add(treeNode1);  
        list.add(treeNode2);  
        list.add(treeNode3);  
        list.add(treeNode4);  
        list.add(treeNode5);  
        list.add(treeNode6);  
        list.add(treeNode7);  
        list.add(treeNode8);  
        
        EasySampleTreeBuilder<EasyTestNode> builder = new EasySampleTreeBuilder<EasyTestNode>() {

			@Override
			public EasySampleTree getTreeNode(EasyTestNode node) {
				EasySampleTree tree = new EasySampleTree();
				tree.setDataValue(node);
				tree.setIdValue(node.getId());
				tree.setPidValue(node.getName());
				return tree;
			}
			
			@Override
			public Object getIdValue(EasyTestNode node) {
				return node.getId();
			}

			@Override
			public Object getPidValue(EasyTestNode node) {
				return node.getName();
			}
		};
		
		EasySampleTree tree = builder.buildTree(list, root);
		System.out.println(EasySampleTreeJson.getTreeJson(tree));
	}
}

