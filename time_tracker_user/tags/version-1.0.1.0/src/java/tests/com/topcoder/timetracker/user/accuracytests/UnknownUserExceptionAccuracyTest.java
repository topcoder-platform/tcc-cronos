/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.UnknownUserException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Accuracy test for class UnknownUserException.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class UnknownUserExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * A string with detail message used for test.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * A throwable cause used for test.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Test for UnknownUserException(String message).
     * </p>
     */
    public void testUnknownUserExceptionString() {
        // construct UnknownUserException with detail message.
        UnknownUserException uue = new UnknownUserException(DETAIL_MESSAGE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE, uue.getMessage());

        // Verify that the UnknownUserException is subclass of BaseException
        assertTrue("UnknownUserException should be subclass of BaseException",
                uue instanceof BaseException);
    }

    /**
     * <p>
     * Test for UnknownUserException(String message, Throwable cause).
     * </p>
     */
    public void testUnknownUserExceptionStringThrowable() {
        // construct UnknownUserException with detail message.
        UnknownUserException uue = new UnknownUserException(DETAIL_MESSAGE, CAUSE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE + ", caused by null", uue
                .getMessage());

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, uue.getCause());
    }

    /**
     * <p>
     * Test for UnknownUserException(Throwable cause).
     * </p>
     */
    public void testUnknownUserExceptionThrowable() {
        // construct UnknownUserException with detail message.
        UnknownUserException uue = new UnknownUserException(CAUSE);

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, uue.getCause());
    }
}
