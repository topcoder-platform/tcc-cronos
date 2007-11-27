/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.UserTypeManagerImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link UserTypeManagerImpl}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeManagerImplFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for
     * <code>{@link UserTypeManagerImpl#UserTypeManagerImpl(com.topcoder.timetracker.user.UserTypeDAO)}</code> method.
     * </p>
     */
    public void testUserTypeManagerImpl_NullUserStatusDAO() {
        try {
            new UserTypeManagerImpl(null);
            fail("Expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // all business method are delegate to corresponding DAO method.
}
