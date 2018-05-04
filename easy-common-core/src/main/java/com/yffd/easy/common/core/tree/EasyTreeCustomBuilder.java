package com.yffd.easy.common.core.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月13日 下午3:23:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyTreeCustomBuilder {
	
	/**
	 * 单棵树
	 * @Date	2018年4月13日 下午4:35:08 <br/>
	 * @author  zhangST
	 * @param idKey
	 * @param pidKey
	 * @param rootNode
	 * @param treeNodes
	 * @return
	 */
	public Map<String, Object> buildTree(String idKey, String pidKey, Map<String, Object> rootNode, List<Map<String, Object>> treeNodes) {
		Object rootIdValue = rootNode.get(idKey);
		if(null==rootIdValue) throw new EasyCommonException("根节点的id为空.【rootNode:" + rootNode + "】");
		if(null==treeNodes || treeNodes.size()==0) return rootNode;
		this.filterChilren(idKey, pidKey, rootNode, treeNodes);
		return rootNode;
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月13日 下午4:58:01 <br/>
	 * @author  zhangST
	 * @param idKey
	 * @param pidKey
	 * @param rootNodes
	 * @param treeNodes
	 * @return
	 */
	public List<Map<String, Object>> buildMultiTree(String idKey, String pidKey, List<Map<String, Object>> rootNodes, List<Map<String, Object>> treeNodes) {
		if(null==rootNodes || rootNodes.size()==0 || null==treeNodes || treeNodes.size()==0) throw new EasyCommonException("参数错误");
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<rootNodes.size();i++) {
			Map<String, Object> rootNode = rootNodes.get(i);
			Object rootIdValue = rootNode.get(idKey);
			if(null==rootIdValue) throw new EasyCommonException("rootNode["+i+"]的id为空.");
			this.filterChilren(idKey, pidKey, rootNode, treeNodes);
			treeList.add(rootNode);
		}
		return treeList;
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月13日 下午5:07:25 <br/>
	 * @author  zhangST
	 * @param idKey
	 * @param pidKey
	 * @param rootPidValue
	 * @param treeNodes
	 * @return
	 */
	public List<Map<String, Object>> buildMultiTree(String idKey, String pidKey, Object rootPidValue, List<Map<String, Object>> treeNodes) {
		if(EasyStringCheckUtils.isEmpty(idKey) || EasyStringCheckUtils.isEmpty(pidKey) 
				|| null==rootPidValue) throw new EasyCommonException("参数【idKey | pidKey | rootPidValue】为空.");
		if(null==treeNodes || treeNodes.size()==0) return null;
		Map<String, Object> rootNode = new HashMap<String, Object>();
		rootNode.put(idKey, rootPidValue);
		rootNode.put(pidKey, "tmp");
		this.filterChilren(idKey, pidKey, rootNode, treeNodes);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) rootNode.get("children");
		return resultList;
	}
	
	/**
	 * 单棵树
	 * @Date	2018年4月13日 下午4:59:32 <br/>
	 * @author  zhangST
	 * @param idKey
	 * @param pidKey
	 * @param rootPidValue
	 * @param treeNodes
	 * @return
	 */
	public Map<String, Object> buildTree(String idKey, String pidKey, Object rootPidValue, List<Map<String, Object>> treeNodes) {
		List<Map<String, Object>> resultList = this.buildMultiTree(idKey, pidKey, rootPidValue, treeNodes);
		if(null==resultList) return null;
		if(resultList.size()>1) throw new EasyCommonException("转换出多棵树.");
		return resultList.get(0);
	}
	
	protected void filterChilren(String idKey, String pidKey, Map<String, Object> rootNode, List<Map<String, Object>> treeNodes) {
//		this.recursiveNodes(idKey, pidKey, rootNode, treeNodes);
		this.iterNodes(idKey, pidKey, rootNode, treeNodes);
	}
	
	/**
	 * 迭代方式
	 * @Date	2018年4月13日 下午4:08:27 <br/>
	 * @author  zhangST
	 * @param idKey
	 * @param pidKey
	 * @param rootNode
	 * @param treeNodes
	 */
	private void iterNodes(String idKey, String pidKey, Map<String, Object> rootNode, List<Map<String, Object>> treeNodes) {
		Object rootIdValue = rootNode.get(idKey);
		Object rootPidValue = rootNode.get(pidKey);
		if(null==rootIdValue || null==rootPidValue) 
			throw new EasyCommonException("节点的id或pid为空.【node:" + rootNode + "】");
		// 查找第一层的children
		List<Map<String, Object>> nodeWithoutChildren = new ArrayList<Map<String, Object>>();
		for(int i=0;i<treeNodes.size();i++) {
			Map<String, Object> node = treeNodes.get(i);
			Object idValue = node.get(idKey);
			Object pIdValue = node.get(pidKey);
			if(null==idValue || null==pIdValue) 
				throw new EasyCommonException("节点的id或pid为空.【node:" + node + "】");
			if(pIdValue.equals(rootIdValue)) {
				if(null==rootNode.get("children")) rootNode.put("children", new ArrayList<Map<String, Object>>());
				List<Map<String, Object>> children = (List<Map<String, Object>>) rootNode.get("children");
				children.add(node);
				nodeWithoutChildren.add(node);
				treeNodes.remove(i);
				i--;
			}
		}
		// 查找第二层，以及以后层的children
		while(nodeWithoutChildren.size()>0 && treeNodes.size()>0) {
			List<Map<String, Object>> tmpNodeWithoutChildren = new ArrayList<Map<String, Object>>();
			for(int j=0;j<nodeWithoutChildren.size();j++) {
				Map<String, Object> tmpRoot = nodeWithoutChildren.get(j);
				Object idValue_root = tmpRoot.get(idKey);
				Object pIdValue_root = tmpRoot.get(pidKey);
				if(null==idValue_root || null==pIdValue_root) 
					throw new EasyCommonException("节点的id或pid为空.【node:" + tmpRoot + "】");
				
				for(int i=0;i<treeNodes.size();i++) {
					Map<String, Object> node = treeNodes.get(i);
					Object idValue_tmp = node.get(idKey);
					Object pIdValue_tmp = node.get(pidKey);
					if(null==idValue_tmp || null==pIdValue_tmp) 
						throw new EasyCommonException("节点的id或pid为空.【node:" + node + "】");
					
					if(pIdValue_tmp.equals(idValue_root)) {
						if(null==tmpRoot.get("children")) tmpRoot.put("children", new ArrayList<Map<String, Object>>());
						List<Map<String, Object>> children = (List<Map<String, Object>>) tmpRoot.get("children");
						children.add(node);
						tmpNodeWithoutChildren.add(node);
						treeNodes.remove(i);
						i--;
					} 
				}
			}
			nodeWithoutChildren = tmpNodeWithoutChildren;
		}
	}
	
	/**
	 * 递归方式
	 * @Date	2018年4月13日 下午4:08:13 <br/>
	 * @author  zhangST
	 * @param idKey
	 * @param pidKey
	 * @param rootNode
	 * @param treeNodes
	 */
	private void recursiveNodes(String idKey, String pidKey, Map<String, Object> rootNode, List<Map<String, Object>> treeNodes) {
		Object rootIdValue = rootNode.get(idKey);
		Object rootPidValue = rootNode.get(pidKey);
		if(null==rootIdValue || null==rootPidValue) 
			throw new EasyCommonException("节点的id或pid为空.【node:" + rootNode + "】");
		for(int i=0;i<treeNodes.size();i++) {
			Map<String, Object> node = treeNodes.get(i);
			Object idValue = node.get(idKey);
			Object pIdValue = node.get(pidKey);
			if(null==idValue || null==pIdValue) 
				throw new EasyCommonException("节点的id或pid为空.【node:" + node + "】");
			if(pIdValue.equals(rootIdValue)) {
				if(null==rootNode.get("children")) rootNode.put("children", new ArrayList<Map<String, Object>>());
				List<Map<String, Object>> children = (List<Map<String, Object>>) rootNode.get("children");
				children.add(node);
				this.recursiveNodes(idKey, pidKey, node, treeNodes);
			}
		}
	}
	
	public static void main(String[] args) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id_", "root");
		root.put("pid_", "-1");
		Map<String, Object> treeNode1 = new HashMap<String, Object>();
		treeNode1.put("id_", "1");
		treeNode1.put("pid_", "root");
        Map<String, Object> treeNode2 = new HashMap<String, Object>();  
        treeNode2.put("id_", "2");
        treeNode2.put("pid_", "root");
        Map<String, Object> treeNode3 = new HashMap<String, Object>(); 
        treeNode3.put("id_", "3");
        treeNode3.put("pid_", "1");
        Map<String, Object> treeNode4 = new HashMap<String, Object>();  
        treeNode4.put("id_", "4");
        treeNode4.put("pid_", "1");
        Map<String, Object> treeNode5 = new HashMap<String, Object>();  
        treeNode5.put("id_", "5");
        treeNode5.put("pid_", "1");
        Map<String, Object> treeNode6 = new HashMap<String, Object>(); 
        treeNode6.put("id_", "6");
        treeNode6.put("pid_", "2");
        Map<String, Object> treeNode7 = new HashMap<String, Object>(); 
        treeNode7.put("id_", "7");
        treeNode7.put("pid_", "2");
        Map<String, Object> treeNode8 = new HashMap<String, Object>();
        treeNode8.put("id_", "8");
        treeNode8.put("pid_", "2");
        Map<String, Object> treeNode9 = new HashMap<String, Object>();
        treeNode9.put("id_", "9");
        treeNode9.put("pid_", "8");
  
        List<Map<String, Object>> treeNodes = new ArrayList<Map<String, Object>>();  
  
        treeNodes.add(root);  
        treeNodes.add(treeNode1);  
        treeNodes.add(treeNode2);  
        treeNodes.add(treeNode3);  
        treeNodes.add(treeNode4);  
        treeNodes.add(treeNode5);  
        treeNodes.add(treeNode6);  
        treeNodes.add(treeNode7);  
        treeNodes.add(treeNode8);  
        treeNodes.add(treeNode9);  
  
        Map<String, Object> root2 = new HashMap<String, Object>();
        root2.put("id_", "root2");
        root2.put("pid_", "-1");
		Map<String, Object> treeNode12 = new HashMap<String, Object>();
		treeNode12.put("id_", "12");
		treeNode12.put("pid_", "root2");
		Map<String, Object> treeNode22 = new HashMap<String, Object>();  
		treeNode22.put("id_", "22");
		treeNode22.put("pid_", "root2");
		Map<String, Object> treeNode32 = new HashMap<String, Object>();  
		treeNode32.put("id_", "32");
		treeNode32.put("pid_", "root2");
		Map<String, Object> treeNode42 = new HashMap<String, Object>();   
		treeNode42.put("id_", "42");
		treeNode42.put("pid_", "12");
		Map<String, Object> treeNode52 = new HashMap<String, Object>();  
		treeNode52.put("id_", "52");
		treeNode52.put("pid_", "12");
				
		treeNodes.add(root2);  
		treeNodes.add(treeNode12);  
		treeNodes.add(treeNode22);  
		treeNodes.add(treeNode32);  
		treeNodes.add(treeNode42);  
		treeNodes.add(treeNode52);  
        
		EasyTreeCustomBuilder builder = new EasyTreeCustomBuilder();
        String idKey = "id_";
        String pidKey = "pid_";
        String idValue = "-1";
        
//        Map<String, Object> result = root;
//        builder.iterNodes(idKey, pidKey, result, treeNodes);	// 迭代
////        builder.recursiveNodes(idKey, pidKey, result, treeNodes);	// 递归
//        String json = JSON.toJSONString(result);
//        System.out.println(json);
        
//        Map<String, Object> result1 = builder.buildTree(idKey, pidKey, root, treeNodes);	// 单棵树
//        String json1 = JSON.toJSONString(result1);
//        System.out.println(json1);
        
//        Map<String, Object> result2 = builder.buildTree(idKey, pidKey, idValue, treeNodes);	// 单棵树
//        String json2 = JSON.toJSONString(result2);
//        System.out.println(json2);
        
        List<Map<String, Object>> result3 = builder.buildMultiTree(idKey, pidKey, idValue, treeNodes);	// 多棵树
        String json3 = JSON.toJSONString(result3);
        System.out.println(json3);
        
	}
	
}

