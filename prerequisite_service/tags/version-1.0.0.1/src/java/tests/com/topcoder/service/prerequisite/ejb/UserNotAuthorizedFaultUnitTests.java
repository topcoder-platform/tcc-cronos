/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link UserNotAuthorizedFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserNotAuthorizedFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>UserNotAuthorizedFault</code> instance used in tests.
     * </p>
     */
    private UserNotAuthorizedFault userNotAuthorizedFault;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UserNotAuthorizedFaultUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        userNotAuthorizedFault = new UserNotAuthorizedFault();
    }

    /**
     * <p>
     * Unit test for <code>UserNotAuthorizedFault#UserNotAuthorizedFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testUserNotAuthorizedFault_accuracy() {
        assertNotNull("Instance should be always created.", userNotAuthorizedFault);
    }

    /**
     * <p>
     * Unit test for <code>UserNotAuthorizedFault#getUserIdNotAuthorized()</code> method.
     * </p>
     * <p>
     * It should return 0, if not set.
     * </p>
     */
    public void testGetUserIdNotAuthorized_default() {
        assertEquals("Should return 0.", 0, userNotAuthorizedFault.getUserIdNotAuthorized());
    }

    /**
     * <p>
     * Unit test for <code>UserNotAuthorizedFault#setUserIdNotAuthorized(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetUserIdNotAuthorized_accuracy() {
        userNotAuthorizedFault.setUserIdNotAuthorized(-1);
        assertEquals("Incorrect user id.", -1, userNotAuthorizedFault.getUserIdNotAuthorized());

        userNotAuthorizedFault.setUserIdNotAuthorized(0);
        assertEquals("Incorrect user id.", 0, userNotAuthorizedFault.getUserIdNotAuthorized());

        userNotAuthorizedFault.setUserIdNotAuthorized(1);
        assertEquals("Incorrect user id.", 1, userNotAuthorizedFault.getUserIdNotAuthorized());
    }
}
