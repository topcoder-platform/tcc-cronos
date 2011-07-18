/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashSet;

import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;

import com.topcoder.shared.security.LoginException;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
/**
 * <p>
 * A simple accuracy mock of WebAuthentication.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class WebAuthenticationAccuracyMock extends BasicAuthentication {
    private static HashSet<Long> hashSet = new HashSet<Long>();

	/**
     * @return the hashSet
     */
    public static HashSet<Long> getIdsSet() {
        return hashSet;
    }
    public WebAuthenticationAccuracyMock() throws Exception {
        super(new SessionPersistor(EasyMock.createNiceMock(HttpSession.class)), EasyMock.createNiceMock(TCRequest.class), EasyMock.createNiceMock(TCResponse.class));
        hashSet.clear();
    }
	public void setCookie(long uid, boolean rememberUser) {
	    if (rememberUser) {
	        hashSet.add(uid);
	    }
	}
    @Override
	public User getUser() {
		return null;
	}

	@Override
	public void login(User arg0) throws LoginException {
	}

	@Override
	public void logout() {
	}

	@Override
	public User getActiveUser() {
		User user = new User() {

			/**
             * 
             */
            private static final long serialVersionUID = -4036771389498461232L;

            @Override
			public boolean isAnonymous() {
				return false;
			}

			@Override
			public String getUserName() {
				return "Beijing2008";
			}

			@Override
			public String getPassword() {
				return "123456";
			}

			@Override
			public long getId() {
				return 100;
			}
		};
		return user;
	}

	@Override
	public void login(User u, boolean rememberUser) throws LoginException {

	}

	@Override
	public boolean isKnownUser() {
		return false;
	}

}
