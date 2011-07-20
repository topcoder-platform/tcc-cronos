/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test case of {@link UserCommunityManagementInitializationException}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class UserCommunityManagementInitializationExceptionTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";
    /**
     * <p>
     * The exception data used for testing.
     * </p>
     */
    private static final ExceptionData EXCEPTIONDATA = new ExceptionData();
    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(UserCommunityManagementInitializationExceptionTest.class);
        return suite;
    }

    /**
     * <p>
     * Test method for
     * {@link UserCommunityManagementInitializationException#UserCommunityManagementInitializationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testUserCommunityManagementInitializationExceptionString() {
        UserCommunityManagementInitializationException exception = new UserCommunityManagementInitializationException(
                MESSAGE);
        assertNotNull("Unable to instantiate UserCommunityManagementInitializationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link UserCommunityManagementInitializationException#UserCommunityManagementInitializationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testUserCommunityManagementInitializationExceptionStringThrowable() {
        UserCommunityManagementInitializationException exception = new UserCommunityManagementInitializationException(
                MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate UserCommunityManagementInitializationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link UserCommunityManagementInitializationException#UserCommunityManagementInitializationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testUserCommunityManagementInitializationExceptionStringExceptionData() {
        UserCommunityManagementInitializationException exception = new UserCommunityManagementInitializationException(
                MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate UserCommunityManagementInitializationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link UserCommunityManagementInitializationException#UserCommunityManagementInitializationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testUserCommunityManagementInitializationExceptionStringCauseExceptionData() {
        UserCommunityManagementInitializationException exception = new UserCommunityManagementInitializationException(
                MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate UserCommunityManagementInitializationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
