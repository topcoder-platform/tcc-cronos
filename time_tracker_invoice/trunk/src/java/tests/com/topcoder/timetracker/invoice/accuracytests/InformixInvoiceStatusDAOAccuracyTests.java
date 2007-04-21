/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO;

/**
 * <p>
 * Accuracy Unit test cases for InformixInvoiceStatusDAO.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InformixInvoiceStatusDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * InformixInvoiceStatusDAO instance for testing.
     * </p>
     */
    private InformixInvoiceStatusDAO instance;

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

        instance = new InformixInvoiceStatusDAO();
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
        return new TestSuite(InformixInvoiceStatusDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InformixInvoiceStatusDAO#InformixInvoiceStatusDAO(String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        assertNotNull("Failed to create InformixInvoiceStatusDAO instance.", new InformixInvoiceStatusDAO(
            "com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO"));
    }

    /**
     * <p>
     * Tests ctor InformixInvoiceStatusDAO#InformixInvoiceStatusDAO() for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create InformixInvoiceStatusDAO instance.", instance);
    }

    /**
     * <p>
     * Tests InformixInvoiceStatusDAO#getInvoiceStatus(long) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetInvoiceStatus1() throws Exception {
        InvoiceStatus invoiceStatus = instance.getInvoiceStatus(11);
        assertEquals("Failed to get the status.", 11, invoiceStatus.getId());
    }

    /**
     * <p>
     * Tests InformixInvoiceStatusDAO#getInvoiceStatus(String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetInvoiceStatus2() throws Exception {
        InvoiceStatus invoiceStatus = instance.getInvoiceStatus("description");
        assertEquals("Failed to get the status.", 11, invoiceStatus.getId());
    }

    /**
     * <p>
     * Tests InformixInvoiceStatusDAO#getAllInvoiceStatus() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAllInvoiceStatus() throws Exception {
        InvoiceStatus[] invoiceStatuses = instance.getAllInvoiceStatus();
        assertEquals("Failed to get all the status.", 11, invoiceStatuses[0].getId());
    }

}