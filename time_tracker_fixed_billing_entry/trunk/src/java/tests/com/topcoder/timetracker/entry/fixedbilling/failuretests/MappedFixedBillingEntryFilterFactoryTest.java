/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingEntryFilterFactory;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingEntryFilterFactory}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class MappedFixedBillingEntryFilterFactoryTest extends TestCase {
    /**
     * <p>
     * Represents the MappedBaseFilterFactory for testing.
     * </p>
     */
    private MappedFixedBillingEntryFilterFactory factory;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        factory = new MappedFixedBillingEntryFilterFactory();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        factory = null;
    }

    /**
     * Failure test for
     * <code>{@link MappedFixedBillingEntryFilterFactory#createAmountFilter(double,double)}</code>.
     */
    public void testMethodCreateAmountFilter_IAE() {
        try {
            factory.createAmountFilter(1.0, 0.0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createAmountFilter(Double.MIN_VALUE, Double.MAX_VALUE);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link MappedFixedBillingEntryFilterFactory#createRejectReasonFilter(long)}</code>.
     */
    public void testMethodCreateRejectReasonFilter_IAE() {
        try {
            factory.createRejectReasonFilter(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link MappedFixedBillingEntryFilterFactory#createInvoiceIdFilter(long)}</code>.
     */
    public void testMethodCreateInvoiceIdFilter_IAE() {
        try {
            factory.createInvoiceIdFilter(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link MappedFixedBillingEntryFilterFactory#createCompanyIdFilter(long)}</code>.
     */
    public void testMethodCreateCompanyIdFilter_IAE() {
        try {
            factory.createCompanyIdFilter(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link MappedFixedBillingEntryFilterFactory#createFixedBillingStatusFilter(FixedBillingStatus)}</code>.
     */
    public void testMethodCreateFixedBillingStatusFilter_IAE() {
        try {
            factory.createFixedBillingStatusFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link MappedFixedBillingEntryFilterFactory#createDescriptionFilter(String,StringMatchType)}</code>.
     */
    public void testMethodCreateDescriptionFilter_IAE() {
        try {
            factory.createDescriptionFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createDescriptionFilter("test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createDescriptionFilter(" ", StringMatchType.EXACT_MATCH);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link MappedFixedBillingEntryFilterFactory#createEntryDateFilter(Date,Date)}</code>.
     */
    public void testMethodCreateEntryDateFilter_IAE() {
        try {
            factory.createEntryDateFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createEntryDateFilter(new Date(99999), new Date(88888));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(MappedFixedBillingEntryFilterFactoryTest.class);
    }
}
