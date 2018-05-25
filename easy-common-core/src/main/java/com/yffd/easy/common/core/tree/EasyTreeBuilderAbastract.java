package com.yffd.easy.common.core.tree;

import java.util.ArrayList;
import java.util.List;

import com.yffd.easy.common.core.exception.EasyCommonException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月17日 上午10:07:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class EasyTreeBuilderAbastract<N, T> {

	protected abstract Object getIdValue(N node);
	
	protected abstract Object getPidValue(N node);
	
	protected abstract T getTreeNode(N node);
	
	protected abstract List<T> getChildren(T node);
	
	protected abstract void setChildren(T node, List<T> children);
	
	
	public T buildTree(List<N> nodes, N rootNode) {
		if(null==nodes || nodes.size()==0) return null;
		if(null==rootNode || null==this.getIdValue(rootNode)) 
			throw new EasyCommonException("根节点的idValue为空【pNode:"+rootNode.getClass().getName()+"】");
		T tree = this.getTreeNode(rootNode);
		this.building(nodes, rootNode, tree);
		return tree;
	}
	
	protected T building(List<N> nodes, N rootNode, T tree) {
//		this.recursiveNodes(nodes, rootNode, tree);
		this.iterNodes(nodes, rootNode, tree);
		return tree;
	}
	
	/**
	 * 迭代方式
	 * @Date	2018年5月17日 下午4:34:56 <br/>
	 * @author  zhangST
	 * @param nodes
	 * @param pNode
	 * @param tree
	 */
	protected void iterNodes(List<N> nodes, N pNode, T tree) {
		T pTreeNode = this.getTreeNode(pNode);
		List<N> childrenList = new ArrayList<>();
		List<T> childrenTreeList = new ArrayList<>();
		for(int i=0;i<nodes.size();i++) {
			N node = nodes.get(i);
			Object idValue = this.getIdValue(pNode);
			Object pidValue = this.getPidValue(node);
			if(pidValue.equals(idValue)) {
				T treeNode = this.getTreeNode(node);
				this.afterFilterNode(treeNode, pTreeNode);
				childrenList.add(node);
				childrenTreeList.add(treeNode);
				nodes.remove(i);
				i--;
			}
		}
		this.setChildren(tree, childrenTreeList);
		
		while(childrenList.size()>0 && nodes.size()>0) {
			List<N> tmpChildrenList = new ArrayList<>();
			List<T> tmpChildrenTreeList = new ArrayList<T>();
			for(int j=0;j<childrenList.size();j++) {
				N tmpParentNode = childrenList.get(j);
				T tmpParentTreeNode = childrenTreeList.get(j);
				pTreeNode = tmpParentTreeNode;
				for(int i=0;i<nodes.size();i++) {
					N node = nodes.get(i);
					Object idValue = this.getIdValue(tmpParentNode);
					Object pidValue = this.getPidValue(node);
					if(pidValue.equals(idValue)) {
						List<T> children = this.getChildren(tmpParentTreeNode);
						if(null==children) {
							children = new ArrayList<T>();
							this.setChildren(tmpParentTreeNode, children);
						}
						T treeNode = this.getTreeNode(node);
						this.afterFilterNode(treeNode, tmpParentTreeNode);
						children.add(treeNode);
						tmpChildrenList.add(node);
						tmpChildrenTreeList.add(treeNode);
						nodes.remove(i);
						i--;
					} 
				}
				this.setChildren(pTreeNode, this.getChildren(tmpParentTreeNode));
			}
			childrenList = tmpChildrenList;
			childrenTreeList = tmpChildrenTreeList;
		}
	}
	
	/**
	 * 递归方式
	 * @Date	2018年5月17日 下午4:34:42 <br/>
	 * @author  zhangST
	 * @param nodes
	 * @param pNode
	 * @param tree
	 */
	protected void recursiveNodes(List<N> nodes, N pNode, T tree) {
		T pTreeNode = this.getTreeNode(pNode);
		for(int i=0;i<nodes.size();i++) {
			N node = nodes.get(i);
			Object idValue = this.getIdValue(pNode);
			Object pidValue = this.getPidValue(node);
			if(pidValue.equals(idValue)) {
				List<T> children = this.getChildren(pTreeNode);
				if(null==children) {
					children = new ArrayList<T>();
					this.setChildren(pTreeNode, children);
				}
				T treeNode = this.getTreeNode(node);
				this.afterFilterNode(treeNode, pTreeNode);
				children.add(treeNode);
				this.recursiveNodes(nodes, node, treeNode);
			}
		}
		this.setChildren(tree, this.getChildren(pTreeNode));
	}
	
	protected void afterFilterNode(T child, T parent) {
		
	}

}

