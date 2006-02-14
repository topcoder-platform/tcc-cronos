/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DAOActionException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Accuracy test for for class DAOActionException.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class DAOActionExceptionAccuracyTest extends TestCase {

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
     * Test for DAOActionException(String).
     * </p>
     */
    public void testDAOActionExceptionString() {
        // construct DAOActionException with detail message.
        DAOActionException dae = new DAOActionException(DETAIL_MESSAGE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE, dae.getMessage());

        // Verify that the DAOActionException is subclass of BaseException
        assertTrue("DAOActionException should be subclass of BaseException",
                dae instanceof BaseException);
    }

    /**
     * <p>
     * Test for DAOActionException(String message, Throwable cause).
     * </p>
     */
    public void testDAOActionExceptionStringThrowable() {
        // construct DAOActionException with detail message.
        DAOActionException dae = new DAOActionException(DETAIL_MESSAGE, CAUSE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE + ", caused by null", dae
                .getMessage());

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, dae.getCause());
    }

}
