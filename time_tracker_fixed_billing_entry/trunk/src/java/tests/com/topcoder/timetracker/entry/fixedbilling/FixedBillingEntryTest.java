/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link FixedBillingEntry}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntryTest extends TestCase {
    /** The FixedBillingEntry instance for testing. */
    private FixedBillingEntry entry;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        entry = new FixedBillingEntry();
    }

    /**
     * Test the <code>{@link FixedBillingEntry#FixedBillingEntry()}</code> with success process.
     */
    public void testConstructor_Success() {
        assertNotNull("Unable to create the instance.", entry);
    }

    /**
     * Test the <code>{@link FixedBillingEntry#getFixedBillingStatus()}</code> with success process.
     */
    public void testGetFixedBillingStatus_Success() {
        testSetFixedBillingStatus_FixedBillingStatus_Success();
    }

    /**
     * Test the <code>{@link FixedBillingEntry#setFixedBillingStatus(FixedBillingStatus)}</code> with null value.
     * Should throw an IllegalArgumentException here.
     */
    public void testSetFixedBillingStatus_FixedBillingStatus_Null() {
        try {
            entry.setFixedBillingStatus(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntry#setFixedBillingStatus(FixedBillingStatus)}</code> with success process.
     */
    public void testSetFixedBillingStatus_FixedBillingStatus_Success() {
        FixedBillingStatus fixedBillingStatus = new FixedBillingStatus();
        entry.setFixedBillingStatus(fixedBillingStatus);
        assertEquals("The return result should be same.", fixedBillingStatus, entry.getFixedBillingStatus());
    }

    /**
     * Test the <code>{@link FixedBillingEntry#getInvoiceId()}</code> with success process.
     */
    public void testGetInvoiceId_Success() {
        tsetSetInvoiceId_long_Success();
    }

    /**
     * Test the <code>{@link FixedBillingEntry#setInvoiceId(long)}</code> with wrong value. Should throw an
     * IllegalArgumentException here.
     */
    public void tsetSetInvoiceId_long_LessThanZero() {
        try {
            entry.setInvoiceId(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntry#setInvoiceId(long)}</code> with success process.
     */
    public void tsetSetInvoiceId_long_Success() {
        entry.setInvoiceId(1);
        assertEquals("The return result should be same.", 1, entry.getInvoiceId());
    }

    /**
     * Test the <code>{@link FixedBillingEntry#getAmount()}</code> with success process.
     */
    public void testGetAmount_Success() {
        testSetAmount_double_Success();
    }

    /**
     * Test the <code>{@link FixedBillingEntry#setAmount(double)}</code> with wrong value. Should throw an
     * IllegalArgumentException here.
     */
    public void testSetAmount_double_LessThanZero() {
        try {
            entry.setAmount(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntry#setAmount(double)}</code> with success process.
     */
    public void testSetAmount_double_Success() {
        entry.setAmount(1);
        assertEquals("The return result should be same.", 1, 1, entry.getAmount());
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingEntryTest.class);
    }
}
