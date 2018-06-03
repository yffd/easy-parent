package com.yffd.easy.demo.shiro.custom.jedis;

import redis.clients.jedis.Jedis;

public class JedisConfig {

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

	public void close(Jedis jedis) {
		if(null!=jedis) jedis.close();
	}
	
}
