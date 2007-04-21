/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for InformixInvoiceFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InformixInvoiceFilterFactoryAccuracyTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InformixInvoiceFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createCompanyIdFilter(8);
        assertEquals("Failed to create the filter.", "company_id", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createProjectIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateProjectIdFilter() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createProjectIdFilter(8);
        assertEquals("Failed to create the filter.", "project_id", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createClientIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateClientIdFilter() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createClientIdFilter(8);
        assertEquals("Failed to create the filter.", "client_id", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createInvoiceStatusIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateInvoiceStatusIdFilter() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(8);
        assertEquals("Failed to create the filter.", "invoice_invoice_status_id", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createInvoiceNumberFilter(String) for accuracy.
     * </p>
     */
    public void testCreateInvoiceNumberFilter() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createInvoiceNumberFilter("invoiceNumber");
        assertEquals("Failed to create the filter.", "invoice_number", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createInvoiceDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateInvoiceDateFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) InformixInvoiceFilterFactory.createInvoiceDateFilter(
            null, new Date());
        assertEquals("Failed to create the filter.", "invoice_date", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateCreationDateFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) InformixInvoiceFilterFactory.createCreationDateFilter(
            new Date(), null);
        assertEquals("Failed to create the filter.", "creation_date", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createDueDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateDueDateFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) InformixInvoiceFilterFactory.createDueDateFilter(
            null, new Date());
        assertEquals("Failed to create the filter.", "due_date", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateModificationDateFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) InformixInvoiceFilterFactory.createModificationDateFilter(
            new Date(), null);
        assertEquals("Failed to create the filter.", "modification_date", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createCreationUsernameFilter(String) for accuracy.
     * </p>
     */
    public void testCreateCreationUsernameFilter() {
        LikeFilter filter = (LikeFilter) InformixInvoiceFilterFactory.createCreationUsernameFilter("SW:creationUsername");
        assertEquals("Failed to create the filter.", "creation_user", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createModificationUsernameFilter(String) for accuracy.
     * </p>
     */
    public void testCreateModificationUsernameFilter() {
        LikeFilter filter = (LikeFilter) InformixInvoiceFilterFactory.createModificationUsernameFilter("SW:modificationUsername");
        assertEquals("Failed to create the filter.", "modification_user", filter.getName());
    }

    /**
     * <p>
     * Tests InformixInvoiceFilterFactory#createPaidFilter(boolean) for accuracy.
     * </p>
     */
    public void testCreatePaidFilter() {
        EqualToFilter filter = (EqualToFilter) InformixInvoiceFilterFactory.createPaidFilter(true);
        assertEquals("Failed to create the filter.", "paid", filter.getName());
    }

}