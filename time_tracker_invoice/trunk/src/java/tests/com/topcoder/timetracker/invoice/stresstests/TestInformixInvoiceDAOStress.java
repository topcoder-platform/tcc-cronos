/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixInvoiceDAO </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestInformixInvoiceDAOStress extends TestCase {

    /**
     * Represents the InformixInvoiceDAO instance for tesitng.
     */
    private InformixInvoiceDAO dao = null;

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
        cm.add(new File("test_files/stress/InformixInvoiceDAO.xml").getCanonicalPath());

        dao = new InformixInvoiceDAO();

        Connection connection = DBUtil.getConnection();

        try {
            DBUtil.insertRecordInto_invoice_status(connection, 1);

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_invoice(connection, i);
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
     * Test the method <code>void addInvoice(Invoice invoice, boolean audit) </code>.
     *
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testAddInvoice() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setCompanyId(1);
        invoice.setPaid(true);
        invoice.setCreationDate(new java.sql.Date(System.currentTimeMillis()));
        invoice.setCreationUser("user");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("user");

        invoice.setDueDate(new Date());
        invoice.setInvoiceNumber("1.3");
        invoice.setProjectId(1);
        invoice.setPurchaseOrderNumber("pool");
        invoice.setInvoiceDate(new Date());

        PaymentTerm term = new PaymentTerm();
        term.setActive(true);
        term.setCreationDate(new Date());
        term.setCreationUser("user");
        term.setModificationDate(new Date());
        term.setModificationUser("user");
        term.setDescription("des");
        term.setTerm(10);
        term.setId(10);

        invoice.setPaymentTerm(term);
        invoice.setExpenseEntries(new ExpenseEntry[0]);
        invoice.setFixedBillingEntries(new FixedBillingEntry[0]);

        InvoiceStatus status = new InvoiceStatus(1, "des", "user", "reviewer", new Date(), new Date());

        invoice.setSalesTax(new BigDecimal("11.3"));

        invoice.setServiceDetails(new InvoiceServiceDetail[0]);

        invoice.setInvoiceStatus(status);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            invoice.setId(-1);
            dao.addInvoice(invoice, false);

        }

        long end = System.currentTimeMillis();

        System.out.println("add 20 Invoice cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code> void updateInvoice(Invoice invoice, boolean audit) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateInvoice() throws Exception {
        Invoice invoice = dao.getInvoice(10);

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 20; i++) {
            invoice.setModificationUser("topcoder" + i);

            dao.updateInvoice(invoice, false);
        }

        long end = System.currentTimeMillis();

        System.out.println("Update Invoice 20 times cost " + (end - start) / 1000.0 + " seconds.");
        Invoice ret = dao.getInvoice(10);

        System.out.println(ret.getModificationUser());
    }

    /**
     * Test method <code>Invoice getInvoice(long invoiceId)  </code>.
     *
     */
    public void testGetInvoice() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            Invoice invoice = dao.getInvoice(1);
            assertNotNull("Should not be null.", invoice);
        }

        long end = System.currentTimeMillis();

        System.out.println("get Invoice 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>searchInvoices(Filter filter, InvoiceSearchDepth depth) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchInvoices() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            Invoice[] ret = dao.searchInvoices(InformixInvoiceFilterFactory.createProjectIdFilter(10),
                    InvoiceSearchDepth.INVOICE_ONLY);


        }
        long end = System.currentTimeMillis();
        System.out.println("Search Invoice 20 times cost " + (end - start) / 1000.0 + " seconds.");

    }

    /**
     * Test method <code>Invoice[] getAllInvoices() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetAllInvoices() throws Exception {
        long start = System.currentTimeMillis();
        Invoice[] all = dao.getAllInvoices();

        long end = System.currentTimeMillis();

        System.out.println("Get all Invoices cost " + (end - start) / 1000.0 + " seconds.");

        assertEquals("Equal is expected.", 20, all.length);
    }

    /**
     * Test method <code>boolean canCreateInvoice(long projectId) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCanCreateInvoice() throws Exception {
        boolean ret = false;

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 20; i++) {
            ret = dao.canCreateInvoice(1);
        }

        long end = System.currentTimeMillis();

        System.out.println("Calling for 20 times canCreateInvoice cost " + (end - start) / 1000.0);
        assertTrue("True is expected.", ret);
    }

}