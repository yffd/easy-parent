package com.yffd.easy.demo.shiro.model;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 上午10:20:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pmsId;
	private String pmsName;
	public String getPmsId() {
		return pmsId;
	}
	public void setPmsId(String pmsId) {
		this.pmsId = pmsId;
	}
	public String getPmsName() {
		return pmsName;
	}
	public void setPmsName(String pmsName) {
		this.pmsName = pmsName;
	}
	
}

