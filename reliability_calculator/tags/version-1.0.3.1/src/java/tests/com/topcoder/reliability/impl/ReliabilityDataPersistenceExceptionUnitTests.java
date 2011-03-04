/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import org.junit.Test;

import com.topcoder.reliability.ReliabilityCalculationException;
import com.topcoder.util.errorhandling.ExceptionData;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * Unit tests for {@link ReliabilityDataPersistenceException} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReliabilityDataPersistenceExceptionUnitTests {
    /**
     * <p>
     * Represents a detail message.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    private static final Throwable CAUSE = new Exception("UnitTests");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    /**
     * <p>
     * Initializes the exception data.
     * </p>
     */
    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReliabilityDataPersistenceExceptionUnitTests.class);
    }

    /**
     * <p>
     * <code>ReliabilityDataPersistenceException</code> should be subclass of
     * <code>ReliabilityCalculationException</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("ReliabilityDataPersistenceException should be subclass of ReliabilityCalculationException.",
            ReliabilityDataPersistenceException.class.getSuperclass() == ReliabilityCalculationException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>ReliabilityDataPersistenceException(String)</code> constructor.<br>
     * The detail error message should be properly set.
     * </p>
     */
    @Test
    public void testCtor1() {
        ReliabilityDataPersistenceException exception = new ReliabilityDataPersistenceException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ReliabilityDataPersistenceException(String, Throwable)</code> constructor.<br>
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    @Test
    public void testCtor2() {
        ReliabilityDataPersistenceException exception = new ReliabilityDataPersistenceException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ReliabilityDataPersistenceException(String, ExceptionData)</code> constructor.<br>
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    @Test
    public void testCtor3() {
        ReliabilityDataPersistenceException exception =
            new ReliabilityDataPersistenceException(DETAIL_MESSAGE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>ReliabilityDataPersistenceException(String, Throwable, ExceptionData)</code>
     * constructor.<br>
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    @Test
    public void testCtor4() {
        ReliabilityDataPersistenceException exception =
            new ReliabilityDataPersistenceException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
