package com.yffd.easy.common.core.db.dialect;
/**
 * @Description  数据库方言接口.
 * @Date		 2017年5月19日 下午3:25:23 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface IDialect {
	
	/**
	 * 
	 * getLimitString:生成具有分页功能的SQL语句. <br/>
	 * @Date	2017年5月19日 下午3:26:50 <br/>
	 * @author  zhangST
	 * @param querySqlString	原sql语句
	 * @param offset			分页开始位置
	 * @param limit				每页限制
	 * @return
	 */
    public abstract String getLimitString(String querySqlString, long offset, long limit);
    
    /**
     * 
     * getCountString:生成具有count功能的SQL语句. <br/>
     * @Date	2017年5月19日 下午3:27:52 <br/>
     * @author  zhangST
     * @param querySqlString	原sql语句
     * @return
     */
    public abstract String getCountString(String querySqlString);
    
    /**
     * 
     * @Description  数据库种类枚举.
     * @Date		 2017年5月19日 下午3:29:18 <br/>
     * @author       zhangST
     * @version		 1.0
     * @since		 JDK 1.7+
     * @see
     */
    public static enum Type {
        MYSQL, ORACLE
    }
}

