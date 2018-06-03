package com.yffd.easy.demo.shiro.custom.jedis;

import java.util.List;
import java.util.Set;

public interface IJedisManager {

	public static final int DB_INDEX = 0;
	public static final String NAME_ID = "shiro";
	
    int getDbIndex();
	String getName();

	byte[] get(final byte[] key);
	String get(final String key);
	
	String set(final byte[] key, final byte[] value);
	String set(final String key, final String value);
	
	String setex(final byte[] key, final byte[] value, final int expireSeconds);
	String setex(final String key, final String value, final int expireSeconds);

	Long del(final byte[] key);
	Long del(final String key);
	
	Long len(final byte[] keyPattern);
	Long len(final String keyPattern);
	
	Set<byte[]> keys(final byte[] keyPattern);
	Set<String> keys(final String keyPattern);
	
	List<byte[]> vals(final byte[] keyPattern);
	List<String> vals(final String keyPattern);
	
	
	byte[] hget(final byte[] key, final byte[] field);
	String hget(final String key, final String field);
	
	Long hset(final byte[] key, final byte[] field, final byte[] value);
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
