package com.yffd.easy.demo.jedis;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 下午1:39:43 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisConfigManager {
	private static final int DB_INDEX = 0;
	private static final String HKEY_ID = "shiro";
	
	private Integer dbIndex;
    private String hkeyId;
    
    public int getDbIndex() {
		if(null==this.dbIndex) return DB_INDEX;
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public String getHkeyId() {
		if(null==this.hkeyId) return HKEY_ID;
		return hkeyId;
	}

	public void setHkeyId(String hkeyId) {
		this.hkeyId = hkeyId;
	}
}

