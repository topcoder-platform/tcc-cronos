/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.EjbBeanAccess;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceDAOFactory;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.delegate.MockUserTransaction;
import com.topcoder.timetracker.invoice.informix.TestHelper;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceSessionBean</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceSessionBeanFailureTest extends TestCase {

    /** Unit under test. */
    private InvoiceSessionBean invoiceSessionBean;

    /** Context used in this unit test. */
    private Context context;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InvoiceSessionBeanFailureTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("config-InvoiceManagerDelegate-noInformixDAOFactory.xml");

        MockContextFactory.setAsInitial();

        context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // creates deployment descriptor of our sample bean. MockEjb does not support XML descriptors.
        SessionBeanDescriptor invoiceManagerLocalDescriptor =
            new SessionBeanDescriptor("invoiceSessionBean", InvoiceManagerLocalHome.class,
                InvoiceManagerLocal.class, InvoiceSessionBean.class);
        // Deploy operation simply creates Home and binds it to JNDI
        mockContainer.deploy(invoiceManagerLocalDescriptor);

        InvoiceManagerLocalHome sampleHome = (InvoiceManagerLocalHome) context.lookup("invoiceSessionBean");

        InvoiceManagerLocal sampleService = sampleHome.create();

        invoiceSessionBean = (InvoiceSessionBean) ((EjbBeanAccess) sampleService).getBean();

    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

        InvoiceDAOFactory.clear();
        TestHelper.clearNamespaces();
        super.tearDown();
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: namespace is not available. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoice() throws Exception {
        try {
            invoiceSessionBean.addInvoice(new Invoice(), true);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>updateInvoice</code> for failure. Condition: namespace is not available. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoice() throws Exception {
        try {
            invoiceSessionBean.updateInvoice(new Invoice(), true);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoice</code> for failure. Condition: namespace is not available. Expect:
     * <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoice() throws Exception {
        try {
            invoiceSessionBean.getInvoice(4);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllInvoices</code> for failure. Condition: namespace is not available. Expect:
     * <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoices() throws Exception {
        try {
            invoiceSessionBean.getAllInvoices();
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>canCreateInvoice</code> for failure. Condition: namespace is not available. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCanCreateInvoice() throws Exception {
        try {
            invoiceSessionBean.canCreateInvoice(1);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: namespace is not available. Expect:
     * <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatus() throws Exception {
        try {
            invoiceSessionBean.getInvoiceStatus(3);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllInvoiceStatus</code> for failure. Condition: namespace is not available. Expect:
     * <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoiceStatus() throws Exception {
        try {
            invoiceSessionBean.getAllInvoiceStatus();
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: namespace is not available. Expect:
     * <code>InvoiceDataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusDesc() throws Exception {
        try {
            invoiceSessionBean.getInvoiceStatus("testDescription");
            fail("InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>searchInvoice</code> for failure. Condition: namespace is not available. Expect: returned value
     * is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoice() throws Exception {
        try {
            invoiceSessionBean.searchInvoices(InformixInvoiceFilterFactory.createClientIdFilter(1),
                InvoiceSearchDepth.INVOICE_ALL);
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

}
