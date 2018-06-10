package com.yffd.easy.framework.web.model.easyui;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年10月13日 下午4:24:41 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyuiSearchVo {
	
	private Integer page; //分页参数：当前页码
	private Integer rows; //分页参数：每页记录数
	private String searchName;	// 单条件查询名称
	private String searchValue; // 单条件查询值
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
}

