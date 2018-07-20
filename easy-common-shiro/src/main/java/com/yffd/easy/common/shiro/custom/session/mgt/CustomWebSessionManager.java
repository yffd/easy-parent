package com.yffd.easy.common.shiro.custom.session.mgt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.handler.ICustomSessionHandler;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月6日 下午2:41:11 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Deprecated
public class CustomWebSessionManager extends DefaultWebSessionManager {
	private static final Logger LOG = LoggerFactory.getLogger(CustomWebSessionManager.class);
	
	private ICustomSessionHandler customSessionHandler;
	
	public void setCustomSessionHandler(ICustomSessionHandler customSessionHandler) {
		this.customSessionHandler = customSessionHandler;
	}

	@Override
	public void validateSessions() {
		if (LOG.isInfoEnabled()) {
			LOG.info("Validating all active sessions...");
        }

        int invalidCount = 0;
        List<Session> invalidSessionList = new ArrayList<>();
        
        Collection<Session> activeSessions = getActiveSessions();

        if (activeSessions != null && !activeSessions.isEmpty()) {
            for (Session s : activeSessions) {
                try {
                    //simulate a lookup key to satisfy the method signature.
                    //this could probably stand to be cleaned up in future versions:
                    SessionKey key = new DefaultSessionKey(s.getId());
                    validate(s, key);
                } catch (InvalidSessionException e) {
                    if (LOG.isDebugEnabled()) {
                        boolean expired = (e instanceof ExpiredSessionException);
                        String msg = "Invalidated session with id [" + s.getId() + "]" +
                                (expired ? " (expired)" : " (stopped)");
                        LOG.debug(msg);
                    }
                    invalidCount++;
                    invalidSessionList.add(s);
                }
            }
        }

        if (invalidCount > 0) {
        	this.customSessionHandler.delete(invalidSessionList);
        }
        
        if (LOG.isInfoEnabled()) {
            String msg = "Finished session validation.";
            if (invalidCount > 0) {
                msg += "  [" + invalidCount + "] sessions were stopped.";
            } else {
                msg += "  No sessions were stopped.";
            }
            LOG.info(msg);
        }
	}

}

