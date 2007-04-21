/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceDAOFactory;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocal;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocalHome;
import com.topcoder.timetracker.invoice.ejb.InvoiceSessionBean;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for InvoiceSessionBean.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InvoiceSessionBeanAccuracyTests extends TestCase {

    /** Context used in the unit test. */
    private Context context;

    /**
     * <p>
     * InvoiceSessionBean instance for testing.
     * </p>
     */
    private InvoiceSessionBean instance;

    /**
     * <p>
     * InvoiceManagerDelegate instance for testing.
     * </p>
     */
    private InvoiceManagerDelegate delegate;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        InvoiceDAOFactory.clear();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();

        MockContextFactory.setAsInitial();
        context = new InitialContext();

        try {
            context.unbind("invoiceContext");
        } catch (NameNotFoundException e) {

        }
        Context invoiceContext = context.createSubcontext("invoiceContext");
        MockContainer mockContainer = new MockContainer(context);
        MockUserTransaction mockTransaction = new MockUserTransaction();
        invoiceContext.rebind("javax.transaction.UserTransaction", mockTransaction);
        instance = new InvoiceSessionBean();
        SessionBeanDescriptor sampleServiceDescriptor =
            new SessionBeanDescriptor("invoiceSessionBeanLocalHome", InvoiceManagerLocalHome.class,
                InvoiceManagerLocal.class, instance);
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new InvoiceManagerDelegate("com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();

        delegate = null;
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
        return new TestSuite(InvoiceSessionBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InvoiceSessionBean#InvoiceSessionBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create InvoiceSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#getInvoiceStatus(long) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatus1() throws Exception {
        InvoiceStatus invoiceStatus = delegate.getInvoiceStatus(11);
        assertEquals("Failed to get the status.", 11, invoiceStatus.getId());
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#getInvoiceStatus(String) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatus2() throws Exception {
        InvoiceStatus invoiceStatus = delegate.getInvoiceStatus("description");
        assertEquals("Failed to get the status.", 11, invoiceStatus.getId());
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#getAllInvoiceStatus() for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoiceStatus() throws Exception {
        InvoiceStatus[] invoiceStatuses = delegate.getAllInvoiceStatus();
        assertEquals("Failed to get all the status.", 11, invoiceStatuses[0].getId());
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#addInvoice(Invoice,boolean) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        delegate.addInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, delegate.getInvoice(invoice.getId()));
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#updateInvoice(Invoice,boolean) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        delegate.addInvoice(invoice, false);

        invoice.setInvoiceNumber("new");
        delegate.updateInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, delegate.getInvoice(invoice.getId()));
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#getInvoice(long) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        delegate.addInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, delegate.getInvoice(invoice.getId()));
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#searchInvoices(Filter,InvoiceSearchDepth) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoices() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        delegate.addInvoice(invoice, false);

        Invoice[] invoices =
            delegate.searchInvoices(InformixInvoiceFilterFactory.createInvoiceNumberFilter("invoiceNumber"),
                InvoiceSearchDepth.INVOICE_ONLY);
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#getAllInvoices() for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoices() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        delegate.addInvoice(invoice, false);

        AccuracyTestHelper.assertInvoices(invoice, delegate.getAllInvoices()[0]);
    }

    /**
     * <p>
     * Tests InvoiceSessionBean#canCreateInvoice(long) for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCanCreateInvoice() throws Exception {
        Invoice invoice = AccuracyTestHelper.createInvoice();
        delegate.addInvoice(invoice, false);

        assertTrue("Failed to return the value.", delegate.canCreateInvoice(8));
    }

}