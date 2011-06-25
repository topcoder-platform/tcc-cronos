/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test case of {@link MemberPhotoActionConfigurationException}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class MemberPhotoActionConfigurationExceptionTest extends TestCase {
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
        TestSuite suite = new TestSuite(MemberPhotoActionConfigurationExceptionTest.class);
        return suite;
    }

    /**
     * <p>
     * Test method for
     * {@link MemberPhotoActionConfigurationException#MemberPhotoActionConfigurationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testMemberPhotoActionConfigurationExceptionString() {
        MemberPhotoActionConfigurationException exception = new MemberPhotoActionConfigurationException(
                MESSAGE);
        assertNotNull("Unable to instantiate MemberPhotoActionConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link MemberPhotoActionConfigurationException#MemberPhotoActionConfigurationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testMemberPhotoActionConfigurationExceptionStringThrowable() {
        MemberPhotoActionConfigurationException exception = new MemberPhotoActionConfigurationException(
                MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate MemberPhotoActionConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link MemberPhotoActionConfigurationException#MemberPhotoActionConfigurationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testMemberPhotoActionConfigurationExceptionStringExceptionData() {
        MemberPhotoActionConfigurationException exception = new MemberPhotoActionConfigurationException(
                MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate MemberPhotoActionConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link MemberPhotoActionConfigurationException#MemberPhotoActionConfigurationException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testMemberPhotoActionConfigurationExceptionStringCauseExceptionData() {
        MemberPhotoActionConfigurationException exception = new MemberPhotoActionConfigurationException(
                MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate MemberPhotoActionConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
