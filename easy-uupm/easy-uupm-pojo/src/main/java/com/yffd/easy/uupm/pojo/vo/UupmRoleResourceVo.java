package com.yffd.easy.uupm.pojo.vo;

import java.util.List;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月28日 上午10:57:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmRoleResourceVo extends UupmCommonVo {
	private static final long serialVersionUID = 7973602106411883657L;

	private List<String> rsCodeList;

	public List<String> getRsCodeList() {
		return rsCodeList;
	}

	public void setRsCodeList(List<String> rsCodeList) {
		this.rsCodeList = rsCodeList;
	}
}

