/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for InvoiceUnrecognizedEntityException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InvoiceUnrecognizedEntityExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The InvoiceUnrecognizedEntityException instance for testing.
     * </p>
     */
    private InvoiceUnrecognizedEntityException entityException;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        entityException = new InvoiceUnrecognizedEntityException(8, ERROR_MESSAGE);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        entityException = null;
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(InvoiceUnrecognizedEntityExceptionAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InvoiceUnrecognizedEntityException#InvoiceUnrecognizedEntityException(long, String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created InvoiceUnrecognizedEntityException instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new InvoiceUnrecognizedEntityException instance.", this.entityException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvoiceUnrecognizedEntityException subclasses Exception.
     * </p>
     */
    public void testInvoiceUnrecognizedEntityExceptionInheritance() {
        assertTrue("InvoiceUnrecognizedEntityException does not subclass Exception.",
            entityException instanceof Exception);
    }

    /**
     * <p>
     * Tests InvoiceUnrecognizedEntityException#getId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies InvoiceUnrecognizedEntityException#getId() is correct.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get the id correctly.", 8, this.entityException.getId());
    }
}