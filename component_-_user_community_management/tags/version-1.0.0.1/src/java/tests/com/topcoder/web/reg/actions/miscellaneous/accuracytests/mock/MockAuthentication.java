/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock;

import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;

/**
 * Mock BasicAuthentication class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuthentication extends BasicAuthentication {
    /**
     * Represents the user id.
     */
    private static long userId = 5;

    /**
     * Sets the user id.
     *
     * @param userId
     *            the user id.
     */
    public static void setUserId(long userId) {
        MockAuthentication.userId = userId;
    }

    /**
     * <p>
     * Mock User class.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    @SuppressWarnings("serial")
    public class MockUser implements User {
        /**
         * Returns the user id.
         *
         * @return the user id.
         */
        public long getId() {
            return userId;
        }

        /**
         * Returns null.
         *
         * @return null
         */
        public String getPassword() {
            return null;
        }

        /**
         * Returns "TCSDEVELOPER".
         *
         * @return "TCSDEVELOPER"
         */
        public String getUserName() {
            return "TCSDEVELOPER";
        }

        /**
         * Returns false.
         *
         * @return false
         */
        public boolean isAnonymous() {
            return false;
        }
    }

    /**
     * Returns the active user.
     *
     * @return the active user.
     */
    @Override
    public User getActiveUser() {
        return new MockUser();
    }

}
