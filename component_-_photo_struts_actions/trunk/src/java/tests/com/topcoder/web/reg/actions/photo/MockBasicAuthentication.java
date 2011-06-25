/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;
/**
 * <p>
 * A simple mock of {@link BasicAuthentication}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class MockBasicAuthentication extends BasicAuthentication {
    /**
     * <p>
     * A simple mock of {@link User}.
     * </p>
     */
    public class MockUser implements User {

        /**
        * <p>
        * The serial version uid.
        * </p>
        */
        private static final long serialVersionUID = 2321345382759191588L;

        /**
         * Returns 100.
         * @return 100
         */
        public long getId() {
            return 100;
        }

        /**
         * Returns null.
         * @return null
         */
        public String getPassword() {
            return null;
        }

        /**
         * Returns null.
         * @return null
         */
        public String getUserName() {
            return "hello-c";
        }
        /**
         * Returns false.
         * @return false
         */
        public boolean isAnonymous() {
            return false;
        }

    }

    /**
     * Returns a MockUser.
     * @return a MockUser.
     */
    @Override
    public User getActiveUser() {
        return new MockUser();
    }

}
