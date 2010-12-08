/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class ReviewBoardApplicationListenerException. All the constructors are tested.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class ReviewBoardApplicationListenerExceptionUnitTests extends TestCase {
    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * An exception instance used to create the ReviewBoardApplicationListenerException.
     */
    private final Exception cause = new Exception();

    /**
     * An exception data used to create the ReviewBoardApplicationListenerException.
     */
    private final ExceptionData data = new ExceptionData();

    /**
     * Tests constructor1: ReviewBoardApplicationListenerException(String), with correct message. The message can
     * be retrieved correctly later.
     */
    public void testCtor1() {
        ReviewBoardApplicationListenerException e = new ReviewBoardApplicationListenerException(
            ERROR_MESSAGE);
        assertNotNull("Unable to instantiate ReviewBoardApplicationListenerException.", e);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, e.getMessage());
    }

    /**
     * Tests constructor1: ReviewBoardApplicationListenerException(String), with null message. No exception is
     * expected.
     */
    public void testCtor1WithNullMessage() {
        // NO exception
        new ReviewBoardApplicationListenerException(null);
    }

    /**
     * Tests constructor1: ReviewBoardApplicationListenerException(String), with empty message. No exception is
     * expected.
     */
    public void testCtor1WithEmptyMessage() {
        // NO exception
        new ReviewBoardApplicationListenerException("");
    }

    /**
     * Tests constructor1: ReviewBoardApplicationListenerException(String), with trimmed empty message. No
     * exception is expected.
     */
    public void testCtor1WithTrimmedEmptyMessage() {
        // NO exception
        new ReviewBoardApplicationListenerException("  ");
    }

    /**
     * Tests constructor2: ReviewBoardApplicationListenerException(String, Throwable), with correct error message
     * and cause. The message and cause can be retrieved correctly later.
     */
    public void testCtor2() {
        ReviewBoardApplicationListenerException ace = new ReviewBoardApplicationListenerException(
            ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate ReviewBoardApplicationListenerException.", ace);
        assertTrue("Error message is not properly propagated to super class.", ace.getMessage().indexOf(
            ERROR_MESSAGE) >= 0);
        assertEquals("The inner exception should match.", cause, ace.getCause());
    }

    /**
     * Tests constructor2: ReviewBoardApplicationListenerException(String, Throwable), with empty message. No
     * exception is expected.
     */
    public void testCtor2WithEmptyMessage() {
        // No exception
        new ReviewBoardApplicationListenerException("", cause);
    }

    /**
     * Tests constructor2: ReviewBoardApplicationListenerException(String, Throwable), with trimmed empty message.
     * No exception is expected.
     */
    public void testCtor2WithTrimmedEmptyMessage() {
        // No exception
        new ReviewBoardApplicationListenerException("  ", cause);
    }

    /**
     * Tests constructor2: ReviewBoardApplicationListenerException(String, Throwable), with null message. No
     * exception is expected.
     */
    public void testCtor2WithNullMessage() {
        // No exception
        new ReviewBoardApplicationListenerException(null, cause);
    }

    /**
     * Tests constructor3: ReviewBoardApplicationListenerException(String, ExceptionData), with correct error
     * message and data. The message can be retrieved correctly later.
     */
    public void testCtor3() {
        ReviewBoardApplicationListenerException ace = new ReviewBoardApplicationListenerException(
            ERROR_MESSAGE, data);

        assertNotNull("Unable to instantiate ReviewBoardApplicationListenerException.", ace);
        assertTrue("Error message is not properly propagated to super class.", ace.getMessage().indexOf(
            ERROR_MESSAGE) >= 0);
    }

    /**
     * Tests constructor3: ReviewBoardApplicationListenerException(String, ExceptionData), with empty message. No
     * exception is expected.
     */
    public void testCtor3WithEmptyMessage() {
        // No exception
        new ReviewBoardApplicationListenerException("", data);
    }

    /**
     * Tests constructor3: ReviewBoardApplicationListenerException(String, ExceptionData), with trimmed empty
     * message. No exception is expected.
     */
    public void testCtor3WithTrimmedEmptyMessage() {
        // No exception
        new ReviewBoardApplicationListenerException("  ", data);
    }

    /**
     * Tests constructor3: ReviewBoardApplicationListenerException(String, ExceptionData), with null message. No
     * exception is expected.
     */
    public void testCtor3WithNullMessage() {
        // No exception
        new ReviewBoardApplicationListenerException(null, data);
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with correct
     * error message, cause and data. The message and cause can be retrieved correctly later.
     */
    public void testCtor4() {
        ReviewBoardApplicationListenerException ace = new ReviewBoardApplicationListenerException(
            ERROR_MESSAGE, cause, data);

        assertNotNull("Unable to instantiate ReviewBoardApplicationListenerException.", ace);
        assertTrue("Error message is not properly propagated to super class.", ace.getMessage().indexOf(
            ERROR_MESSAGE) >= 0);
        assertEquals("The inner exception should match.", cause, ace.getCause());
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with empty
     * message. No exception is expected.
     */
    public void testCtor4WithEmptyMessage() {
        // No exception
        new ReviewBoardApplicationListenerException("", cause, data);
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with trimmed
     * empty message. No exception is expected.
     */
    public void testCtor4WithTrimmedEmptyMessage() {
        // No exception
        new ReviewBoardApplicationListenerException("  ", cause, data);
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with null
     * message. No exception is expected.
     */
    public void testCtor4WithNullMessage() {
        // No exception
        new ReviewBoardApplicationListenerException(null, cause, data);
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with null
     * cause. No exception is expected.
     */
    public void testCtor4WithNullCause() {
        // No exception
        new ReviewBoardApplicationListenerException(ERROR_MESSAGE, null, data);
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with null
     * data. No exception is expected.
     */
    public void testCtor4WithNullData() {
        // No exception
        new ReviewBoardApplicationListenerException(ERROR_MESSAGE, cause, null);
    }

    /**
     * Tests constructor4: ReviewBoardApplicationListenerException(String, Throwable, ExceptionData), with null
     * cause and null data. No exception is expected.
     */
    public void testCtor4WithNullCauseAndNullData() {
        // No exception
        new ReviewBoardApplicationListenerException(ERROR_MESSAGE, null, null);
    }
}
