/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import com.topcoder.service.prerequisite.ejb.UserNotAuthorizedFault;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link UserNotAuthorizedException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserNotAuthorizedExceptionUnitTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents the fault info.
     * </p>
     */
    private static final UserNotAuthorizedFault FAULT_INFO = new UserNotAuthorizedFault();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UserNotAuthorizedExceptionUnitTests.class);
    }

    /**
     * <p>
     * Unit test <code>UserNotAuthorizedException(String, UserNotAuthorizedFault)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the fault info should be correct.
     * </p>
     */
    public void testUserNotAuthorizedException_accuracy1() {
        // Construct UserNotAuthorizedException with a detail message and fault info
        UserNotAuthorizedException exception = new UserNotAuthorizedException(DETAIL_MESSAGE, FAULT_INFO);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertEquals("Incorrect user id.", -1, exception.getUserIdNotAuthorized());
    }

    /**
     * <p>
     * Unit test <code>UserNotAuthorizedException(String, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the user id should be correct.
     * </p>
     */
    public void testUserNotAuthorizedException_accuracy2() {
        // Construct UserNotAuthorizedException with a detail message
        UserNotAuthorizedException exception = new UserNotAuthorizedException(DETAIL_MESSAGE, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertEquals("Incorrect user id.", 1, exception.getUserIdNotAuthorized());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test for <code>UserNotAuthorizedException#getUserIdNotAuthorized()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserIdNotAuthorized_accuracy() throws Exception {
        // Construct UserNotAuthorizedException with a detail message and fault info
        UserNotAuthorizedException exception = new UserNotAuthorizedException(DETAIL_MESSAGE, FAULT_INFO);
        assertEquals("Incorrect user id.", -1, exception.getUserIdNotAuthorized());

        UnitTestHelper.setFieldValue(exception, "userIdNotAuthorized", 1L);
        assertEquals("Incorrect user id.", 1, exception.getUserIdNotAuthorized());
    }

    /**
     * <p>
     * Unit test for <code>UserNotAuthorizedException#getFaultInfo()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFaultInfo_accuracy() throws Exception {
        // Construct UserNotAuthorizedException with a detail message
        UserNotAuthorizedException exception = new UserNotAuthorizedException(DETAIL_MESSAGE, 1);
        assertNull("The fault info should be null.", exception.getFaultInfo());

        UnitTestHelper.setFieldValue(exception, "faultInfo", FAULT_INFO);
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
    }
}
