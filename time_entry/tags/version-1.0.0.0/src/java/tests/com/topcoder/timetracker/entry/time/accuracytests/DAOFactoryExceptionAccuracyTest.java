/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DAOFactoryException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Accuracy test for for class DAOFactoryException.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class DAOFactoryExceptionAccuracyTest extends TestCase {

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
     * Test for DAOFactoryException(String).
     * </p>
     */
    public void testDAOFactoryExceptionString() {
        // construct DAOFactoryException with detail message.
        DAOFactoryException dae = new DAOFactoryException(DETAIL_MESSAGE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE, dae.getMessage());

        // Verify that the DAOFactoryException is subclass of BaseException
        assertTrue("DAOFactoryException should be subclass of BaseException",
                dae instanceof BaseException);
    }

    /**
     * <p>
     * Test for DAOFactoryException(String message, Throwable cause).
     * </p>
     */
    public void testDAOFactoryExceptionStringThrowable() {
        // construct DAOFactoryException with detail message.
        DAOFactoryException dae = new DAOFactoryException(DETAIL_MESSAGE, CAUSE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE + ", caused by null", dae
                .getMessage());

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, dae.getCause());
    }
}
