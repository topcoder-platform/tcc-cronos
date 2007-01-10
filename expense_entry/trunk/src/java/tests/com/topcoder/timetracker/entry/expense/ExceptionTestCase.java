/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;


/**
 * <p>
 * Tests functionality of custom exceptions.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExceptionTestCase extends TestCase {
    /**
     * <p>
     * Represents a string with a detail message.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents a throwable cause.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Tests accuracy of <code>ConfigurationException(String)</code> constructor. The detail error message should be
     * correct.
     * </p>
     */
    public void testConfigurationExceptionStringAccuracy() {
        //Construct ConfigurationException with a detail message
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE);

        //Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfigurationException(String, Throwable)</code> constructor. The detail error message
     * and the original cause of error should be correct.
     * </p>
     */
    public void testConfigurationExceptionStringThrowableAccuracy() {
        //Construct ConfigurationException with a detail message and a cause
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE, CAUSE);

        //Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());

        //altered message is the result of a modification to the BaseException component.
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        //Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>InsufficientDataException(String)</code> constructor. The detail error message should
     * be correct.
     * </p>
     */
    public void testInsufficientDataExceptionStringAccuracy() {
        //Construct InsufficientDataException with a detail message
        InsufficientDataException exception = new InsufficientDataException(DETAIL_MESSAGE);

        //Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>InsufficientDataException(String, Throwable)</code> constructor. The detail error
     * message and the original cause of error should be correct.
     * </p>
     */
    public void testInsufficientDataExceptionStringThrowableAccuracy() {
        //Construct InsufficientDataException with a detail message and a cause
        InsufficientDataException exception = new InsufficientDataException(DETAIL_MESSAGE, CAUSE);

        //Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());

        //altered message is the result of a modification to the BaseException component.
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        //Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>PersistenceException(String)</code> constructor. The detail error message should be
     * correct.
     * </p>
     */
    public void testPersistenceExceptionStringAccuracy() {
        //Construct PersistenceException with a detail message
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE);

        //Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>PersistenceException(String, Throwable)</code> constructor. The detail error message
     * and the original cause of error should be correct.
     * </p>
     */
    public void testPersistenceExceptionStringThrowableAccuracy() {
        //Construct PersistenceException with a detail message and a cause
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, CAUSE);

        //Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());

        //altered message is the result of a modification to the BaseException component.
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        //Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExceptionTestCase.class);
    }
}





