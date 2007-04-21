/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceStatus</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceStatusTest extends TestCase {

    /** Unit under test. */
    private InvoiceStatus invoiceStatus;

    /** Creation date used in the unit test. */
    private Date creationDate;

    /** Modification date used in the unit test. */
    private Date modificationDate;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InvoiceStatusTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        creationDate = Calendar.getInstance().getTime();
        modificationDate = Calendar.getInstance().getTime();

        invoiceStatus = new InvoiceStatus(5, "testStatus", "TCS", "TCS", creationDate, modificationDate);

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
    public void testInvoiceStatusAccuracy() {
        assertNotNull(new InvoiceStatus(5, "testStatus", "TCS", "TCS", creationDate, modificationDate));
    }

    /**
     * Test constructor for failure. Condition: description is null. Expect: <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusDescriptionNull() {
        try {
            assertNotNull(new InvoiceStatus(5, null, "TCS", "TCS", creationDate, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: description is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusDescriptionEmptyString() {
        try {
            assertNotNull(new InvoiceStatus(5, "   \n", "TCS", "TCS", creationDate, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: creation user is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusCreationUserIsNull() {
        try {
            assertNotNull(new InvoiceStatus(5, "testStatus", null, "TCS", creationDate, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: creation user is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusCreationUserEmptyString() {
        try {
            assertNotNull(new InvoiceStatus(5, "testStatus", "   \n", "TCS", creationDate, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: modification user is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusModificationUserNull() {
        try {
            assertNotNull(new InvoiceStatus(5, "testStatus", "TCS", null, creationDate, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: modification user is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusModificationUserEmptyString() {
        try {
            assertNotNull(new InvoiceStatus(5, "testStatus", "TCS", "", creationDate, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: creation date is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusCreationDateNull() {
        try {
            assertNotNull(new InvoiceStatus(5, "testStatus", "TCS", "TCS", null, modificationDate));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: modification date is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testInvoiceStatusModificationDateNull() {
        try {
            assertNotNull(new InvoiceStatus(5, "testStatus", "TCS", "TCS", creationDate, null));
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getCreationDate</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetCreationDate() {
        assertEquals("Returned value is not as expected", creationDate, invoiceStatus.getCreationDate());
    }

    /**
     * Test <code>getCreationUser</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetCreationUser() {
        assertEquals("Returned value is not as expected", "TCS", invoiceStatus.getCreationUser());
    }

    /**
     * Test <code>getDescription</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetDescription() {
        assertEquals("Returned value is not as expected", "testStatus", invoiceStatus.getDescription());
    }

    /**
     * Test <code>getId</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetId() {
        assertEquals("Returned value is not as expected", 5, invoiceStatus.getId());
    }

    /**
     * Test <code>getModificationDate</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     */
    public void testGetModificationDate() {
        assertEquals("Returned value is not as expected", modificationDate, invoiceStatus.getModificationDate());
    }

    /**
     * Test <code>getModificationUser</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     */
    public void testGetModificationUser() {
        assertEquals("Returned value is not as expected", "TCS", invoiceStatus.getModificationUser());
    }

}
