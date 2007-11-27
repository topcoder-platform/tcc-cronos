/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.UserStatusManagerImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link UserStatusManagerImpl}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusManagerImplFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for
     * <code>{@link UserStatusManagerImpl#UserStatusManagerImpl(com.topcoder.timetracker.user.UserStatusDAO)}</code>
     * method.
     * </p>
     */
    public void testUserStatusManagerImpl_NullUserStatusDAO() {
        try {
            new UserStatusManagerImpl(null);
            fail("Expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // all business method are delegate to corresponding DAO method.
}
