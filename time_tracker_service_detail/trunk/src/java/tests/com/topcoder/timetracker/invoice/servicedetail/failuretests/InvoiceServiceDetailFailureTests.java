/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Failure test cases for <code>InvoiceServiceDetail</code>.
 * </p>
 * @author myxgyy
 * @version 1.0
 */
public class InvoiceServiceDetailFailureTests extends TestCase {
    /**
     * Unit under test.
     */
    private InvoiceServiceDetail invoiceServiceDetail;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     * @return the suite.
     */
    public static Test suite() {
        return new TestSuite(InvoiceServiceDetailFailureTests.class);
    }

    /**
     * Sets up the tests.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        invoiceServiceDetail = new InvoiceServiceDetail();
    }

    /**
     * <p>
     * Test <code>setTimeEntry</code> for failure.
     * </p>
     * <p>
     * Condition: timeEntry is null.
     * </p>
     * <p>
     * Expect: <code>IllegalArgumentException</code>.
     * </p>
     */
    public void testSetTimeEntryNull() {
        try {
            invoiceServiceDetail.setTimeEntry(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test <code>setRate</code> for failure.
     * </p>
     * <p>
     * Condition: rate is negative.
     * </p>
     * <p>
     * Expect: <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetRateNegative() throws Exception {
        try {
            invoiceServiceDetail.setRate(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test <code>setAmount</code> for failure.
     * </p>
     * <p>
     * <p>
     * Condition: amount is null.
     * </p>
     * <p>
     * Expect: <code>IllegalArgumentException</code>.
     * </p>
     */
    public void testSetAmountNull() {
        try {
            invoiceServiceDetail.setAmount(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
