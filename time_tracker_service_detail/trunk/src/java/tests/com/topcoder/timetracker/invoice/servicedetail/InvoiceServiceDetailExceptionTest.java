/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Unit test cases for <code>InvoiceServiceDetailException</code>.
 * </p>
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error
 * message and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceServiceDetailExceptionTest extends TestCase {

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(InvoiceServiceDetailExceptionTest.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testInvoiceServiceDetailException1() {
        final InvoiceServiceDetailException ex = new InvoiceServiceDetailException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate InvoiceServiceDetailException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex
            .getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testInvoiceServiceDetailException2() {
        Exception cause = new Exception();
        InvoiceServiceDetailException ex = new InvoiceServiceDetailException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate InvoiceServiceDetailException.", ex);
        assertEquals("Cause is not properly propagated to super class.", cause, ex.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>InvoiceServiceDetailException</code> subclasses <code>BaseException</code>.
     * </p>
     */
    public void testInvoiceServiceDetailExceptionInheritance1() {
        assertTrue("InvoiceServiceDetailException does not subclass BaseException.",
            new InvoiceServiceDetailException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>InvoiceServiceDetailException</code> subclasses <code>BaseException</code>.
     * </p>
     */
    public void testInvoiceServiceDetailExceptionInheritance2() {
        assertTrue("InvoiceServiceDetailException does not subclass BaseException.",
            new InvoiceServiceDetailException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }
}
