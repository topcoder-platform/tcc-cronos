/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.PersistenceException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Accuracy test for class PersistenceException.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class PersistenceExceptionAccuracyTest extends TestCase {

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
     * Test for PersistenceException(String message).
     * </p>
     */
    public void testPersistenceExceptionString() {
        // construct PersistenceException with detail message.
        PersistenceException pe = new PersistenceException(DETAIL_MESSAGE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE, pe.getMessage());

        // Verify that the PersistenceException is subclass of BaseException
        assertTrue("PersistenceException should be subclass of BaseException",
                pe instanceof BaseException);
    }

    /**
     * <p>
     * Test for PersistenceException(String message, Throwable cause).
     * </p>
     */
    public void testPersistenceExceptionStringThrowable() {
        // construct PersistenceException with detail message.
        PersistenceException pe = new PersistenceException(DETAIL_MESSAGE, CAUSE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE + ", caused by null", pe
                .getMessage());

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, pe.getCause());
    }

    /**
     * <p>
     * Test for PersistenceException(Throwable cause).
     * </p>
     */
    public void testPersistenceExceptionThrowable() {
        // construct PersistenceException with detail message.
        PersistenceException pe = new PersistenceException(CAUSE);

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, pe.getCause());
    }
}
