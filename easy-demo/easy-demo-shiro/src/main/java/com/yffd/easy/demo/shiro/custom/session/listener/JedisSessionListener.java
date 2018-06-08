package com.yffd.easy.demo.shiro.custom.session.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.custom.jedis.IJedisManager;
import com.yffd.easy.demo.shiro.custom.util.ShiroSerializeUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午1:49:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSessionListener implements SessionListener {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSessionListener.class);
	
	private IJedisManager jedisManager;
	
	public IJedisManager getJedisManager() {
		return jedisManager;
	}

	public void setJedisManager(IJedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}
	
	//会话创建时触发
	@Override
	public void onStart(Session session) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session listener] : JedisSessionListener#onStart(%s) =======", session));

	}

	//退出/会话过期时触发
	@Override
	public void onStop(Session session) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session listener] : JedisSessionListener#onStop(%s) =======", session));

	}

	//会话过期时触发
	@Override
	public void onExpiration(Session session) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session listener] : JedisSessionListener#onExpiration(%s) =======", session));
		if(null==session || null==session.getId()) throw new NullPointerException("id argument cannot be null.");
		String key = this.getJedisManager().getName() + ":" + session.getId();
		byte[] byteKey = ShiroSerializeUtils.serialize(key);
		this.jedisManager.del(byteKey);
	}

}

