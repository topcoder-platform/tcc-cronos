/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for InformixInvoiceDAO.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InformixInvoiceDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * InformixInvoiceDAO instance for testing.
     * </p>
     */
    private InformixInvoiceDAO instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();

        instance = new InformixInvoiceDAO();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();

        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InformixInvoiceDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InformixInvoiceDAO#InformixInvoiceDAO() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create InformixInvoiceDAO instance.", instance);
    }

    /**
     * <p>
     * Tests ctor InformixInvoiceDAO#InformixInvoiceDAO(String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        assertNotNull("Failed to create InformixInvoiceDAO instance.", new InformixInvoiceDAO(
            "com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO"));
    }

    /**
     * <p>
     * Tests InformixInvoiceDAO#addInvoice(Invoice,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        instance.addInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, instance.getInvoice(invoice.getId()));
    }

    /**
     * <p>
     * Tests InformixInvoiceDAO#updateInvoice(Invoice,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        instance.addInvoice(invoice, false);

        invoice.setInvoiceNumber("new");
        instance.updateInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, instance.getInvoice(invoice.getId()));
    }

    /**
     * <p>
     * Tests InformixInvoiceDAO#getInvoice(long) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        instance.addInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, instance.getInvoice(invoice.getId()));
    }

    /**
     * <p>
     * Tests InformixInvoiceDAO#searchInvoices(Filter,InvoiceSearchDepth) for accuracy.
     * </p>
     * @throws Exception to JUnit
    */
    public void testSearchInvoices() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        instance.addInvoice(invoice, false);

        Invoice[] invoices = instance.searchInvoices(
            InformixInvoiceFilterFactory.createInvoiceNumberFilter("invoiceNumber"), InvoiceSearchDepth.INVOICE_ONLY);
    }
 
    /**
     * <p>
     * Tests InformixInvoiceDAO#getAllInvoices() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAllInvoices() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        instance.addInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, instance.getAllInvoices()[0]);
    }

    /**
     * <p>
     * Tests InformixInvoiceDAO#canCreateInvoice(long) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCanCreateInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        instance.addInvoice(invoice, false);

        assertTrue("Failed to return the value.", instance.canCreateInvoice(8));
    }

}