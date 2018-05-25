package com.yffd.easy.framework.common.service.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.exception.EasyCommonException;
import com.yffd.easy.common.core.tree.sample.EasySampleTree;
import com.yffd.easy.common.core.tree.sample.EasySampleTreeBuilder;
import com.yffd.easy.common.core.tree.sample.EasySampleTreeJson;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.constants.CommonConstants;
import com.yffd.easy.framework.common.persist.entity.CommonPartialTreeEntity;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.persist.mybatis.dao.MybatisCustomDao;
import com.yffd.easy.framework.common.util.SpringBeanUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月8日 下午2:36:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonServiceTreeSupport<E extends CommonPartialTreeEntity> {
	public static final String SQL_ID_SELECT_CHILDREN_NODES = "selectChildrenNodes";
	public static final String SQL_ID_SELECT_PARENT_NODES = "selectParentNodes";
	public static final String SQL_ID_COUNT_LAYER = "countLayer";
	public static final String SQL_ID_UPDATE_NODES = "updateNodes";
	public static final String SQL_ID_DELETE_NODES = "deleteNodes";
	public static final String SQL_ID_UPDATE_LEFT_FOR_ADD = "updateLeftForAdd";
	public static final String UPDATE_RIGHT_FOR_ADD = "updateRightForAdd";
	public static final String UPDATE_LEFT_FOR_DEL = "updateLeftForDel";
	public static final String UPDATE_RIGHT_FOR_DEL = "updateRightForDel";
	
	protected MybatisCustomDao commonCustomDao = (MybatisCustomDao) SpringBeanUtils.getBean("mybatisCustomDao");
	
	protected IMybatisCommonDao<?> bindDao;
	
	public CommonServiceTreeSupport(IMybatisCommonDao<?> bindDao) {
		this.bindDao = bindDao;
	}

	private String getStatement(String sqlId) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.bindDao.getSqlNamespace()).append(".").append(sqlId);
        return sb.toString();
	}
	
	public E findNode(E node) {
		if(null==node) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
		return this.commonCustomDao.customSelectOneBy(this.getStatement(CommonConstants.SQL_ID_SELECT_ONE_BY), params);
	}
	
	public long countNodeLayer(E node) {
		if(null==node) return 0;
		E resultNode = this.findNode(node);
		if(null==resultNode) return 0;
		return this.commonCustomDao.customSelectOneBy(this.getStatement(SQL_ID_COUNT_LAYER), resultNode);
	}
	
//	public boolean existTree(E node) {
//		if(null==node) throw new EasyCommonException("参数为空"); 
//		if(EasyStringCheckUtils.isEmpty(node.getTreeId())) throw new EasyCommonException("treeId、nodeCode为空"); 
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
//		int result = this.commonCustomDao.customSelectCountBy(this.getStatement(CommonConstants.SQL_ID_SELECT_COUNT_BY), params);
//		return (result > 0);
//	}
	
	public boolean existNode(E node) {
		if(null==node) throw new EasyCommonException("参数为空"); 
		String nodeCode = node.getNodeCode();
		if(EasyStringCheckUtils.isEmpty(nodeCode)) throw new EasyCommonException("nodeCode为空"); 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
		int result = this.commonCustomDao.customSelectCountBy(this.getStatement(CommonConstants.SQL_ID_SELECT_COUNT_BY), params);
		return (result > 0);
	}
	
	// root section
	public List<E> findRootNodes(E node) {
		if(null==node) return null;
		node.setParentCode("root");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
		return this.commonCustomDao.customSelectListBy(this.getStatement(CommonConstants.SQL_ID_SELECT_LIST_BY), params);
	}
	
	public int addRootNode(E node) {
		if(null==node) throw new EasyCommonException("参数为空"); 
		String treeId = node.getTreeId();
		String nodeCode = node.getNodeCode();
		if(EasyStringCheckUtils.isEmpty(treeId) || EasyStringCheckUtils.isEmpty(nodeCode)) throw new EasyCommonException("treeId、nodeCode为空");
		node.setNodeLeft(1);
		node.setNodeRight(2);
		node.setParentCode("root");
		node.setNodeLayer(1);
		return this.commonCustomDao.customInsertBy(this.getStatement(CommonConstants.SQL_ID_INSERT_ONE_BY), node);
	}
	
	
	// add child section
