/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import java.io.File;
import java.sql.Connection;
import java.util.Iterator;

import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixInvoiceStatusDAO </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestInformixInvoiceStatusDAOStress extends TestCase {

    /**
     * Represents the InformixInvoiceStatusDAO instance for testing.
     */
    private InformixInvoiceStatusDAO dao = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add(new File("test_files/stress/DBConnectionFactory.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/InformixInvoiceStatusDAO.xml").getCanonicalPath());

        dao = new InformixInvoiceStatusDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO");

        Connection connection = DBUtil.getConnection();
        try {
            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_invoice_status(connection, i);
            }

        } finally {
            DBUtil.closeConnection(connection);
        }

    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBUtil.clearTables();
        DBUtil.clearNamespaces();
    }

    /**
     * Stress test method for <code>InvoiceStatus[] getAllInvoiceStatus() </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetAllInvoiceStatus() throws Exception {
        long start = System.currentTimeMillis();
        InvoiceStatus[] ret = dao.getAllInvoiceStatus();

        long end = System.currentTimeMillis();

        System.out.println("select " + ret.length + " InvoiceStatus cost " + (end - start) / 1000.0 + " seconds.");

        assertEquals("Equal is expected.", 20, ret.length);
    }

    /**
     * Stress test cases for method <code>InvoiceStatus getInvoiceStatus(long id) </code>.
     */
    public void testGetInvoiceStatuslong() throws Exception {
        InvoiceStatus status = null;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            status = dao.getInvoiceStatus(10);
        }

        long end = System.currentTimeMillis();

        System.out.println("select one InvoiceStatus cost " + (end - start) / 1000.0 + " seconds.");

        assertNotNull("Should not be null.", status);

        assertEquals("Equal is expected.", "des10", status.getDescription());
    }

    /**
     * Stress test case for method <code>InvoiceStatus getInvoiceStatus(String description) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetInvoiceStatusString() throws Exception {
        InvoiceStatus status = null;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            status = dao.getInvoiceStatus("des10");
        }

        long end = System.currentTimeMillis();

        System.out.println("select one InvoiceStatus cost " + (end - start) / 1000.0 + " seconds.");

        assertNotNull("Should not be null.", status);

        assertEquals("Equal is expected.", 10, status.getId());
    }

}