/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test for <code>{@link SubmissionManagementConfigurationException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionManagementConfigurationExceptionUnitTests extends TestCase {

    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SubmissionManagementConfigurationExceptionUnitTests.class);
    }

    /**
     * Tests accuracy of <code>SubmissionManagementConfigurationException(String)</code> constructor. The detail error
     * message should be correct.
     */
    public void testSubmissionManagementConfigurationExceptionStringAccuracy() {
        // Construct SubmissionManagementConfigurationException with a detail message
        SubmissionManagementConfigurationException exception = new SubmissionManagementConfigurationException(
                DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>SubmissionManagementConfigurationException(String, ExceptionData)</code> constructor.
     * The detail error message and the exception data should be correct.
     */
    public void testSubmissionManagementConfigurationExceptionStringExceptionDataAccuracy() {
        // Construct SubmissionManagementConfigurationException with a detail message and a cause
        SubmissionManagementConfigurationException exception = new SubmissionManagementConfigurationException(
                DETAIL_MESSAGE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * Tests accuracy of <code>SubmissionManagementConfigurationException(String, Throwable)</code> constructor. The
     * detail error message and the original cause of error should be correct.
     */
    public void testSubmissionManagementConfigurationExceptionStringThrowableAccuracy() {
        // Construct SubmissionManagementConfigurationException with a detail message and a cause
        SubmissionManagementConfigurationException exception = new SubmissionManagementConfigurationException(
                DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * Tests accuracy of <code>SubmissionManagementConfigurationException(String, Throwable, ExceptionData)</code>
     * constructor. The detail error message, the cause exception and the exception data should be correct.
     */
    public void testSubmissionManagementConfigurationExceptionStringThrowableExceptionDataAccuracy() {
        // Construct SubmissionManagementConfigurationException with a detail message and a cause
        SubmissionManagementConfigurationException exception = new SubmissionManagementConfigurationException(
                DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
