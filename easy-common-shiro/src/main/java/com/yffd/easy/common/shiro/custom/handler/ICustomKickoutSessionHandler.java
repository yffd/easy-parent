package com.yffd.easy.common.shiro.custom.handler;

import java.io.Serializable;
import java.util.Deque;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月5日 下午4:00:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICustomKickoutSessionHandler {

	Deque<Serializable> getSessionIds(String userId);
	
	
	void putSessionIds(String userId, Deque<Serializable> sessionIds);
}

