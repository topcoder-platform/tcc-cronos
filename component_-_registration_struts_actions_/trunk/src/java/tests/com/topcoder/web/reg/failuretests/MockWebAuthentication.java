/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.shared.security.LoginException;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;

import com.topcoder.web.common.security.WebAuthentication;


/**
 * Mock for failure!
 *
 * @author $author$
 * @version $Revision$
  */
public class MockWebAuthentication implements WebAuthentication {
    private boolean returnNull;

    /**
     * Creates a new MockWebAuthentication object.
     *
     * @param returnNull Mock for failure!
     */
    public MockWebAuthentication(boolean returnNull) {
        this.returnNull = returnNull;
    }

    /**
     * Mock for failure!
     *
     * @return Mock for failure!
     */
    public User getActiveUser() {
        if (returnNull) {
            return null;
        }

        return new SimpleUser(1, "user", "password");
    }

    /**
     * Mock for failure!
     *
     * @return Mock for failure!
     */
    public boolean isKnownUser() {
        
        return false;
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     * @param arg1 Mock for failure!
     *
     * @throws LoginException Mock for failure!
     */
    public void login(User arg0, boolean arg1) throws LoginException {
        
    }

    /**
     * Mock for failure!
     *
     * @return Mock for failure!
     */
    public User getUser() {
        
        return null;
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     *
     * @throws LoginException Mock for failure!
     */
    public void login(User arg0) throws LoginException {
        
    }

    /**
     * Mock for failure!
     */
    public void logout() {
        
    }
}
