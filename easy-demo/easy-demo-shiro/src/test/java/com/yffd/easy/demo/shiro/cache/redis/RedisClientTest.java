package com.yffd.easy.demo.shiro.cache.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月31日 上午10:32:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class RedisClientTest {

	private JedisPool pool;
	
	@Before
	public void init() {
		String configLocation = "classpath:spring/spring-cache-redis.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		pool = context.getBean("jedisPool", JedisPool.class);
	}
	
	@After
	public void destroy() {
		pool.close();
	}
	
	@Test
	public void test() {
		System.out.println(pool.getResource());
	}
	
	public Jedis getClient() {
		return this.pool.getResource();
	}
	
	@Test
	public void get() {
		//获取指定 key 的值。如果 key 不存在，返回 nil 
		String key = "test_12";
        String result = this.getClient().get(key);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void set() {
		//设置一些字符串值
		String key = "test_12";
		String value = "value_12";
        String result = this.getClient().set(key, value);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void hget() {
		//获取哈希表中指定字段的值
		String hkey = "h_test_12";
		String key = "hkey_12";
        String result = this.getClient().hget(hkey, key);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void hset() {
		//为哈希表中的字段赋值
		String hkey = "h_test_12";
		String key = "hkey_12";
		String value = "hvalue_12";
        long result = this.getClient().hset(hkey, key, value);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void incr() {
		//将 key 中储存的数字值增一,如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行INCR操作
		String key = "incr_test_12";
        long result = this.getClient().incr(key);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void expire() {
		//设置key的到期时间
		String key = "incr_test_12";
		int second = 12;
        long result = this.getClient().expire(key, second);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void ttl() {
		//以秒为单位返回 key 的剩余过期时间
		String key = "incr_test_12";
        long result = this.getClient().ttl(key);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void del() {
		//根据key删除
		String key = "d_test_12";
        long result = this.getClient().del(key);
        System.out.println(result);
        this.getClient().close();
    }

	@Test
	public void hdel() {
		//删除哈希表key中的一个或多个指定字段
		String hkey = "h_test_12";
		String key = "hkey_13";
        long result = this.getClient().hdel(hkey, key);
        System.out.println(result);
        this.getClient().close();
    }
}

