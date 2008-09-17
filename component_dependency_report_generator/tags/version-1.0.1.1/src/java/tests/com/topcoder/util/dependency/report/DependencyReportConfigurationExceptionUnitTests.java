/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;


/**
 * <p>
 * Unit tests for <code>DependencyReportConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependencyReportConfigurationExceptionUnitTests extends BaseTestCase {

    /**
     * <p>
     * <code>DependencyReportConfigurationException</code> should be subclass of
     * <code>DependencyReportGenerationException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "DependencyReportConfigurationException should be subclass of DependencyReportGenerationException",
            DependencyReportConfigurationException.class.getSuperclass() == DependencyReportGenerationException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>DependencyReportConfigurationException(String)</code> constructor.
     * The detail error message should be properly set.
     * </p>
     */
    public void testCtor1() {
        // Construct DependencyReportConfigurationException with a detail message
        DependencyReportConfigurationException exception =
            new DependencyReportConfigurationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>DependencyReportConfigurationException(String, Throwable)</code> constructor.
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    public void testCtor2() {
        // Construct DependencyReportConfigurationException with a detail message and a cause
        DependencyReportConfigurationException exception =
            new DependencyReportConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>DependencyReportConfigurationException(String, ExceptionData)
     * </code> constructor.
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    public void testCtor3() {
        // Construct DependencyReportConfigurationException with a detail message and a cause
        DependencyReportConfigurationException exception =
            new DependencyReportConfigurationException(DETAIL_MESSAGE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>DependencyReportConfigurationException(String, Throwable, ExceptionData)
     * </code> constructor.
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    public void testCtor4() {
        // Construct DependencyReportConfigurationException with a detail message and a cause
        DependencyReportConfigurationException exception =
            new DependencyReportConfigurationException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
