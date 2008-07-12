/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * <p>
 * Unit tests for {@link DigitalRunContestManagerException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunContestManagerExceptionUnitTests extends BaseTestCase {


    /**
     * <p>
     * <code>DigitalRunContestManagerException</code> should be subclass of
     * <code>BaseCriticalException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "DigitalRunContestManagerException should be subclass of BaseCriticalException",
            DigitalRunContestManagerException.class.getSuperclass() == BaseCriticalException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunContestManagerException(String)</code> constructor.
     * The detail error message should be properly set.
     * </p>
     */
    public void testCtor1() {
        // Construct DigitalRunContestManagerException with an error message
        DigitalRunContestManagerException exception =
            new DigitalRunContestManagerException(ERROR_MESSAGE);

        // Verify that there is an error message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", ERROR_MESSAGE, exception.getMessage());

    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunContestManagerException(String, Throwable)</code> constructor.
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    public void testCtor2() {
        // Construct DigitalRunContestManagerException with an error message and a cause
        DigitalRunContestManagerException exception =
            new DigitalRunContestManagerException(ERROR_MESSAGE, CAUSE);

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
     * Tests accuracy of <code>DigitalRunContestManagerException(String, ExceptionData)
     * </code> constructor.
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    public void testCtor3() {
        // Construct DigitalRunContestManagerException with an error message and a cause
        DigitalRunContestManagerException exception =
            new DigitalRunContestManagerException(ERROR_MESSAGE, EXCEPTION_DATA);

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
     * Tests accuracy of <code>DigitalRunContestManagerException(String, Throwable, ExceptionData)
     * </code> constructor.
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    public void testCtor4() {
        // Construct DigitalRunContestManagerException with an error message and a cause
        DigitalRunContestManagerException exception =
            new DigitalRunContestManagerException(ERROR_MESSAGE, CAUSE, EXCEPTION_DATA);

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
