/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import com.topcoder.shared.security.LoginException;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockWebAuthentication implements WebAuthentication {

    /**
     * Mock method.
     */
    public User getUser() {
        return null;
    }

    /**
     * Mock method.
     */
    public void login(User arg0) throws LoginException {

    }

    /**
     * Mock method.
     */
    public void logout() {
        // ok
    }

    /**
     * Mock method.
     */
    public User getActiveUser() {
        return new User() {
            /**
             * Mock method.
             */
            public boolean isAnonymous() {
                return false;
            }

            /**
             * Mock method.
             */
            public String getUserName() {
                return "userName";
            }

            /**
             * Mock method.
             */
            public String getPassword() {
                return null;
            }

            /**
             * Mock method.
             */
            public long getId() {
                return 10L;
            }
        };
    }

    /**
     * Mock method.
     */
    public void login(User u, boolean rememberUser) throws LoginException {
        // ok
    }

    /**
     * Mock method.
     */
    public boolean isKnownUser() {
        return false;
    }

}
