/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import com.topcoder.timetracker.invoice.InvoiceConfigurationException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for InvoiceConfigurationException.
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
public class InvoiceConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the InvoiceConfigurationException.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new NullPointerException();

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(InvoiceConfigurationExceptionAccuracyTests.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testInvoiceConfigurationException1() {
        InvoiceConfigurationException exception = new InvoiceConfigurationException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate InvoiceConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testInvoiceConfigurationException2() {
        InvoiceConfigurationException exception = new InvoiceConfigurationException(ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate InvoiceConfigurationException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvoiceConfigurationException subclasses Exception.
     * </p>
     */
    public void testInvoiceConfigurationExceptionInheritance1() {
        InvoiceConfigurationException exception = new InvoiceConfigurationException(ERROR_MESSAGE);
        assertTrue("InvoiceConfigurationException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvoiceConfigurationException subclasses Exception.
     * </p>
     */
    public void testInvoiceConfigurationExceptionInheritance2() {
        InvoiceConfigurationException exception = new InvoiceConfigurationException(ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("InvoiceConfigurationException does not subclass Exception.", exception instanceof Exception);
    }
}