package com.yffd.easy.demo.shiro.shiro.jedis;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 下午3:57:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisCustomBaseManager {
	private static final int DB_INDEX = 0;
	private static final String NAME_ID = "shiro";
	
	private String name;
	private Integer dbIndex;
    
    public int getDbIndex() {
		if(null==this.dbIndex) return DB_INDEX;
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public String getName() {
		if(null==this.name) return NAME_ID;
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

