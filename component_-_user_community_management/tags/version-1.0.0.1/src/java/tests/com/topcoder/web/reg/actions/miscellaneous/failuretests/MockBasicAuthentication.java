/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;

import com.topcoder.web.common.security.BasicAuthentication;


/**
 * Mock class for failure tests.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockBasicAuthentication extends BasicAuthentication {
    /** The user id. */
    private long userId;

/**
         * The constructor.
         * @param userId the user id
         */
    public MockBasicAuthentication(long userId) {
        this.userId = userId;
    }

    /**
     * Return active user.
     *
     * @return DOCUMENT ME!
     */
    public User getActiveUser() {
        if (userId == 3L) {
            return null;
        }

        SimpleUser user = new SimpleUser(userId, "username", "password");

        return user;
    }
}
