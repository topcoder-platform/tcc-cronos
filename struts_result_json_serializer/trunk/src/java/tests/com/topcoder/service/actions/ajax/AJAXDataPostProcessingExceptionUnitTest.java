/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>AJAXDataPostProcessingException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AJAXDataPostProcessingExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";
    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(AJAXDataPostProcessingExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>AJAXDataPostProcessingException</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        AJAXDataPostProcessingException exception = new AJAXDataPostProcessingException(MESSAGE);
        assertNotNull("Unable to create AJAXDataPostProcessingException instance.", exception);
        assertTrue("The AJAXDataPostProcessingException should be instance of Exception.",
                exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>AJAXDataPostProcessingException</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr_Null() {
        AJAXDataPostProcessingException exception = new AJAXDataPostProcessingException(null);
        assertNotNull("Unable to create AJAXDataPostProcessingException instance.", exception);
        assertTrue("The AJAXDataPostProcessingException should be instance of Exception.",
                exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>AJAXDataPostProcessingException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        AJAXDataPostProcessingException exception = new AJAXDataPostProcessingException(MESSAGE, CAUSE);
        assertNotNull("Unable to create AJAXDataPostProcessingException instance.", exception);
        assertTrue("The AJAXDataPostProcessingException should be instance of Exception.",
                exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>AJAXDataPostProcessingException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        AJAXDataPostProcessingException exception = new AJAXDataPostProcessingException(null,
                (Throwable) null);
        assertNotNull("Unable to create AJAXDataPostProcessingException instance.", exception);
        assertTrue("The AJAXDataPostProcessingException should be instance of Exception.",
                exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
