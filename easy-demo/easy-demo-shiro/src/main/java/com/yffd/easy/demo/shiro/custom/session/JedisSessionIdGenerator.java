package com.yffd.easy.demo.shiro.custom.session;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class JedisSessionIdGenerator implements SessionIdGenerator {

	private String prefix;
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public Serializable generateId(Session session) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>::"+this.getPrefix());
		String prefix = this.getPrefix();
		if(null==prefix || "".equals(prefix)) return UUID.randomUUID().toString();
		return getPrefix() + UUID.randomUUID().toString();
	}

}
