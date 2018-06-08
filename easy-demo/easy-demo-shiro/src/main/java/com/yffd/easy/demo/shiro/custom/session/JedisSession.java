package com.yffd.easy.demo.shiro.custom.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午2:58:30 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSession implements Session {

	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getStartTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastAccessTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTimeout() throws InvalidSessionException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void touch() throws InvalidSessionException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws InvalidSessionException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Object> getAttributeKeys() throws InvalidSessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(Object key) throws InvalidSessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(Object key, Object value) throws InvalidSessionException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object removeAttribute(Object key) throws InvalidSessionException {
		// TODO Auto-generated method stub
		return null;
	}

}

