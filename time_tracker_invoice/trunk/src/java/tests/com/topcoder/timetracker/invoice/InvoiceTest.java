/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import java.math.BigDecimal;
import java.util.Calendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>Invoice</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceTest extends TestCase {

    /** Unit under test. */
    private Invoice invoice;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InvoiceTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        invoice = new Invoice();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: normal.
     */
    public void testInvoice() {
        assertNotNull(new Invoice());
    }

    /**
     * Test <code>getCompanyId</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testGetCompanyId() {
        invoice.setCompanyId(10);

        assertEquals(10, invoice.getCompanyId());
    }

    /**
     * Test <code>getDueDate</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testGetDueDate() {
        Calendar instance = Calendar.getInstance();
        invoice.setDueDate(instance.getTime());

        assertEquals(instance.getTime(), invoice.getDueDate());
    }

    /**
     * Test <code>getProjectId</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testGetProjectId() {
        invoice.setProjectId(10);

        assertEquals(10, invoice.getProjectId());
    }

    /**
     * Test <code>getSalexTax</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testGetSalexTax() {
        BigDecimal st = new BigDecimal(10);
        invoice.setSalesTax(st);

        assertEquals(st, invoice.getSalesTax());
    }
}
