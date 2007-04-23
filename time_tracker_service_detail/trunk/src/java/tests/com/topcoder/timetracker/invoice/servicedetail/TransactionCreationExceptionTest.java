/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for <code>TransactionCreationException</code>.
 * </p>
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error
 * message and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author enefem21
 * @version 1.0
 */
public class TransactionCreationExceptionTest extends TestCase {

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
        return new TestSuite(TransactionCreationExceptionTest.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testTransactionCreationException1() {
        final TransactionCreationException ex = new TransactionCreationException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate TransactionCreationException.", ex);
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
    public void testTransactionCreationException2() {
        Exception cause = new Exception();
        TransactionCreationException ex = new TransactionCreationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate TransactionCreationException.", ex);
        assertEquals("Cause is not properly propagated to super class.", cause, ex.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>TransactionCreationException</code> subclasses <code>InvoiceServiceDetailException</code>.
     * </p>
     */
    public void testTransactionCreationExceptionInheritance1() {
        assertTrue("TransactionCreationException does not subclass InvoiceServiceDetailException.",
            new TransactionCreationException(ERROR_MESSAGE) instanceof InvoiceServiceDetailException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>TransactionCreationException</code> subclasses <code>InvoiceServiceDetailException</code>.
     * </p>
     */
    public void testTransactionCreationExceptionInheritance2() {
        assertTrue(
            "TransactionCreationException does not subclass InvoiceServiceDetailException.",
            new TransactionCreationException(ERROR_MESSAGE, new Exception()) instanceof InvoiceServiceDetailException);
    }
}
