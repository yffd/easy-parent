package com.yffd.easy.common.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description  分页结果基类对象.
 * @Date		 2017年12月6日 下午3:17:47 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PageResult<T> implements Serializable {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = -6241544541048563186L;
	
	private PageParam pageParam; // 分页信息
	private List<T> recordList = new ArrayList<T>(0); // 本页的数据列表
	
	public PageResult() {
	}
	
	public PageResult(PageParam pageParam, List<T> recordList) {
		this.pageParam = pageParam;
		this.recordList = recordList;
	}

	public PageParam getPageParam() {
		return pageParam;
	}

	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

}

