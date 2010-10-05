/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.UserNotAuthorizedFault;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for UserNotAuthorizedException class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserNotAuthorizedExceptionTest extends TestCase {
    /**
     * Sample of message for exception.
     */
    private static final String SOME_MESSAGE = "yes, this is sample message";

    /**
     * Exception to test.
     */
    private UserNotAuthorizedException target;

    /**
     * Aggregates all tests.
     *
     * @return test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(UserNotAuthorizedExceptionTest.class);
    }

    /**
     * Tests constructor 1.
     */
    public void testConstructor1() {
        UserNotAuthorizedFault fault = new UserNotAuthorizedFault();
        fault.setUserIdNotAuthorized(10);
        target = new UserNotAuthorizedException(SOME_MESSAGE, fault);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("id", 10, target.getUserIdNotAuthorized());
        assertSame("fault info", fault, target.getFaultInfo());
    }

    /**
     * Tests constructor 1 for null fault.
     */
    public void testConstructor1ForNullFault() {
        target = new UserNotAuthorizedException(SOME_MESSAGE, null);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("id", -1, target.getUserIdNotAuthorized());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Test constructor 2.
     */
    public void testConstructor2() {
        target = new UserNotAuthorizedException(SOME_MESSAGE, 3);
        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("id", 3, target.getUserIdNotAuthorized());
        assertNull("fault info", target.getFaultInfo());
    }
}
