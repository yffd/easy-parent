package com.yffd.easy.demo.jedis;

import java.util.List;
import java.util.Set;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 下午1:55:28 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface IJedisCRUD {

	byte[] hget(final byte[] key, final byte[] field);
	String hget(final String key, final String field);
	
	Long hset(final byte[] key, final byte[] field, final String value);
	Long hset(final String key, final String field, final String value);
	
	Long hdel(final byte[] key, final byte[]... fields);
	Long hdel(final String key, final String... fields);
	
	Long hlen(final String key);
	Long hlen(final byte[] key);
	
	Set<byte[]> hkeys(final byte[] key);
	Set<String> hkeys(final String key);
	
	List<byte[]> hvals(final byte[] key);
	List<String> hvals(final String key);
}

