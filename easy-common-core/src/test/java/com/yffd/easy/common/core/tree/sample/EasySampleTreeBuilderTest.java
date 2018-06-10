package com.yffd.easy.common.core.tree.sample;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.tree.model.EasyTestNode;
import com.yffd.easy.common.core.tree.sample.EasySampleTree;
import com.yffd.easy.common.core.tree.sample.EasySampleTreeBuilder;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月17日 上午10:23:16 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasySampleTreeBuilderTest {

	@Test
	public void buildTreeTest() {
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
  
        EasyTestNode root2 = new EasyTestNode("root2", "-1");
		EasyTestNode treeNode12 = new EasyTestNode("12", "root2");
		EasyTestNode treeNode22 = new EasyTestNode("22", "root2");  
		EasyTestNode treeNode32 = new EasyTestNode("32", "root2");  
		EasyTestNode treeNode42 = new EasyTestNode("42", "12");   
		EasyTestNode treeNode52 = new EasyTestNode("52", "12");   
				
		list.add(root2);  
		list.add(treeNode12);  
		list.add(treeNode22);  
		list.add(treeNode32);  
		list.add(treeNode42);  
		list.add(treeNode52); 
		
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
		

		// 2.1
		EasySampleTree tree2 = builder.buildTree(list, root);
		String json2 = JSON.toJSONString(tree2);
		System.out.println(json2);
		
	}
}

