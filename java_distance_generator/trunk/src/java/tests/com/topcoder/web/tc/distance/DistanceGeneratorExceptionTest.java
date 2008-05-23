/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test cases for <code>DistanceGeneratorException</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class DistanceGeneratorExceptionTest extends TestCase {
    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /** The application code used in the ExceptionData. */
    private static final String APP_CODE = "DistanceGeneratorExceptionTest";

    /** An exception instance used to create the DistanceGeneratorException. */
    private final Exception cause = new Exception("Exception");

    /** The Exception data used as fixture. */
    private final ExceptionData data = new ExceptionData();

    /**
     * <p>
     * Sets up exception data.
     * </p>
     */
    protected void setUp() {
        data.setApplicationCode(APP_CODE);
    }

    /**
     * Tests constructor1: DistanceGeneratorException(String, ExceptionData), with correct error message and data.
     * The message and data can be retrieved correctly later.
     */
    public void testCtor1() {
        DistanceGeneratorException ex = new DistanceGeneratorException(ERROR_MESSAGE, data);

        assertNotNull("Unable to instantiate DistanceGeneratorException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("The inner exception should match.", ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
    }

    /**
     * Tests constructor1: DistanceGeneratorException(String, ExceptionData), with empty message. No exception is
     * expected.
     */
    public void testCtor1WithEmptyMessage() {
        // No exception
        new DistanceGeneratorException("", data);
    }

    /**
     * Tests constructor1: DistanceGeneratorException(String, ExceptionData), with trimmed empty message. No
     * exception is expected.
     */
    public void testCtor1WithTrimmedEmptyMessage() {
        // No exception
        new DistanceGeneratorException("  ", data);
    }

    /**
     * Tests constructor1: DistanceGeneratorException(String, ExceptionData), with null message. No exception is
     * expected.
     */
    public void testCtor1WithNullMessage() {
        // No exception
        new DistanceGeneratorException((String) null, data);
    }

    /**
     * Tests constructor1: DistanceGeneratorException(String, ExceptionData), with null data. No exception is
     * expected.
     */
    public void testCtor1WithNullExceptionData() {
        // No exception
        new DistanceGeneratorException(ERROR_MESSAGE, (ExceptionData) null);
    }

    /**
     * Tests constructor2: DistanceGeneratorException(String, Throwable, ExceptionData), with correct error
     * message, cause and data. The message and data can be retrieved correctly later.
     */
    public void testCtor2() {
        DistanceGeneratorException ex = new DistanceGeneratorException(ERROR_MESSAGE, cause, data);

        assertNotNull("Unable to instantiate DistanceGeneratorException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
    }

    /**
     * Tests constructor2: DistanceGeneratorException(String, Throwable, ExceptionData), with empty message. No
     * exception is expected.
     */
    public void testCtor2WithEmptyMessage() {
        // No exception
        new DistanceGeneratorException("", cause, data);
    }

    /**
     * Tests constructor2: DistanceGeneratorException(String, Throwable, ExceptionData), with trimmed empty
     * message. No exception is expected.
     */
    public void testCtor2WithTrimmedEmptyMessage() {
        // No exception
        new DistanceGeneratorException("  ", cause, data);
    }

    /**
     * Tests constructor2: DistanceGeneratorException(String, Throwable, ExceptionData), with null message. No
     * exception is expected.
     */
    public void testCtor2WithNullMessage() {
        // No exception
        new DistanceGeneratorException((String) null, cause, data);
    }

    /**
     * Tests constructor2: DistanceGeneratorException(Throwable, Throwable, ExceptionData), with null cause. No
     * exception is expected.
     */
    public void testCtor2WithNullCause() {
        // No exception
        new DistanceGeneratorException(ERROR_MESSAGE, (Throwable) null, data);
    }

    /**
     * Tests constructor2: DistanceGeneratorException(String, Throwable, ExceptionData), with null data. No
     * exception is expected.
     */
    public void testCtor2WithNullExceptionData() {
        // No exception
        new DistanceGeneratorException(ERROR_MESSAGE, cause, (ExceptionData) null);
    }
}