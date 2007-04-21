/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.delegate;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocal;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocalHome;
import com.topcoder.timetracker.invoice.ejb.InvoiceSessionBean;
import com.topcoder.timetracker.invoice.informix.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceManagerDelegate</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceManagerDelegateTest extends TestCase {

    /** Unit under test. */
    private InvoiceManagerDelegate invoiceManagerDelegate;

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
        return new TestSuite(InvoiceManagerDelegateTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("config-InvoiceManagerDelegate.xml");

        MockContextFactory.setAsInitial();

        context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // creates deployment descriptor of our sample bean. MockEjb does not support XML descriptors.
        SessionBeanDescriptor invoiceManagerLocalDescriptor =
            new SessionBeanDescriptor("invoiceManagerDelegate", InvoiceManagerLocalHome.class,
                InvoiceManagerLocal.class, InvoiceSessionBean.class);
        // Deploy operation simply creates Home and binds it to JNDI
        mockContainer.deploy(invoiceManagerLocalDescriptor);

        invoiceManagerDelegate =
            new InvoiceManagerDelegate("com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate");

    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: all states are updated as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInvoiceManagerDelegate() throws Exception {
        new InvoiceManagerDelegate("com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate");
    }

    /**
     * Test <code>addInvoice</code> for failure. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoice() throws Exception {
        try {
            invoiceManagerDelegate.addInvoice(null, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateInvoice</code> for failure. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoice() throws Exception {
        try {
            invoiceManagerDelegate.updateInvoice(null, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoice</code> for failure. Condition: invoice id is not in the database. Expect:
     * <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoice() throws Exception {
        try {
            invoiceManagerDelegate.getInvoice(7);
            fail("Should throw InvoiceUnrecognizedEntityException");
        } catch (InvoiceUnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceAccuracy() throws Exception {
        Invoice invoice = invoiceManagerDelegate.getInvoice(3);

        assertEquals("The returned value is not as expected", 3, invoice.getId());
        assertEquals("The returned value is not as expected", 3, invoice.getInvoiceStatus().getId());

    }

    /**
     * Test <code>getAllInvoices</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoices() throws Exception {
        Invoice[] allInvoices = invoiceManagerDelegate.getAllInvoices();

        assertEquals("The number of returned elements is not correct", 5, allInvoices.length);
    }

    /**
     * Test <code>canCreateInvoice</code> for failure. Condition: projectId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCanCreateInvoice() throws Exception {
        try {
            invoiceManagerDelegate.canCreateInvoice(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>canCreateInvoice</code> for accuracy. Condition: normal. Expect: everything is ok.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCanCreateInvoiceAccuracy() throws Exception {
        assertEquals("The result is not as expected", true, invoiceManagerDelegate.canCreateInvoice(5));

    }

    /**
     * Test <code>getInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByIdAccuracy() throws Exception {
        InvoiceStatus invoiceStatus = invoiceManagerDelegate.getInvoiceStatus(1);

        assertEquals("The id of the returned value is not as expected", 1, invoiceStatus.getId());
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: there is no such invoice status with given id.
     * Expect: <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByIdMissing() throws Exception {
        try {
            invoiceManagerDelegate.getInvoiceStatus(7);
            fail("Should throw InvoiceUnrecognizedEntityException");
        } catch (InvoiceUnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByDescriptionAccuracy() throws Exception {
        InvoiceStatus invoiceStatus = invoiceManagerDelegate.getInvoiceStatus("testDescription");

        assertEquals("The id of the returned value is not as expected", 1, invoiceStatus.getId());
        assertEquals("The description of the returned value is not as expected", "testDescription", invoiceStatus
            .getDescription());
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: there is no such invoice status with given
     * description. Expect: <code>InvoiceDataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByDescriptionMissing() throws Exception {
        try {
            invoiceManagerDelegate.getInvoiceStatus("unrecognized");
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoiceStatus() throws Exception {
        InvoiceStatus[] invoiceStatuses = invoiceManagerDelegate.getAllInvoiceStatus();

        assertEquals("The returned numbers of invoice statuses is not correct", 5, invoiceStatuses.length);
    }

}
