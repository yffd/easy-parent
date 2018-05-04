package com.yffd.easy.framework.web.view.tree;

import java.util.List;

/**
 * @Description  树节点对象.
 * @Date		 2017年9月25日 下午2:10:48 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class TreeNode {

	private String id_;
	private String pid_;
    private List<TreeNode> children;
    
    public TreeNode() {
    }
    
    public TreeNode(String id_, String pid_) {
        this.id_ = id_;
        this.pid_ = pid_;
    }
    
	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getPid_() {
		return pid_;
	}

	public void setPid_(String pid_) {
		this.pid_ = pid_;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeNode [id_=" + id_ + ", pid_=" + pid_ + "]";
	}

}

