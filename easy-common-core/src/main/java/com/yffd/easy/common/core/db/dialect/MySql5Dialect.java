package com.yffd.easy.common.core.db.dialect;
/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年5月19日 下午3:25:59 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MySql5Dialect implements IDialect {

	@Override
    public String getLimitString(String querySqlString, long offset, long limit) {
        return querySqlString + " limit " + offset + ", " + limit;
    }

    @Override
    public String getCountString(String querySqlString) {
        int limitIndex = querySqlString.lastIndexOf("limit");
        if(limitIndex != -1){
            querySqlString = querySqlString.substring(0, limitIndex != -1 ? limitIndex : querySqlString.length() - 1);
        }
        return "SELECT COUNT(*) FROM (" + querySqlString + ") tem";
    }
    
    public boolean supportsLimit() {
        return true;
    }

}

