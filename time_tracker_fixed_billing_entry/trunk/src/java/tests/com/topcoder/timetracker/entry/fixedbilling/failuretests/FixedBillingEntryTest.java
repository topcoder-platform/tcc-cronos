/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class FixedBillingEntryTest extends TestCase {
    /**
     * <p>
     * Represents the FixedBillingEntry for testing.
     * </p>
     */
    private FixedBillingEntry entry;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        entry = new FixedBillingEntry();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        entry = null;
    }

    /**
     * Failure test for <code>{@link FixedBillingEntry#setInvoiceId(long)}</code>.
     */
    public void testMethodSetInvoiceId_long() {
        try {
            entry.setInvoiceId(-2);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e ) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntry#setAmount(double)}</code>.
     */
    public void testMethodSetAmount_double() {
        try {
            entry.setAmount(-1.0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e ) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntry#setFixedBillingStatus(FixedBillingStatus)}</code>.
     */
    public void testMethodSetFixedBillingStatus_FixedBillingStatus() {
        try {
            entry.setFixedBillingStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e ) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(FixedBillingEntryTest.class);
    }
}
