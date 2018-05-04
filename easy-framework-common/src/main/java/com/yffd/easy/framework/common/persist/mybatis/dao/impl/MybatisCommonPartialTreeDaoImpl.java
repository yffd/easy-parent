package com.yffd.easy.framework.common.persist.mybatis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.persist.entity.CommonPartialTreeEntity;
import com.yffd.easy.framework.common.persist.mybatis.dao.support.MybatisCommonMapDaoSupport;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月4日 上午10:02:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MybatisCommonPartialTreeDaoImpl<E extends CommonPartialTreeEntity> extends MybatisCommonBaseDaoImpl<E> {
	
	public E findTreeNode(E node) {
		if(null==node) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY, node);
		return this.customSelectOneBy(MybatisCommonMapDaoSupport.SQL_ID_SELECT_ONE_BY, params, true);
	}
	
	public List<E> findChildrenNodes(E node) {
		if(null==node) return null;
		E resultNode = this.findTreeNode(node);
		if(null==resultNode) return null;
		String treeId = resultNode.getTreeId();
		if(EasyStringCheckUtils.isEmpty(treeId)) throw new EasyCommonException("treeId is null");
		node.setTreeId(treeId);
		node.setNodeLeft(resultNode.getNodeLeft());
		node.setNodeRight(resultNode.getNodeRight());
		node.setNodeLayer(null);
		
		return this.customSelectListBy("selectChildrenNodes", node, true);
	}
	
	public List<E> findChildrenNodes(E node, int layer) {
		if(null==node) return null;
		E resultNode = this.findTreeNode(node);
		if(null==resultNode) return null;
		String treeId = resultNode.getTreeId();
		if(EasyStringCheckUtils.isEmpty(treeId)) throw new EasyCommonException("treeId is null");
		node.setTreeId(treeId);
		node.setNodeLeft(resultNode.getNodeLeft());
		node.setNodeRight(resultNode.getNodeRight());
		node.setNodeLayer(resultNode.getNodeLayer() + layer);
		return this.customSelectListBy("selectChildrenNodes", node, true);
	}
	
	public List<E> findParentNodes(E node) {
		if(null==node) return null;
		E resultNode = this.findTreeNode(node);
		if(null==resultNode) return null;
		String treeId = resultNode.getTreeId();
		if(EasyStringCheckUtils.isEmpty(treeId)) throw new EasyCommonException("treeId is null");
		node.setTreeId(treeId);
		node.setNodeLeft(resultNode.getNodeLeft());
		node.setNodeRight(resultNode.getNodeRight());
		node.setNodeLayer(null);
		return this.customSelectListBy("selectParentNodes", node, true);
	}
	
	public String findNodePath(E node) {
		List<E> parentNodeList = findParentNodes(node);
		if(null==parentNodeList || parentNodeList.size()==0) return null;
		StringBuffer sb = new StringBuffer();
		for(E tmp : parentNodeList) {
			sb.append(tmp.getNodeCode()).append(",");
		}
		return sb.length()>0?sb.substring(0, sb.length()-1) : sb.toString();
	}
	
	public long countNodeLayer(E node) {
		if(null==node) return 0;
		E resultNode = this.findTreeNode(node);
		if(null==resultNode) return 0;
		return this.customSelectOneBy("countLayer", resultNode, true);
	}
	
	
	// add root section
	public List<E> findRootNodes(E node) {
		if(null==node) return null;
		node.setParentCode("root");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY, node);
		return this.customSelectListBy(MybatisCommonMapDaoSupport.SQL_ID_SELECT_LIST_BY, params, true);
	}
	
	public boolean existRootNode(E node) {
		if(null==node) return false;
		node.setParentCode("root");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY, node);
		int result = this.customSelectCountBy(MybatisCommonMapDaoSupport.SQL_ID_SELECT_COUNT_BY, params, true);
		return (result > 0);
	}
	
	public int addRootNode(E node) {
		if(null==node) return 0;
		String treeId = node.getTreeId();
		String nodeCode = node.getNodeCode();
		if(EasyStringCheckUtils.isEmpty(treeId) || EasyStringCheckUtils.isEmpty(nodeCode)) return 0;
		node.setNodeLeft(1);
		node.setNodeRight(2);
		node.setParentCode("root");
		node.setNodeLayer(1);
		return this.customInsertBy(MybatisCommonMapDaoSupport.SQL_ID_INSERT_ONE_BY, node, true);
	}
	
	
	// add child section
	public E findParentNode(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(node.getParentCode())) return null;
		CommonPartialTreeEntity paramNode = new CommonPartialTreeEntity();
		paramNode.setTreeId(node.getTreeId());
		paramNode.setNodeCode(node.getParentCode());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY, paramNode);
		return this.customSelectOneBy(MybatisCommonMapDaoSupport.SQL_ID_SELECT_ONE_BY, params, true);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addChildNode(E node) {
		if(null==node) return 0;
		String treeId = node.getTreeId();
		String nodeCode = node.getNodeCode();
		if(EasyStringCheckUtils.isEmpty(treeId) || EasyStringCheckUtils.isEmpty(nodeCode)) 
			throw new EasyCommonException("添加树节点的treeId 和 nodeCode 不能为空");
		E resultParentNode = this.findParentNode(node);
		if(null==resultParentNode) return 0;
		// 先更新节点左右偏序号，再插入节点，顺序不能变
		// 1.先更新节点左右偏序号
		this.updateLeftForAdd(resultParentNode);			// 修改左编号
		this.updateRightForAdd(resultParentNode);  			// 修改右编号
		
		node.setNodeLeft(resultParentNode.getNodeRight());
		node.setNodeRight(resultParentNode.getNodeRight() + 1);
		node.setParentCode(resultParentNode.getNodeCode());
		node.setNodeLayer(resultParentNode.getNodeLayer() + 1);
		// 2.再插入节点
		int num = this.customInsertBy(MybatisCommonMapDaoSupport.SQL_ID_INSERT_ONE_BY, node, true);
		return num;
	}
	
	
	// update node section
	/**
	 * 级联更新节点，以及其子孙节点.</br>
	 * 			不更新的字段：treeId、nodeLeft、nodeRight、parentCode
	 * @Date	2018年4月11日 上午10:18:38 <br/>
	 * @author  zhangST
	 * @param node
	 * @param nodeOld
	 * @return
	 */
	public int updateNodes(E node, E nodeOld) {
		if(null==node || null==nodeOld) return 0;
		E resultUpdateNode = this.findTreeNode(nodeOld);
		if(null==resultUpdateNode) return 0;
		// 不进行更新属性
		node.setTreeId(null);
		node.setNodeLeft(null);
		node.setNodeRight(null);
		node.setParentCode(null);
		
		nodeOld.setTreeId(resultUpdateNode.getTreeId());
		nodeOld.setNodeLeft(resultUpdateNode.getNodeLeft());
		nodeOld.setNodeRight(resultUpdateNode.getNodeRight());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY, node);
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY_OLD, nodeOld);
		int num = this.customUpdateBy("updateNodes", params, true);
		return num;
	}
	

	// delete node section
	/**
	 * 级联删除节点，以及其子孙节点
	 * @Date	2018年4月11日 上午10:19:32 <br/>
	 * @author  zhangST
	 * @param node
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteNodes(E node) {
		if(null==node) return 0;
		E resultDeleteNode = this.findTreeNode(node);
		if(null==resultDeleteNode) return 0;
		// 先删除节点，再更新节点左右偏序号，顺序不能变
		node.setTreeId(resultDeleteNode.getTreeId());
		node.setNodeLeft(resultDeleteNode.getNodeLeft());
		node.setNodeRight(resultDeleteNode.getNodeRight());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonMapDaoSupport.PARAM_NAME_ENTITY, node);
		// 1.先删除节点
		int delNum = this.customDeleteBy("deleteNodes", params, true);	// 删除节点、及子节点
//		int childrenNodes = (resultDeleteNode.getNodeRight() - resultDeleteNode.getNodeLeft() - 1) / 2; // 拥有子节点数
//		if(childrenNodes!=delNum) throw EasyBizException.newInstance("待删除节点数与已删除节点数不相等");
		// 2.再更新节点左右偏序号
		this.updateLeftForDel(resultDeleteNode);		// 修改左编号
		this.updateRightForDel(resultDeleteNode);  		// 修改右编号
		return delNum;
	}
	
	
	private int updateLeftForAdd(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId()) ||
				null==node.getNodeRight()) throw new EasyCommonException("添加树节点时，修改左偏序号错误"); 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("treeId", node.getTreeId());
		paramMap.put("nodeRight", node.getNodeRight());
		paramMap.put("nodeStep", 2);
		return this.customUpdateBy("updateLeftForAdd", paramMap, true);
	}
	
	private int updateRightForAdd(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId())
				|| null==node.getNodeRight()) throw new EasyCommonException("添加树节点时，修改右偏序号错误"); 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("treeId", node.getTreeId());
		paramMap.put("nodeRight", node.getNodeRight());
		paramMap.put("nodeStep", 2);
		return this.customUpdateBy("updateRightForAdd", paramMap, true);
	}
	
	private int updateLeftForDel(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId())
				|| null==node.getNodeRight() || null==node.getNodeLeft()) throw new EasyCommonException("删除树节点时，修改左偏序号错误"); 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("treeId", node.getTreeId());
		paramMap.put("nodeLeft", node.getNodeLeft());
		int nodeStep = node.getNodeRight() - node.getNodeLeft() + 1;
		paramMap.put("nodeStep", nodeStep);
		return this.customUpdateBy("updateLeftForDel", paramMap, true);
	}
	
	private int updateRightForDel(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId())
				|| null==node.getNodeRight() || null==node.getNodeLeft()) throw new EasyCommonException("删除树节点时，修改右偏序号错误"); 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("treeId", node.getTreeId());
		paramMap.put("nodeRight", node.getNodeRight());
		int nodeStep = node.getNodeRight() - node.getNodeLeft() + 1;
		paramMap.put("nodeStep", nodeStep);
		return this.customUpdateBy("updateRightForDel", paramMap, true);
	}
	
}

