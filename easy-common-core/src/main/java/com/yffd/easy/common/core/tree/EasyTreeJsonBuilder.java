package com.yffd.easy.common.core.tree;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月13日 下午5:49:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyTreeJsonBuilder {

	/**
	 * 单棵树
	 * @Date	2018年4月16日 下午3:33:34 <br/>
	 * @author  zhangST
	 * @param idKeyName
	 * @param pidKeyName
	 * @param rootNodeBean
	 * @param treeNodes
	 * @return
	 */
	public String buildTreeJson(String idKeyName, String pidKeyName, Object rootNodeBean, List<?> treeNodes) {
		Map<String, Object> rootNodeMap = this.bean2map(rootNodeBean);
		Object rootIdValue = rootNodeMap.get(idKeyName);
		if(null==rootIdValue) throw new EasyCommonException("根节点的id为空.【rootNode:" + rootNodeMap + "】");
		if(null==treeNodes || treeNodes.size()==0) return null;
		this.filterChilren(idKeyName, pidKeyName, rootNodeMap, treeNodes);
		return JSON.toJSONString(rootNodeMap);
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月16日 下午3:36:46 <br/>
	 * @author  zhangST
	 * @param idKeyName
	 * @param pidKeyName
	 * @param rootNodes
	 * @param treeNodes
	 * @return
	 */
	public String buildMultiTreeJson(String idKeyName, String pidKeyName, List<Object> rootNodes, List<?> treeNodes) {
		if(null==rootNodes || rootNodes.size()==0 || null==treeNodes || treeNodes.size()==0) throw new EasyCommonException("参数错误");
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<rootNodes.size();i++) {
			Object rootNodeBean = rootNodes.get(i);
			Map<String, Object> rootNodeMap = this.bean2map(rootNodeBean);
			Object rootIdValue = rootNodeMap.get(idKeyName);
			if(null==rootIdValue) throw new EasyCommonException("根节点的id为空.【rootNode:" + rootNodeMap + "】");
			if(null==treeNodes || treeNodes.size()==0) return null;
			this.filterChilren(idKeyName, pidKeyName, rootNodeMap, treeNodes);
			treeList.add(rootNodeMap);
		}
		return JSON.toJSONString(treeList);
	}
	
	/**
	 * 单棵树
	 * @Date	2018年4月16日 下午3:31:47 <br/>
	 * @author  zhangST
	 * @param nodeClazz
	 * @param idKeyName
	 * @param pidKeyName
	 * @param rootPidValue
	 * @param treeNodes
	 * @return
	 */
	public String buildTreeJson(Class<?> nodeClazz, String idKeyName, String pidKeyName, Object rootPidValue, List<?> treeNodes) {
		List<Map<String, Object>> resultList = this.buildMultiTree(nodeClazz, idKeyName, pidKeyName, rootPidValue, treeNodes);
		if(null==resultList) return null;
		if(resultList.size()>1) throw new EasyCommonException("转换出多棵树.");
		return JSON.toJSONString(resultList);
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月16日 下午3:41:45 <br/>
	 * @author  zhangST
	 * @param nodeClazz
	 * @param idKeyName
	 * @param pidKeyName
	 * @param rootPidValue
	 * @param treeNodes
	 * @return
	 */
	public String buildMultiTreeJson(Class<?> nodeClazz, String idKeyName, String pidKeyName, Object rootPidValue, List<?> treeNodes) {
		List<Map<String, Object>> resultList = this.buildMultiTree(nodeClazz, idKeyName, pidKeyName, rootPidValue, treeNodes);
		return JSON.toJSONString(resultList);
	}
	
	/**
	 * 多棵树
	 * @Date	2018年4月16日 下午3:31:52 <br/>
	 * @author  zhangST
	 * @param nodeClazz
	 * @param idKeyName
	 * @param pidKeyName
	 * @param rootPidValue
	 * @param treeNodes
	 * @return
	 */
	protected List<Map<String, Object>> buildMultiTree(Class<?> nodeClazz, String idKeyName, String pidKeyName, Object rootPidValue, List<?> treeNodes) {
		if(EasyStringCheckUtils.isEmpty(idKeyName) || EasyStringCheckUtils.isEmpty(pidKeyName) 
				|| null==rootPidValue) throw new EasyCommonException("参数【idKeyName | pidKeyName | rootPidValue】为空.");
		if(null==treeNodes || treeNodes.size()==0) return null;
		Map<String, Object> rootNodeMap = new HashMap<String, Object>();
		rootNodeMap.put(idKeyName, rootPidValue);
		rootNodeMap.put(pidKeyName, "tmp");
		this.filterChilren(idKeyName, pidKeyName, rootNodeMap, treeNodes);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) rootNodeMap.get("children");
		return resultList;
	}
	
	private void filterChilren(String idKeyName, String pidKeyName, Map<String, Object> rootNodeMap, List<?> treeNodeBeans) {
//		this.recursiveNodes(idKeyName, pidKeyName, rootNodeMap, treeNodeBeans);
		this.iterNodes(idKeyName, pidKeyName, rootNodeMap, treeNodeBeans);
	}
	
	
	private void iterNodes(String idKeyName, String pidKeyName, Map<String, Object> rootNodeMap, List<?> treeNodeBeans) {
		Object rootIdValue = rootNodeMap.get(idKeyName);
		Object rootPidValue = rootNodeMap.get(pidKeyName);
		if(null==rootIdValue || null==rootPidValue) 
			throw new EasyCommonException("节点的id或pid为空.【node:" + rootNodeMap + "】");
		// 查找第一层的children
		List<Map<String, Object>> nodeWithoutChildren = new ArrayList<Map<String, Object>>();
		for(int i=0;i<treeNodeBeans.size();i++) {
			Object nodeBean = treeNodeBeans.get(i);
			Map<String, Object> nodeMap = this.bean2map(nodeBean);
			Object idValue = nodeMap.get(idKeyName);
			Object pIdValue = nodeMap.get(pidKeyName);
			if(null==idValue || null==pIdValue) 
				throw new EasyCommonException("节点的id或pid为空.【node:" + nodeBean + "】");
			if(pIdValue.equals(rootIdValue)) {
				if(null==rootNodeMap.get("children")) rootNodeMap.put("children", new ArrayList<Map<String, Object>>());
				List<Map<String, Object>> children = (List<Map<String, Object>>) rootNodeMap.get("children");
				children.add(nodeMap);
				nodeWithoutChildren.add(nodeMap);
				treeNodeBeans.remove(i);
				i--;
			}
		}
		// 查找第二层，以及以后层的children
		while(nodeWithoutChildren.size()>0 && treeNodeBeans.size()>0) {
			List<Map<String, Object>> tmpNodeWithoutChildren = new ArrayList<Map<String, Object>>();
			for(int j=0;j<nodeWithoutChildren.size();j++) {
				Map<String, Object> tmpRoot = nodeWithoutChildren.get(j);
				Object idValue_root = tmpRoot.get(idKeyName);
				Object pIdValue_root = tmpRoot.get(pidKeyName);
				if(null==idValue_root || null==pIdValue_root) 
					throw new EasyCommonException("节点的id或pid为空.【node:" + tmpRoot + "】");
				
				for(int i=0;i<treeNodeBeans.size();i++) {
					Object node = treeNodeBeans.get(i);
					Map<String, Object> tmpNode = this.bean2map(node);
					Object idValue_tmp = tmpNode.get(idKeyName);
					Object pIdValue_tmp = tmpNode.get(pidKeyName);
					if(null==idValue_tmp || null==pIdValue_tmp) 
						throw new EasyCommonException("节点的id或pid为空.【node:" + node + "】");
					
					if(pIdValue_tmp.equals(idValue_root)) {
						if(null==tmpRoot.get("children")) tmpRoot.put("children", new ArrayList<Map<String, Object>>());
						List<Map<String, Object>> children = (List<Map<String, Object>>) tmpRoot.get("children");
						children.add(tmpNode);
						tmpNodeWithoutChildren.add(tmpNode);
						treeNodeBeans.remove(i);
						i--;
					} 
				}
			}
			nodeWithoutChildren = tmpNodeWithoutChildren;
		}
	}
	
	/**
	 * 递归方式
	 * @Date	2018年4月16日 下午3:28:14 <br/>
	 * @author  zhangST
	 * @param idKeyName
	 * @param pidKeyName
	 * @param rootNodeMap
	 * @param treeNodeBeans
	 */
	private void recursiveNodes(String idKeyName, String pidKeyName, Map<String, Object> rootNodeMap, List<?> treeNodeBeans) {
		Object rootIdValue = rootNodeMap.get(idKeyName);
		Object rootPidValue = rootNodeMap.get(pidKeyName);
		if(null==rootIdValue || null==rootPidValue) 
			throw new EasyCommonException("节点的id或pid为空.【node:" + rootNodeMap + "】");
		for(int i=0;i<treeNodeBeans.size();i++) {
			Object nodeBean = treeNodeBeans.get(i);
			Map<String, Object> nodeMap = this.bean2map(nodeBean);
			Object idValue = nodeMap.get(idKeyName);
			Object pIdValue = nodeMap.get(pidKeyName);
			if(null==idValue || null==pIdValue) 
				throw new EasyCommonException("节点的id或pid为空.【node:" + nodeBean + "】");
			if(pIdValue.equals(rootIdValue)) {
				if(null==rootNodeMap.get("children")) rootNodeMap.put("children", new ArrayList<Map<String, Object>>());
				List<Map<String, Object>> children = (List<Map<String, Object>>) rootNodeMap.get("children");
				children.add(nodeMap);
				this.recursiveNodes(idKeyName, pidKeyName, nodeMap, treeNodeBeans);
			}
		}
	}
	
	private Map<String, Object> bean2map(Object beanInstance) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(beanInstance.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if("class".equals(name)) continue;
				Method method = pd.getReadMethod();
				Object value = method.invoke(beanInstance);
				map.put(name, value);
			}
			return map;
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new EasyCommonException("JavaBean转换Map错误【class="+beanInstance.getClass()+"】", e);
		}
	}
	
}

