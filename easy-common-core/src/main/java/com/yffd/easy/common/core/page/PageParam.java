package com.yffd.easy.common.core.page;

import java.io.Serializable;

/**
 * @Description  分页信息基类对象.
 * @Date		 2017年12月6日 下午3:17:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PageParam implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = 6844568491810672063L;

	/** 开始页码为1*/
    public static final Integer PAGE_NUM_START = 1;
    /** 每页最小记录数(15) */
    public static final Integer PAGE_LIMIT_MIN = 1;
    /** 每页最大记录数(100) */
    public static final Integer PAGE_LIMIT_MAX = 100;
    
    private Integer pageNum; 		// 当前页数
    private Integer pageLimit; 	// 每页记录数
    private Integer totalRecord; 	// 总记录数
    
//    private Integer startIndex; 	// 每页开始行号
//    private Integer endIndex; 	// 每页结束行号
//    private Integer totalPage; 	// 总页数
    
    /** 是否进行自动count计算总条数，默认为自动计算：true:是，false:否 */
//    private boolean autoCount = true;
    
	public PageParam(Integer pageNum, Integer pageLimit) {
		//当前页码不能小于1，如果小于将返回最小页码
		pageNum = pageNum < 1 ? PAGE_NUM_START : pageNum;
		// pageLimit in [ PAGE_LIMIT_MIN, PAGE_LIMIT_MAX ] 范围
		pageLimit = pageLimit > PAGE_LIMIT_MAX ? PAGE_LIMIT_MAX : pageLimit < 1 ? PAGE_LIMIT_MIN : pageLimit;
		this.pageNum = pageNum;
		this.pageLimit = pageLimit;
	}
    
	/**
	 * 当前页码不能小于1，如果小于将返回最小页码{@link #PAGE_NUM_START}
	 * @Date	2018年3月23日 下午2:41:08 <br/>
	 * @author  zhangST
	 * @return
	 */
	public Integer getPageNum() {
		return this.pageNum;
	}

	/**
	 * 每页记录数
	 * @Date	2018年3月23日 下午2:43:27 <br/>
	 * @author  zhangST
	 * @return
	 */
	public Integer getPageLimit() {
		return this.pageLimit;
	}

	/**
	 * 总记录数
	 * @Date	2018年3月23日 下午3:08:03 <br/>
	 * @author  zhangST
	 * @return
	 */
	public Integer getTotalRecord() {
		return totalRecord;
	}

	/**
	 * 总记录数
	 * @Date	2018年3月23日 下午3:08:12 <br/>
	 * @author  zhangST
	 * @param totalRecord
	 */
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord < 0 ? 0 : totalRecord;
	}

	/**
	 * 总页数
	 * @Date	2018年3月23日 下午3:08:21 <br/>
	 * @author  zhangST
	 * @return
	 */
	public Integer getTotalPage() {
		return this.getTotalRecord() % this.getPageLimit() == 0 ? 
				this.getTotalRecord() / this.getPageLimit() : this.getTotalRecord() / this.getPageLimit() + 1;
	}

	/**
	 * 每页开始行号
	 * @Date	2018年3月23日 下午3:07:33 <br/>
	 * @author  zhangST
	 * @return
	 */
	public Integer getStartIndex() {
		return (this.getPageNum() - 1) * this.getPageLimit();
	}

	/**
	 * 每页结束行号
	 * @Date	2018年3月23日 下午3:07:43 <br/>
	 * @author  zhangST
	 * @return
	 */
	public Integer getEndIndex() {
		return this.getPageNum() * this.getPageLimit();
	}
    
}

