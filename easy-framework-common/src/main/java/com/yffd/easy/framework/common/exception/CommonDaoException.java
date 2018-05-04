package com.yffd.easy.framework.common.exception;

import com.yffd.easy.common.core.exception.EasyCommonException;

/**
 * @Description  DAO异常封装.
 * @Date		 2017年9月22日 上午11:51:40 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonDaoException extends EasyCommonException {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = -877612146007504246L;
	
	/**
	 * 数据库操作
	 */
	public static final EasyCommonException newInstance(String msg) {
		return new EasyCommonException("5D0001", msg);
	}
	
	/**
	 * 数据库操作，插入数据为空
	 */
	public static final EasyCommonException DB_INSERT_NULL(String Statement) {
		return new EasyCommonException("5D0101", "数据库操作，插入数据为空.{" + Statement + "}");
	}
	
	/**
	 * 数据库操作，插入返回结果为0
	 */
    public static final EasyCommonException DB_INSERT_RESULT_0(String Statement) {
    	return new EasyCommonException("5D0102", "数据库操作，插入返回结果为0.{" + Statement + "}");
    }

    /**
	 * 数据库操作，更新数据为空
	 */
	public static final EasyCommonException DB_UPDATE_NULL(String Statement) {
		return new EasyCommonException("5D0201", "数据库操作，更新数据为空.{" + Statement + "}");
	}
	
    /**
     * 数据库操作，更新返回结果为0
     */
    public static final EasyCommonException DB_UPDATE_RESULT_0(String Statement) {
    	return new EasyCommonException("5D0202", "数据库操作，更新返回结果为0.{" + Statement + "}");
    }
    
    /**
	 * 数据库操作，删除数据为空
	 */
	public static final EasyCommonException DB_DELETE_NULL(String Statement) {
		return new EasyCommonException("5D0301", "数据库操作，删除数据为空.{" + Statement + "}");
	}
	
    /**
     * 数据库操作，删除返回结果为0
     */
    public static final EasyCommonException DB_DELETE_RESULT_0(String Statement) {
    	return new EasyCommonException("5D0302", "数据库操作，删除返回结果为0.{" + Statement + "}");
    }

    /**
     * 数据库操作，查询条件为空
     */
    public static final EasyCommonException DB_SELECT_BY_NULL(String Statement) {
    	return new EasyCommonException("5D0401", "数据库操作，查询条件为空.{" + Statement + "}");
    }
    
    /**
     * 数据库操作，单条查询返回结果为多条
     */
    public static final EasyCommonException DB_SELECT_ONE_RESULT_MULTI(String Statement) {
    	return new EasyCommonException("5D0402", "数据库操作，单条查询返回结果为多条.{" + Statement + "}");
    }
    
    /**
     * 数据库操作，序列生成超时
     */
    public static final EasyCommonException DB_GET_SEQ_NEXT_VALUE_ERROR(String Statement) {
    	return new EasyCommonException("5D0501", "数据库操作，序列生成超时.{" + Statement + "}");
    }
    
    /**
	 * 数据库操作，查询条件为空
	 */
	public static final EasyCommonException DB_SELECT_NULL(String Statement) {
		return new EasyCommonException("5D0601", "数据库操作，查询条件为空.{" + Statement + "}");
	}
	
	public static final EasyCommonException DB_UNSUPPORTTYPE(String Statement) {
		return new EasyCommonException("5D0701", "数据库操作，不支持类型.{" + Statement + "}");
	}
    

   
}

