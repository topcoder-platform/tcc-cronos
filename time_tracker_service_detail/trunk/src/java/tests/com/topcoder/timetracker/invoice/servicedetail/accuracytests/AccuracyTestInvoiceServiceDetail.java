/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

/**
 * Unit test for <code>InvoiceServiceDetail</code>.
 * @author KLW
 * @version 1.1
 */
public class AccuracyTestInvoiceServiceDetail extends TestCase {

    /** Instance used for test. */
    private InvoiceServiceDetail invoiceServiceDetail;

    /**
     * Set up test enviroment.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        invoiceServiceDetail = new InvoiceServiceDetail();
    }

    /**
     * Test constructor for accuracy.
     */
    public void testConstructor() {
        assertNotNull("The creation of the object is failed", new InvoiceServiceDetail());
    }

    /**
     * Test <code>getInvoice</code> .
     */
    public void testGetInvoice_01() {
        assertEquals("Returned value is not as expected", null, invoiceServiceDetail.getInvoice());
    }

    /**
     * Test <code>getInvoice</code> .
     */
    public void testGetInvoice_02() {
        Invoice invoice = new Invoice();
        invoiceServiceDetail.setInvoice(invoice);
        assertEquals("Returned value is not as expected", invoice, invoiceServiceDetail.getInvoice());
    }

    /**
     * Test <code>setInvoice</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testSetInvoice_01() throws Exception {
        Invoice invoice = new Invoice();
        invoiceServiceDetail.setInvoice(invoice);

        assertEquals("The field is not set as expected", invoice, this.invoiceServiceDetail.getInvoice());
    }

    /**
     * Test <code>getTimeEntry</code>.
     */
    public void testGetTimeEntry_01() {
        assertEquals("Returned value is not as expected", null, invoiceServiceDetail.getTimeEntry());
    }

    /**
     * Test <code>getTimeEntry</code>.
     */
    public void testGetTimeEntry_02() {
        TimeEntry timeEntry = new TimeEntry();
        invoiceServiceDetail.setTimeEntry(timeEntry);
        assertEquals("Returned value is not as expected", timeEntry, invoiceServiceDetail.getTimeEntry());
    }

    /**
     * Test <code>setTimeEntry</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testSetTimeEntry_01() throws Exception {
        TimeEntry timeEntry = new TimeEntry();
        invoiceServiceDetail.setTimeEntry(timeEntry);
        assertEquals("Value is not correct.", timeEntry, this.invoiceServiceDetail.getTimeEntry());
    }

    /**
     * Test <code>setTimeEntry</code>.
     */
    public void testSetTimeEntry_02() {
        try {
            invoiceServiceDetail.setTimeEntry(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getRate</code>.
     */
    public void testGetRate_01() {
        assertEquals("Returned value is not as expected", -1, invoiceServiceDetail.getRate());
    }

    /**
     * Test <code>getRate</code>.
     */
    public void testGetRate_02() {
        invoiceServiceDetail.setRate(33);

        assertEquals("Returned value is not as expected", 33, invoiceServiceDetail.getRate());
    }

    /**
     * Test <code>setRate</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testSetRate_01() throws Exception {
        invoiceServiceDetail.setRate(10);
        assertEquals("Value is not correct.", 10, this.invoiceServiceDetail.getRate());
    }

    /**
     * Test <code>setRate</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testSetRate_02() throws Exception {
        try {
            invoiceServiceDetail.setRate(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getAmount</code>.
     */
    public void testGetAmount_01() {
        assertEquals("Returned value is not as expected", null, invoiceServiceDetail.getAmount());
    }

    /**
     * Test <code>getAmount</code>.
     */
    public void testGetAmount_02() {
        BigDecimal decimal = new BigDecimal("3.4");
        invoiceServiceDetail.setAmount(decimal);

        assertEquals("Returned value is not as expected", decimal, invoiceServiceDetail.getAmount());
    }

    /**
     * Test <code>setAmount</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testSetAmount_01() throws Exception {
        BigDecimal decimal = new BigDecimal("33");
        invoiceServiceDetail.setAmount(decimal);

        assertEquals("The value is not correct.", decimal, this.invoiceServiceDetail.getAmount());
    }

    /**
     * Test <code>setAmount</code> .
     */
    public void testSetAmount_02() {
        try {
            invoiceServiceDetail.setAmount(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
