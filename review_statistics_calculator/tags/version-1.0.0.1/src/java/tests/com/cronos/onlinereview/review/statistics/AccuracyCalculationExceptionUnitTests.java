/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>AccuracyCalculationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyCalculationExceptionUnitTests extends TestCase {
    /**
     * The error message used for testing.
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * The application code used in the ExceptionData.
     */
    private static final String APP_CODE = "AccuracyCalculationExceptionUnitTests";

    /**
     * An exception instance used to create the AccuracyCalculationException.
     */
    private final Exception cause = new Exception("Exception");

    /**
     * The Exception data used as fixture.
     */
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
     * Tests constructor1: AccuracyCalculationException(String), with correct message. The message can be retrieved
     * correctly later.
     */
    public void test_Ctor1() {
        AccuracyCalculationException ex = new AccuracyCalculationException(ERROR_MESSAGE);
        assertTrue("Unable to instantiate AccuracyCalculationException.", ex instanceof StatisticsCalculatorException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("Exception data is incorrect", ex.getApplicationCode());
    }

    /**
     * Tests constructor1: AccuracyCalculationException(String), with null message. No exception is expected.
     */
    public void test_Ctor1_WithNullMessage() {
        // No exception
        new AccuracyCalculationException((String) null);
    }

    /**
     * Tests constructor1: AccuracyCalculationException(String), with empty message. No exception is expected.
     */
    public void test_Ctor1_WithEmptyMessage() {
        // No exception
        new AccuracyCalculationException("");
    }

    /**
     * Tests constructor1: AccuracyCalculationException(String), with trimmed empty message. No exception is expected.
     */
    public void test_Ctor1_WithTrimmedEmptyMessage() {
        // No exception
        new AccuracyCalculationException("    ");
    }

    /**
     * Tests constructor2: AccuracyCalculationException(String, Throwable), with correct cause. The cause can be
     * retrieved correctly later.
     */
    public void test_Ctor2() {
        AccuracyCalculationException ex = new AccuracyCalculationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate WidgetServiceHandlingException.", ex);
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
    }

    /**
     * Tests constructor2: AccuracyCalculationException(Throwable), with null inner exception. No exception is
     * expected.
     */
    public void test_Ctor2_WithNullCause() {
        // No exception
        new AccuracyCalculationException(ERROR_MESSAGE, (Throwable) null);
    }

    /**
     * Tests constructor3: AccuracyCalculationException(String, ExceptionData), with correct error message and data.
     * The message and data can be retrieved correctly later.
     */
    public void test_Ctor3() {
        AccuracyCalculationException ex = new AccuracyCalculationException(ERROR_MESSAGE, data);

        assertNotNull("Unable to instantiate AccuracyCalculationException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("The inner exception should match.", ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
    }

    /**
     * Tests constructor3: AccuracyCalculationException(String, ExceptionData), with empty message. No exception is
     * expected.
     */
    public void test_Ctor3_WithEmptyMessage() {
        // No exception
        new AccuracyCalculationException("", data);
    }

    /**
     * Tests constructor3: AccuracyCalculationException(String, ExceptionData), with trimmed empty message. No
     * exception is expected.
     */
    public void test_Ctor3_WithTrimmedEmptyMessage() {
        // No exception
        new AccuracyCalculationException("  ", data);
    }

    /**
     * Tests constructor3: AccuracyCalculationException(String, ExceptionData), with null message. No exception is
     * expected.
     */
    public void test_Ctor3_WithNullMessage() {
        // No exception
        new AccuracyCalculationException((String) null, data);
    }

    /**
     * Tests constructor3: AccuracyCalculationException(String, ExceptionData), with null data. No exception is
     * expected.
     */
    public void test_Ctor3_WithNullExceptionData() {
        // No exception
        new AccuracyCalculationException(ERROR_MESSAGE, (ExceptionData) null);
    }

    /**
     * Tests constructor4: AccuracyCalculationException(String, Throwable, ExceptionData), with correct cause and
     * data. The cause and data can be retrieved correctly later.
     */
    public void test_Ctor4() {
        AccuracyCalculationException ex = new AccuracyCalculationException(ERROR_MESSAGE, cause, data);

        assertNotNull("Unable to instantiate AccuracyCalculationException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
    }

    /**
     * Tests constructor4: AccuracyCalculationException(String, Throwable, ExceptionData), with null cause. No
     * exception is expected.
     */
    public void test_Ctor4_WithNullCause() {
        // No exception
        new AccuracyCalculationException(ERROR_MESSAGE, (Throwable) null, data);
    }

    /**
     * Tests constructor4: AccuracyCalculationException(String, Throwable, ExceptionData), with null data. No
     * exception is expected.
     */
    public void test_Ctor4_WithNullExceptionData() {
        // No exception
        new AccuracyCalculationException(ERROR_MESSAGE, cause, (ExceptionData) null);
    }
}
