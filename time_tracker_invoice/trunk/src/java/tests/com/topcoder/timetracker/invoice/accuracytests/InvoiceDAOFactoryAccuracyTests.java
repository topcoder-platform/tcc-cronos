/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.invoice.InvoiceDAO;
import com.topcoder.timetracker.invoice.InvoiceDAOFactory;
import com.topcoder.timetracker.invoice.InvoiceStatusDAO;

/**
 * <p>
 * Accuracy Unit test cases for InvoiceDAOFactory.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InvoiceDAOFactoryAccuracyTests extends TestCase {

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();
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
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InvoiceDAOFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests InvoiceDAOFactory#getInvoiceDAO() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetInvoiceDAO() throws Exception {
        InvoiceDAO dao1 = InvoiceDAOFactory.getInvoiceDAO();
        InvoiceDAO dao2 = InvoiceDAOFactory.getInvoiceDAO();
        assertNotNull("Failed to get the invoice dao correctly.", dao1);
        assertSame("Failed to get the invoice dao correctly.", dao1, dao2);
    }

    /**
     * <p>
     * Tests InvoiceDAOFactory#getInvoiceStatusDAO() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetInvoiceStatusDAO() throws Exception {
        InvoiceStatusDAO dao1 = InvoiceDAOFactory.getInvoiceStatusDAO();
        InvoiceStatusDAO dao2 = InvoiceDAOFactory.getInvoiceStatusDAO();
        assertNotNull("Failed to get the invoice status dao correctly.", dao1);
        assertSame("Failed to get the invoice status dao correctly.", dao1, dao2);
    }

}