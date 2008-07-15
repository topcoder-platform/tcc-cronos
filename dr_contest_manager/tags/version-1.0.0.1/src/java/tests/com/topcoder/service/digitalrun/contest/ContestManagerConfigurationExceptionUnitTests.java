/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * Unit tests for {@link ContestManagerConfigurationException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerConfigurationExceptionUnitTests extends BaseTestCase {


    /**
     * <p>
     * <code>ContestManagerConfigurationException</code> should be subclass of
     * <code>BaseRuntimeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "ContestManagerConfigurationException should be subclass of BaseRuntimeException",
            ContestManagerConfigurationException.class.getSuperclass() == BaseRuntimeException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestManagerConfigurationException(String)</code> constructor.
     * The detail error message should be properly set.
     * </p>
     */
    public void testCtor1() {
        // Construct ContestManagerConfigurationException with an error message
        ContestManagerConfigurationException exception =
            new ContestManagerConfigurationException(ERROR_MESSAGE);

        // Verify that there is an error message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", ERROR_MESSAGE, exception.getMessage());

    }

    /**
     * <p>
     * Tests accuracy of <code>ContestManagerConfigurationException(String, Throwable)</code> constructor.
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    public void testCtor2() {
        // Construct ContestManagerConfigurationException with an error message and a cause
        ContestManagerConfigurationException exception =
            new ContestManagerConfigurationException(ERROR_MESSAGE, CAUSE);

        // Verify that there is an error message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            ERROR_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestManagerConfigurationException(String, ExceptionData)
     * </code> constructor.
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    public void testCtor3() {
        // Construct ContestManagerConfigurationException with an error message and a cause
        ContestManagerConfigurationException exception =
            new ContestManagerConfigurationException(ERROR_MESSAGE, EXCEPTION_DATA);

        // Verify that there is an error message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            ERROR_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestManagerConfigurationException(String, Throwable, ExceptionData)
     * </code> constructor.
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    public void testCtor4() {
        // Construct ContestManagerConfigurationException with an error message and a cause
        ContestManagerConfigurationException exception =
            new ContestManagerConfigurationException(ERROR_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is an error message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            ERROR_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
