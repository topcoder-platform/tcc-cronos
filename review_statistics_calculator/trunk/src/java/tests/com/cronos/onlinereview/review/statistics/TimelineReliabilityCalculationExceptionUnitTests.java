/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>TimelineReliabilityCalculationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TimelineReliabilityCalculationExceptionUnitTests extends TestCase {
    /**
     * The error message used for testing.
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * The application code used in the ExceptionData.
     */
    private static final String APP_CODE = "TimelineReliabilityCalculationExceptionUnitTests";

    /**
     * An exception instance used to create the TimelineReliabilityCalculationException.
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
     * Tests constructor1: TimelineReliabilityCalculationException(String), with correct message. The message can be
     * retrieved correctly later.
     */
    public void test_Ctor1() {
        TimelineReliabilityCalculationException ex = new TimelineReliabilityCalculationException(ERROR_MESSAGE);
        assertTrue("Unable to instantiate TimelineReliabilityCalculationException.",
            ex instanceof StatisticsCalculatorException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("Exception data is incorrect", ex.getApplicationCode());
    }

    /**
     * Tests constructor1: TimelineReliabilityCalculationException(String), with null message. No exception is
     * expected.
     */
    public void test_Ctor1_WithNullMessage() {
        // No exception
        new TimelineReliabilityCalculationException((String) null);
    }

    /**
     * Tests constructor1: TimelineReliabilityCalculationException(String), with empty message. No exception is
     * expected.
     */
    public void test_Ctor1_WithEmptyMessage() {
        // No exception
        new TimelineReliabilityCalculationException("");
    }

    /**
     * Tests constructor1: TimelineReliabilityCalculationException(String), with trimmed empty message. No exception
     * is expected.
     */
    public void test_Ctor1_WithTrimmedEmptyMessage() {
        // No exception
        new TimelineReliabilityCalculationException("    ");
    }

    /**
     * Tests constructor2: TimelineReliabilityCalculationException(String, Throwable), with correct cause. The cause
     * can be retrieved correctly later.
     */
    public void test_Ctor2() {
        TimelineReliabilityCalculationException ex = new TimelineReliabilityCalculationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate WidgetServiceHandlingException.", ex);
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
    }

    /**
     * Tests constructor2: TimelineReliabilityCalculationException(Throwable), with null inner exception. No exception
     * is expected.
     */
    public void test_Ctor2_WithNullCause() {
        // No exception
        new TimelineReliabilityCalculationException(ERROR_MESSAGE, (Throwable) null);
    }

    /**
     * Tests constructor3: TimelineReliabilityCalculationException(String, ExceptionData), with correct error message
     * and data. The message and data can be retrieved correctly later.
     */
    public void test_Ctor3() {
        TimelineReliabilityCalculationException ex = new TimelineReliabilityCalculationException(ERROR_MESSAGE, data);

        assertNotNull("Unable to instantiate TimelineReliabilityCalculationException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("The inner exception should match.", ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
    }

    /**
     * Tests constructor3: TimelineReliabilityCalculationException(String, ExceptionData), with empty message. No
     * exception is expected.
     */
    public void test_Ctor3_WithEmptyMessage() {
        // No exception
        new TimelineReliabilityCalculationException("", data);
    }

    /**
     * Tests constructor3: TimelineReliabilityCalculationException(String, ExceptionData), with trimmed empty message.
     * No exception is expected.
     */
    public void test_Ctor3_WithTrimmedEmptyMessage() {
        // No exception
        new TimelineReliabilityCalculationException("  ", data);
    }

    /**
     * Tests constructor3: TimelineReliabilityCalculationException(String, ExceptionData), with null message. No
     * exception is expected.
     */
    public void test_Ctor3_WithNullMessage() {
        // No exception
        new TimelineReliabilityCalculationException((String) null, data);
    }

    /**
     * Tests constructor3: TimelineReliabilityCalculationException(String, ExceptionData), with null data. No
     * exception is expected.
     */
    public void test_Ctor3_WithNullExceptionData() {
        // No exception
        new TimelineReliabilityCalculationException(ERROR_MESSAGE, (ExceptionData) null);
    }

    /**
     * Tests constructor4: TimelineReliabilityCalculationException(String, Throwable, ExceptionData), with correct
     * cause and data. The cause and data can be retrieved correctly later.
     */
    public void test_Ctor4() {
        TimelineReliabilityCalculationException ex = new TimelineReliabilityCalculationException(ERROR_MESSAGE,
            cause, data);

        assertNotNull("Unable to instantiate TimelineReliabilityCalculationException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
    }

    /**
     * Tests constructor4: TimelineReliabilityCalculationException(String, Throwable, ExceptionData), with null cause.
     * No exception is expected.
     */
    public void test_Ctor4_WithNullCause() {
        // No exception
        new TimelineReliabilityCalculationException(ERROR_MESSAGE, (Throwable) null, data);
    }

    /**
     * Tests constructor4: TimelineReliabilityCalculationException(String, Throwable, ExceptionData), with null data.
     * No exception is expected.
     */
    public void test_Ctor4_WithNullExceptionData() {
        // No exception
        new TimelineReliabilityCalculationException(ERROR_MESSAGE, cause, (ExceptionData) null);
    }
}