//	public E findParentNode(E node) {
//		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId()) 
//				|| EasyStringCheckUtils.isEmpty(node.getParentCode())) throw new EasyCommonException("查询父节点时，treeId 和 parentCode 不能为空");
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
//		return this.commonCustomDao.customSelectOneBy(this.getStatement(CommonConstants.SQL_ID_SELECT_ONE_BY), params);
//	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addChildNode(E childNode, E parentNode) {
		if(null==childNode) throw new EasyCommonException("参数为空"); 
		if(EasyStringCheckUtils.isEmpty(childNode.getTreeId()) || EasyStringCheckUtils.isEmpty(childNode.getNodeCode())) 
			throw new EasyCommonException("添加树节点的treeId 和 nodeCode 不能为空");
		E resultParentNode = this.findNode(parentNode);
		if(null==resultParentNode) return 0;
		// 先更新节点左右偏序号，再插入节点，顺序不能变
		// 1.先更新节点左右偏序号
		this.updateLeftForAdd(resultParentNode);			// 修改左编号
		this.updateRightForAdd(resultParentNode);  			// 修改右编号
		
		childNode.setNodeLeft(resultParentNode.getNodeRight());
		childNode.setNodeRight(resultParentNode.getNodeRight() + 1);
		childNode.setParentCode(resultParentNode.getNodeCode());
		childNode.setNodeLayer(resultParentNode.getNodeLayer() + 1);
		// 2.再插入节点
		int num = this.commonCustomDao.customInsertBy(this.getStatement(CommonConstants.SQL_ID_INSERT_ONE_BY), childNode);
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
	public int updateNodeWithChildren(E node, E nodeOld) {
		if(null==node || null==nodeOld) throw new EasyCommonException("参数为空"); 
		if(EasyStringCheckUtils.isEmpty(nodeOld.getTreeId()) || EasyStringCheckUtils.isEmpty(nodeOld.getNodeCode())) 
			throw new EasyCommonException("treeId、 nodeCode 不能为空");
		E resultUpdateNode = this.findNode(nodeOld);
		if(null==resultUpdateNode) return 0;
		// 不进行更新属性
		node.setTreeId(null);
		node.setNodeLeft(null);
		node.setNodeRight(null);
		node.setParentCode(null);
		
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(resultUpdateNode.getTreeId());
		entity.setNodeLeft(resultUpdateNode.getNodeLeft());
		entity.setNodeRight(resultUpdateNode.getNodeRight());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
		params.put(CommonConstants.PARAM_NAME_ENTITY_OLD, entity);
		int num = this.commonCustomDao.customUpdateBy(this.getStatement(SQL_ID_UPDATE_NODES), params);
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
	public int deleteNodeWithChildren(E node) {
		if(null==node) return 0;
		E resultDeleteNode = this.findNode(node);
		if(null==resultDeleteNode) return 0;
		// 先删除节点，再更新节点左右偏序号，顺序不能变
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(resultDeleteNode.getTreeId());
		entity.setNodeLeft(resultDeleteNode.getNodeLeft());
		entity.setNodeRight(resultDeleteNode.getNodeRight());
		
		// 1.先删除节点
		int delNum = this.commonCustomDao.customDeleteBy(this.getStatement(SQL_ID_DELETE_NODES), entity);	// 删除节点、及子节点
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
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(node.getTreeId());
		entity.setNodeRight(node.getNodeRight());
		entity.setNodeStep(2);
		return this.commonCustomDao.customUpdateBy(this.getStatement(SQL_ID_UPDATE_LEFT_FOR_ADD), entity);
	}
	
	private int updateRightForAdd(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId())
				|| null==node.getNodeRight()) throw new EasyCommonException("添加树节点时，修改右偏序号错误"); 
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(node.getTreeId());
		entity.setNodeRight(node.getNodeRight());
		entity.setNodeStep(2);
		return this.commonCustomDao.customUpdateBy(this.getStatement(UPDATE_RIGHT_FOR_ADD), entity);
	}
	
	private int updateLeftForDel(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId())
				|| null==node.getNodeRight() || null==node.getNodeLeft()) throw new EasyCommonException("删除树节点时，修改左偏序号错误"); 
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(node.getTreeId());
		entity.setNodeLeft(node.getNodeLeft());
		int nodeStep = node.getNodeRight() - node.getNodeLeft() + 1;
		entity.setNodeStep(nodeStep);
		return this.commonCustomDao.customUpdateBy(this.getStatement(UPDATE_LEFT_FOR_DEL), entity);
	}
	
	private int updateRightForDel(E node) {
		if(null==node || EasyStringCheckUtils.isEmpty(node.getTreeId())
				|| null==node.getNodeRight() || null==node.getNodeLeft()) throw new EasyCommonException("删除树节点时，修改右偏序号错误"); 
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(node.getTreeId());
		entity.setNodeRight(node.getNodeRight());
		int nodeStep = node.getNodeRight() - node.getNodeLeft() + 1;
		entity.setNodeStep(nodeStep);
		return this.commonCustomDao.customUpdateBy(this.getStatement(UPDATE_RIGHT_FOR_DEL), entity);
	}
	
	//////////////////////////// node list //////////////////////////////////////////////////////////
	
	public List<E> findNodes(E node) {
		if(null==node) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_ENTITY, node);
		return this.commonCustomDao.customSelectListBy(this.getStatement(CommonConstants.SQL_ID_SELECT_LIST_BY), params);
	}
	
	public List<E> findNodeWithChildren(E node) {
		return this.findNodeWithChildren(node, -1, -1, true);
	}
	public List<E> findNodeWithChildren(E node, int relativeBeginLayer) {
		return this.findNodeWithChildren(node, relativeBeginLayer, -1, true);
	}
	public List<E> findNodeWithChildren(E node, int beginLayer, int endLayer, boolean relativeLayer) {
		if(null==node) return null;
		E result = this.findNode(node);
		if(null==result) return null;
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(result.getTreeId());
		entity.setNodeLeft(result.getNodeLeft());
		entity.setNodeRight(result.getNodeRight());
		if(relativeLayer) {
			if(beginLayer>0) entity.setBeginLayer(result.getNodeLayer() + beginLayer);
			if(endLayer>0) entity.setEndLayer(result.getNodeLayer() + endLayer);
		} else {
			if(beginLayer>0) entity.setBeginLayer(beginLayer);
			if(endLayer>0) entity.setEndLayer(endLayer);
		}
		return this.commonCustomDao.customSelectListBy(this.getStatement(SQL_ID_SELECT_CHILDREN_NODES), entity);
	}
	
	public List<E> findNodesWithChildren(E node) {
		return this.findNodesWithChildren(node, -1, -1, true);
	}
	public List<E> findNodesWithChildren(E node, int relativeBeginLayer) {
		return this.findNodesWithChildren(node, relativeBeginLayer, -1, true);
	}
	public List<E> findNodesWithChildren(E node, int beginLayer, int endLayer, boolean relativeLayer) {
		if(null==node) return null;
		List<E> nodes = this.findNodes(node);
		if(null==nodes || nodes.size()==0) return null;
		List<E> ret = new ArrayList<>();
		for(E tmp : nodes) {
			CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
			entity.setTreeId(tmp.getTreeId());
			entity.setNodeLeft(tmp.getNodeLeft());
			entity.setNodeRight(tmp.getNodeRight());
			if(relativeLayer) {
				if(beginLayer>0) entity.setBeginLayer(tmp.getNodeLayer() + beginLayer);
				if(endLayer>0) entity.setEndLayer(tmp.getNodeLayer() + endLayer);
			} else {
				if(beginLayer>0) entity.setBeginLayer(beginLayer);
				if(endLayer>0) entity.setEndLayer(endLayer);
			}
			List<E> list = this.commonCustomDao.customSelectListBy(this.getStatement(SQL_ID_SELECT_CHILDREN_NODES), entity);
			if(null!=list && list.size()>0) ret.addAll(list);
		}
		return ret;
	}
	
	public List<E> findNodeWithParent(E node) {
		return this.findNodeWithParent(node, -1, -1, true);
	}
	public List<E> findNodeWithParent(E node, int beginLayer, int endLayer, boolean relativeLayer) {
		if(null==node) return null;
		E resultNode = this.findNode(node);
		if(null==resultNode) return null;
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(resultNode.getTreeId());
		entity.setNodeLeft(resultNode.getNodeLeft());
		entity.setNodeRight(resultNode.getNodeRight());
		if(relativeLayer) {
			if(beginLayer>0) entity.setBeginLayer(resultNode.getNodeLayer() + beginLayer);
			if(endLayer>0) entity.setEndLayer(resultNode.getNodeLayer() + endLayer);
		} else {
			if(beginLayer>0) entity.setBeginLayer(beginLayer);
			if(endLayer>0) entity.setEndLayer(endLayer);
		}
		return this.commonCustomDao.customSelectListBy(this.getStatement(SQL_ID_SELECT_PARENT_NODES), entity);
	}
	
	public String findNodePath(E node) {
		List<E> parentNodeList = findNodeWithParent(node);
		if(null==parentNodeList || parentNodeList.size()==0) return null;
		StringBuffer sb = new StringBuffer();
		for(E tmp : parentNodeList) {
			sb.append(tmp.getNodeCode()).append(",");
		}
		return sb.length()>0?sb.substring(0, sb.length()-1) : sb.toString();
	}
	
	//////////////////////////// tree //////////////////////////////////////////////////////////

	EasySampleTreeBuilder<E> builder = new EasySampleTreeBuilder<E>() {
		
		@Override
		protected EasySampleTree getTreeNode(E node) {
			EasySampleTree tree = new EasySampleTree();
			tree.setNodeValue(node);
			tree.setIdValue(node.getNodeCode());
			tree.setPidValue(node.getParentCode());
			return tree;
		}

		@Override
		protected Object getIdValue(E node) {
			return node.getNodeCode();
		}

		@Override
		protected Object getPidValue(E node) {
			return node.getParentCode();
		}
		
	};
	
	public String findTreeJson(E node) {
		EasySampleTree tree = this.findTree(node);
		return EasySampleTreeJson.getTreeJson(tree);
	}
	
	public String findTreeJson(E node, int deep) {
		EasySampleTree tree = this.findTree(node, deep);
		return EasySampleTreeJson.getTreeJson(tree);
	}
	
	public EasySampleTree findTree(E node) {
		return this.findTree(node, 0);
	}
	
	public EasySampleTree findTree(E node, int deep) {
		if(null==node) return null;
		E resultNode = this.findNode(node);
		if(null==resultNode) return null;
		CommonPartialTreeEntity entity = new CommonPartialTreeEntity();
		entity.setTreeId(resultNode.getTreeId());
		entity.setNodeLeft(resultNode.getNodeLeft());
		entity.setNodeRight(resultNode.getNodeRight());
		if(deep>0) entity.setNodeLayer(resultNode.getNodeLayer() + deep);
		List<E> childrenNode = this.commonCustomDao.customSelectListBy(this.getStatement(SQL_ID_SELECT_CHILDREN_NODES), entity);
		return builder.buildTree(childrenNode, resultNode);
	}
	

}

