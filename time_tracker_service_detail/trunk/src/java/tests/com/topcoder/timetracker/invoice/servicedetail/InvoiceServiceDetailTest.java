/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceServiceDetail</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceServiceDetailTest extends TestCase {

    /** Unit under test. */
    private InvoiceServiceDetail invoiceServiceDetail;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InvoiceServiceDetailTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        invoiceServiceDetail = new InvoiceServiceDetail();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is constructed.
     */
    public void testInvoiceServiceDetailAccuracy() {
        assertNotNull("The creation of the object is failed", new InvoiceServiceDetail());
    }

    /**
     * Test <code>getInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetInvoiceAccuracy1() {
        assertEquals("Returned value is not as expected", null, invoiceServiceDetail.getInvoice());
    }

    /**
     * Test <code>getInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetInvoiceAccuracy2() {
        Invoice invoice = new Invoice();
        invoiceServiceDetail.setInvoice(invoice);

        assertEquals("Returned value is not as expected", invoice, invoiceServiceDetail.getInvoice());
    }

    /**
     * Test <code>setInvoice</code> for accuracy. Condition: normal. Expect: the field is set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetInvoiceAccuracy() throws Exception {
        Invoice invoice = new Invoice();
        invoiceServiceDetail.setInvoice(invoice);

        // check the invoice fields
        Field invoiceField = InvoiceServiceDetail.class.getDeclaredField("invoice");
        invoiceField.setAccessible(true);
        assertEquals("The field is not set as expected", invoice, invoiceField.get(invoiceServiceDetail));
    }

    /**
     * Test <code>getTimeEntry</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetTimeEntryAccuracy1() {
        assertEquals("Returned value is not as expected", null, invoiceServiceDetail.getTimeEntry());
    }

    /**
     * Test <code>getTimeEntry</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetTimeEntry1Accuracy2() {
        TimeEntry timeEntry = new TimeEntry();
        invoiceServiceDetail.setTimeEntry(timeEntry);

        assertEquals("Returned value is not as expected", timeEntry, invoiceServiceDetail.getTimeEntry());
    }

    /**
     * Test <code>setTimeEntry</code> for accuracy. Condition: normal. Expect: field is set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetTimeEntryAccuracy() throws Exception {
        TimeEntry timeEntry = new TimeEntry();
        invoiceServiceDetail.setTimeEntry(timeEntry);

        // check the timeEntry fields
        Field timeEntryField = InvoiceServiceDetail.class.getDeclaredField("timeEntry");
        timeEntryField.setAccessible(true);
        assertEquals("The field is not set as expected", timeEntry, timeEntryField.get(invoiceServiceDetail));
    }

    /**
     * Test <code>setTimeEntry</code> for failure. Condition: timeEntry is null. Expect:
     * <code>IllegalArgumentException</code>.
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
     * Test <code>getRate</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetRateAccuracy1() {
        assertEquals("Returned value is not as expected", -1, invoiceServiceDetail.getRate());
    }

    /**
     * Test <code>getRate</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetRateAccuracy2() {
        invoiceServiceDetail.setRate(10);

        assertEquals("Returned value is not as expected", 10, invoiceServiceDetail.getRate());
    }

    /**
     * Test <code>setRate</code> for accuracy. Condition: normal. Expect: field is set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetRateAccuracy() throws Exception {
        invoiceServiceDetail.setRate(10);

        // check the rate fields
        Field rateField = InvoiceServiceDetail.class.getDeclaredField("rate");
        rateField.setAccessible(true);
        assertEquals("The field is not set as expected", 10, ((Integer) rateField.get(invoiceServiceDetail))
            .intValue());
    }

    /**
     * Test <code>setRate</code> for failure. Condition: rate is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
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
     * Test <code>getAmount</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetAmountAccuracy1() {
        assertEquals("Returned value is not as expected", null, invoiceServiceDetail.getAmount());
    }

    /**
     * Test <code>getAmount</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetAmountAccuracy2() {
        BigDecimal decimal = new BigDecimal("100");
        invoiceServiceDetail.setAmount(decimal);

        assertEquals("Returned value is not as expected", decimal, invoiceServiceDetail.getAmount());
    }

    /**
     * Test <code>setAmount</code> for accuracy. Condition: normal. Expect: field is set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetAmountAccuracy() throws Exception {
        BigDecimal decimal = new BigDecimal("100");
        invoiceServiceDetail.setAmount(decimal);

        // check the amount fields
        Field amountField = InvoiceServiceDetail.class.getDeclaredField("amount");
        amountField.setAccessible(true);
        assertEquals("The field is not set as expected", decimal, amountField.get(invoiceServiceDetail));
    }

    /**
     * Test <code>setAmount</code> for failure. Condition: amount is null. Expect:
     * <code>IllegalArgumentException</code>.
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
