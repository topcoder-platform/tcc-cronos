/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix.filterfactory;

import java.util.Calendar;

import com.topcoder.search.builder.filter.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InformixInvoiceFilterFactory</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InformixInvoiceFilterFactoryTest extends TestCase {

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InformixInvoiceFilterFactoryTest.class);
    }

    /**
     * Test <code>createCompanyIdFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateCompanyIdFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createCompanyIdFilter(100);

        assertEquals("The filter is not created correctly", "company_id", filter.getName());

    }

    /**
     * Test <code>createProjectIdFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateProjectIdFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createProjectIdFilter(100);

        assertEquals("The filter is not created correctly", "project_id", filter.getName());
    }

    /**
     * Test <code>createClientIdFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateClientIdFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createClientIdFilter(100);

        assertEquals("The filter is not created correctly", "client_id", filter.getName());
    }

    /**
     * Test <code>createInvoiceStatusIdFilter</code> for accuracy. Condition: normal. Expect: all fields are set
     * as expected.
     */
    public void testCreateInvoiceStatusIdFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(100);

        assertEquals("The filter is not created correctly", "invoice_invoice_status_id", filter.getName());
    }

    /**
     * Test <code>createInvoiceNumberFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateInvoiceNumberFilterAccuracy() {
        EqualToFilter filter =
            (EqualToFilter) InformixInvoiceFilterFactory.createInvoiceNumberFilter("invoiceNumber");

        assertEquals("The filter is not created correctly", "invoice_number", filter.getName());
    }

    /**
     * Test <code>createInvoiceDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateInvoiceDateFilterAccuracy1() {
        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        BetweenFilter filter = (BetweenFilter)InformixInvoiceFilterFactory
                .createInvoiceDateFilter(fromCal.getTime(), toCal.getTime());

        assertEquals("The filter is not created correctly", filter.getLowerThreshold(), fromCal.getTime());
        assertEquals("The filter is not created correctly", filter.getUpperThreshold(), toCal.getTime());        
    }

    /**
     * Test <code>createInvoiceDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateInvoiceDateFilterAccuracy2() {
        Calendar fromCal = Calendar.getInstance();
        GreaterThanOrEqualToFilter filter =
            (GreaterThanOrEqualToFilter) InformixInvoiceFilterFactory.createInvoiceDateFilter(fromCal.getTime(),
                null);

        assertEquals("The filter is not created correctly", "invoice_date", filter.getName());
    }

    /**
     * Test <code>createInvoiceDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateInvoiceDateFilterAccuracy3() {
        Calendar toCal = Calendar.getInstance();
        LessThanOrEqualToFilter filter =
            (LessThanOrEqualToFilter) InformixInvoiceFilterFactory.createInvoiceDateFilter(null, toCal.getTime());

        assertEquals("The filter is not created correctly", "invoice_date", filter.getName());
    }

    /**
     * Test <code>createInvoiceDateFilter</code> for failure. Condition: to and from are null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateInvoiceDateFilterBothNull() {
        try {
            InformixInvoiceFilterFactory.createInvoiceDateFilter(null, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createDueDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateDueDateFilterAccuracy1() {
        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        BetweenFilter filter = (BetweenFilter)InformixInvoiceFilterFactory
                .createInvoiceDateFilter(fromCal.getTime(), toCal.getTime());

        assertEquals("The filter is not created correctly", filter.getLowerThreshold(), fromCal.getTime());
        assertEquals("The filter is not created correctly", filter.getUpperThreshold(), toCal.getTime());
    }

    /**
     * Test <code>createDueDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateDueDateFilterAccuracy2() {
        Calendar fromCal = Calendar.getInstance();
        GreaterThanOrEqualToFilter filter =
            (GreaterThanOrEqualToFilter) InformixInvoiceFilterFactory.createDueDateFilter(fromCal.getTime(), null);

        assertEquals("The filter is not created correctly", "due_date", filter.getName());
    }

    /**
     * Test <code>createDueDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateDueDateFilterAccuracy3() {
        Calendar toCal = Calendar.getInstance();
        LessThanOrEqualToFilter filter =
            (LessThanOrEqualToFilter) InformixInvoiceFilterFactory.createDueDateFilter(null, toCal.getTime());

        assertEquals("The filter is not created correctly", "due_date", filter.getName());
    }

    /**
     * Test <code>createModificationDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set
     * as expected.
     */
    public void testCreateModificationDateFilterAccuracy1() {
        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        BetweenFilter filter = (BetweenFilter)InformixInvoiceFilterFactory
                .createInvoiceDateFilter(fromCal.getTime(), toCal.getTime());

        assertEquals("The filter is not created correctly", filter.getLowerThreshold(), fromCal.getTime());
        assertEquals("The filter is not created correctly", filter.getUpperThreshold(), toCal.getTime());
    }

    /**
     * Test <code>createModificationDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set
     * as expected.
     */
    public void testModificationInvoiceDateFilterAccuracy2() {
        Calendar fromCal = Calendar.getInstance();
        GreaterThanOrEqualToFilter filter =
            (GreaterThanOrEqualToFilter) InformixInvoiceFilterFactory.createModificationDateFilter(fromCal
                .getTime(), null);

        assertEquals("The filter is not created correctly", "modification_date", filter.getName());
    }

    /**
     * Test <code>createModificationDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set
     * as expected.
     */
    public void testCreateModificationDateFilterAccuracy3() {
        Calendar toCal = Calendar.getInstance();
        LessThanOrEqualToFilter filter =
            (LessThanOrEqualToFilter) InformixInvoiceFilterFactory.createModificationDateFilter(null, toCal
                .getTime());

        assertEquals("The filter is not created correctly", "modification_date", filter.getName());
    }

    /**
     * Test <code>createCreationDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateCreationDateFilterAccuracy1() {
        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        BetweenFilter filter = (BetweenFilter)InformixInvoiceFilterFactory
                .createInvoiceDateFilter(fromCal.getTime(), toCal.getTime());

        assertEquals("The filter is not created correctly", filter.getLowerThreshold(), fromCal.getTime());
        assertEquals("The filter is not created correctly", filter.getUpperThreshold(), toCal.getTime());
    }

    /**
     * Test <code>createCreationDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateCreationDateFilterAccuracy2() {
        Calendar fromCal = Calendar.getInstance();
        GreaterThanOrEqualToFilter filter =
            (GreaterThanOrEqualToFilter) InformixInvoiceFilterFactory.createCreationDateFilter(fromCal.getTime(),
                null);

        assertEquals("The filter is not created correctly", "creation_date", filter.getName());
    }

    /**
     * Test <code>createCreationDateFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreateCreationDateFilterAccuracy3() {
        Calendar toCal = Calendar.getInstance();
        LessThanOrEqualToFilter filter =
            (LessThanOrEqualToFilter) InformixInvoiceFilterFactory.createCreationDateFilter(null, toCal.getTime());

        assertEquals("The filter is not created correctly", "creation_date", filter.getName());
    }

    /**
     * Test <code>createCreationUsernameIdFilter</code> for accuracy. Condition: normal. Expect: all fields are
     * set as expected.
     */
    public void testCreateCreationUsernameFilterAccuracy() {
        LikeFilter filter = (LikeFilter) InformixInvoiceFilterFactory.createCreationUsernameFilter("SS:test");

        assertEquals("The filter is not created correctly", "creation_user", filter.getName());
    }

    /**
     * Test <code>createModificationUsername</code> for accuracy. Condition: normal. Expect: all fields are set
     * as expected.
     */
    public void testCreateModificationUsernameFilterAccuracy() {
        LikeFilter filter = (LikeFilter) InformixInvoiceFilterFactory.createModificationUsernameFilter("SS:test");

        assertEquals("The filter is not created correctly", "modification_user", filter.getName());
    }

    /**
     * Test <code>createPaidIdFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreatePaidFilterAccuracy1() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createPaidFilter(true);

        assertEquals("The filter is not created correctly", "paid", filter.getName());
        assertEquals("The filter is not created correctly", new Integer(1), filter.getValue());
    }

    /**
     * Test <code>createPaidIdFilter</code> for accuracy. Condition: normal. Expect: all fields are set as
     * expected.
     */
    public void testCreatePaidFilterAccuracy2() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createPaidFilter(false);

        assertEquals("The filter is not created correctly", new Integer(0), filter.getValue());
    }

}
