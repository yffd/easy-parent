package com.yffd.easy.demo.shiro.custom.jedis;

import java.util.List;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;

import redis.clients.jedis.ShardedJedisPool;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午6:17:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSharedManager extends JedisConfig implements IJedisManager, Initializable, Destroyable {

	private ShardedJedisPool shardedJedisPool;
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws ShiroException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(byte[] key, byte[] value, int expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(String key, String value, int expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long del(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long len(byte[] keyPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long len(String keyPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> keys(byte[] keyPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> keys(String keyPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> vals(byte[] keyPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> vals(String keyPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(byte[] key, byte[]... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(String key, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hlen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hlen(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> hkeys(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> hvals(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> hvals(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}

