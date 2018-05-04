package com.yffd.easy.framework.web.view.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.tree.EasyTreeCustomBuilder;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年9月25日 下午2:12:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class TreeBuilder extends EasyTreeCustomBuilder {

	/**
	 * 单棵树
	 * @Date	2018年4月13日 上午11:07:19 <br/>
	 * @author  zhangST
	 * @param treeNodes
	 * @param rootNode
	 * @return
	 */
	public <T extends TreeNode> T build(List<T> treeNodes, T rootNode) {
		if(null==treeNodes || treeNodes.size()==0) return rootNode;
		if(null==rootNode || EasyStringCheckUtils.isEmpty(rootNode.getId_())) throw new EasyCommonException("rootNode的id_为空.");
		this.filterChilren(treeNodes, rootNode);
		return rootNode;
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月13日 上午11:07:30 <br/>
	 * @author  zhangST
	 * @param treeNodes
	 * @param rootNodes
	 * @return
	 */
	public <T extends TreeNode> List<T> buildMulti(List<T> treeNodes, List<T> rootNodes) {
		if(null==treeNodes || treeNodes.size()==0 || null==rootNodes || rootNodes.size()==0) throw new EasyCommonException("参数错误");
		List<T> treeList = new ArrayList<T>();
		for(int i=0;i<rootNodes.size();i++) {
			T rootNode = rootNodes.get(i);
			if(EasyStringCheckUtils.isEmpty(rootNode.getId_())) throw new EasyCommonException("rootNode["+i+"]的id_为空.");
			this.filterChilren(treeNodes, rootNode);
			treeList.add(rootNode);
		}
		return treeList;
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月13日 上午11:07:39 <br/>
	 * @author  zhangST
	 * @param treeNodes
	 * @param rootPid
	 * @return
	 */
	public <T extends TreeNode> List<T> buildMulti(List<T> treeNodes, String rootPid) {
		if(null==treeNodes || treeNodes.size()==0) return null;
		if(EasyStringCheckUtils.isEmpty(rootPid)) throw new EasyCommonException("rootPid为空.");
		T rootNode = (T) new TreeNode();
		rootNode.setId_(rootPid);
		rootNode.setPid_("tmp");
		this.filterChilren(treeNodes, rootNode);
		List<T> resultList = (List<T>) rootNode.getChildren();
		return resultList;
	}
	
	/**
	 * 单棵树
	 * @Date	2018年4月13日 上午11:16:23 <br/>
	 * @author  zhangST
	 * @param treeNodes
	 * @param rootPid
	 * @return
	 */
	public <T extends TreeNode> T build(List<T> treeNodes, String rootPid) {
		List<T> resultList = this.buildMulti(treeNodes, rootPid);
		if(null==resultList) return null;
		if(resultList.size()>1) throw new EasyCommonException("转换出多棵树.");
		return resultList.get(0);
	}
	
	protected <T extends TreeNode> void filterChilren(List<T> treeNodes, T rootNode) {
//		this.recursiveNodes(treeNodes, rootNode);
		this.iterNodes(treeNodes, rootNode);
	}
	
	/**
	 * 迭代方式
	 * @Date	2018年4月13日 上午10:33:00 <br/>
	 * @author  zhangST
	 * @param treeNodes
	 * @param rootNode
	 */
	private <T extends TreeNode> void iterNodes(List<T> treeNodes, T rootNode) {
		if(EasyStringCheckUtils.isEmpty(rootNode.getPid_()) || EasyStringCheckUtils.isEmpty(rootNode.getId_())) 
			throw new EasyCommonException("节点的id_或pid_为空.【node:" + rootNode + "】");
		List<TreeNode> children = new ArrayList<TreeNode>();
		for(int i=0;i<treeNodes.size();i++) {
			T node = treeNodes.get(i);
			if(node.getPid_().equals(rootNode.getId_())) {
				if(null==rootNode.getChildren()) rootNode.setChildren(new ArrayList<TreeNode>());
				rootNode.getChildren().add(node);
				children.add(node);
				treeNodes.remove(i);
				i--;
			} 
		}
		while(children.size()>0 && treeNodes.size()>0) {
			List<TreeNode> tmpChildren = new ArrayList<TreeNode>();
			for(int j=0;j<children.size();j++) {
				T tmpRoot = (T) children.get(j);
				for(int i=0;i<treeNodes.size();i++) {
					T node = treeNodes.get(i);
					if(node.getPid_().equals(tmpRoot.getId_())) {
						if(null==tmpRoot.getChildren()) tmpRoot.setChildren(new ArrayList<TreeNode>());
						tmpRoot.getChildren().add(node);
						tmpChildren.add(node);
						treeNodes.remove(i);
						i--;
					} 
				}
			}
			children = tmpChildren;
		}
	}
	/**
	 * 递归方式
	 * @Date	2018年4月13日 上午10:33:47 <br/>
	 * @author  zhangST
	 * @param treeNodes
	 * @param rootNode
	 */
	private <T extends TreeNode> void recursiveNodes(List<T> treeNodes, T rootNode) {
		if(EasyStringCheckUtils.isEmpty(rootNode.getPid_()) || EasyStringCheckUtils.isEmpty(rootNode.getId_())) 
			throw new EasyCommonException("节点的id_或pid_为空.【node:" + rootNode + "】");
		for(int i=0;i<treeNodes.size();i++) {
			T node = treeNodes.get(i);
			if(node.getPid_().equals(rootNode.getId_())) {
				if(null==rootNode.getChildren()) rootNode.setChildren(new ArrayList<TreeNode>());
				rootNode.getChildren().add(node);
				this.recursiveNodes(treeNodes, node);
			} 
		}
	}
	

	public static void main(String[] args) {
		TreeNode root = new TreeNode("root", "-1");
		TreeNode treeNode1 = new TreeNode("1", "root");  
        TreeNode treeNode2 = new TreeNode("2", "root");  
  
        TreeNode treeNode3 = new TreeNode("3", "1");  
        TreeNode treeNode4 = new TreeNode("4", "1");  
        TreeNode treeNode5 = new TreeNode("5", "1");  
  
  
        TreeNode treeNode6 = new TreeNode("6", "2");  
        TreeNode treeNode7 = new TreeNode("7", "2");  
        TreeNode treeNode8 = new TreeNode("8", "2");  
  
        List<TreeNode> list = new ArrayList<TreeNode>();  
  
        list.add(root);  
        list.add(treeNode1);  
        list.add(treeNode2);  
        list.add(treeNode3);  
        list.add(treeNode4);  
        list.add(treeNode5);  
        list.add(treeNode6);  
        list.add(treeNode7);  
        list.add(treeNode8);  
  
        TreeNode root2 = new TreeNode("root2", "-1");
		TreeNode treeNode12 = new TreeNode("12", "root2");
		TreeNode treeNode22 = new TreeNode("22", "root2");  
		TreeNode treeNode32 = new TreeNode("32", "root2");  
		TreeNode treeNode42 = new TreeNode("42", "12");   
		TreeNode treeNode52 = new TreeNode("52", "12");   
				
		list.add(root2);  
		list.add(treeNode12);  
		list.add(treeNode22);  
		list.add(treeNode32);  
		list.add(treeNode42);  
		list.add(treeNode52);  
        
		TreeBuilder builder = new TreeBuilder();
        
//		EasyTreeNode result = root;
//		builder.iterNodes(list, result);	// 迭代
////		builder.recursiveNodes(list, result);	// 递归
//        String json = JSON.toJSONString(result);
//        System.out.println(json);
        
        
//        EasyTreeNode result1 = builder.build(list, root);	// 单棵树
//        String json1 = JSON.toJSONString(result1);
//        System.out.println(json1);
        
//        EasyTreeNode result2 = builder.build(list, "-1");	// 单棵树
//        String json2 = JSON.toJSONString(result2);
//        System.out.println(json2);
        
        List<TreeNode> result3 = builder.buildMulti(list, "-1");	// 多棵树
        String json3 = JSON.toJSONString(result3);
        System.out.println(json3);
        
	}
}

