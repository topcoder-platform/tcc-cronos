/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.UnknownUserStoreException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Accuracy test for class UnknownUserStoreException.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class UnknownUserStoreExceptionAccuracyTest extends TestCase {

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
     * Test for UnknownUserStoreException(String message).
     * </p>
     */
    public void testUnknownUserStoreExceptionString() {
        // construct UnknownUserStoreException with detail message.
        UnknownUserStoreException uuse = new UnknownUserStoreException(DETAIL_MESSAGE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE, uuse.getMessage());

        // Verify that the UnknownUserStoreException is subclass of BaseException
        assertTrue("UnknownUserStoreException should be subclass of BaseException",
                uuse instanceof BaseException);
    }

    /**
     * <p>
     * Test for UnknownUserStoreException(String message, Throwable cause).
     * </p>
     */
    public void testUnknownUserStoreExceptionStringThrowable() {
        // construct UnknownUserStoreException with detail message.
        UnknownUserStoreException uuse = new UnknownUserStoreException(DETAIL_MESSAGE, CAUSE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE + ", caused by null",
                uuse.getMessage());

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, uuse.getCause());
    }

    /**
     * <p>
     * Test for UnknownUserStoreException(Throwable cause).
     * </p>
     */
    public void testUnknownUserStoreExceptionThrowable() {
        // construct UnknownUserStoreException with detail message.
        UnknownUserStoreException uuse = new UnknownUserStoreException(CAUSE);

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, uuse.getCause());
    }
}
