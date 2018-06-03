package com.yffd.easy.demo.shiro.custom.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;

public class JedisSession implements ValidatingSession, Serializable {
	private static final long serialVersionUID = -2779982407100897822L;

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

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate() throws InvalidSessionException {
		// TODO Auto-generated method stub
		
	}

}
