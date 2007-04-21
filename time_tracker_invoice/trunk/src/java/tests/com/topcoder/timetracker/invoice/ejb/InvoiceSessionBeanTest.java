/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.ejb;

import java.math.BigDecimal;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.EjbBeanAccess;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceDAOFactory;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.delegate.MockUserTransaction;
import com.topcoder.timetracker.invoice.informix.TestHelper;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceSessionBean</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceSessionBeanTest extends TestCase {

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
        return new TestSuite(InvoiceSessionBeanTest.class);
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
     * Test <code>addInvoice</code> for failure. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddInvoice() throws Exception {
        try {
            invoiceSessionBean.addInvoice(null, true);
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
            invoiceSessionBean.updateInvoice(null, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateInvoice</code> for accuracy. Condition: invoice is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateInvoiceAccuracy() throws Exception {
        Invoice invoice = new Invoice();

        invoice.setId(4);
        invoice.setCreationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationDate(new Date());
        invoice.setModificationUser("tc");
        invoice.setCompanyId(5);
        invoice.setProjectId(8);
        invoice.setInvoiceNumber("invoiceNumber");
        invoice.setPaid(true);
        invoice.setDueDate(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setPurchaseOrderNumber("purchaseOrderNumber");
        invoice.setSalesTax(new BigDecimal(5));

        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        invoice.setExpenseEntries(expenseEntries);

        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(3);
        invoice.setPaymentTerm(paymentTerm);

        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        invoice.setFixedBillingEntries(fixedBillingEntries);

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        detail.setId(4);
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        invoice.setServiceDetails(serviceDetails);

        InvoiceStatus invoiceStatus = new InvoiceStatus(4, "description", "user", "user", new Date(), new Date());
        invoice.setInvoiceStatus(invoiceStatus);

        invoiceSessionBean.updateInvoice(invoice, true);

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
            invoiceSessionBean.getInvoice(7);
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
        Invoice invoice = invoiceSessionBean.getInvoice(3);

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
        Invoice[] allInvoices = invoiceSessionBean.getAllInvoices();

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
            invoiceSessionBean.canCreateInvoice(-1);
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
        assertEquals("The result is not as expected", true, invoiceSessionBean.canCreateInvoice(5));

    }

    /**
     * Test <code>getInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByIdAccuracy() throws Exception {
        InvoiceStatus invoiceStatus = invoiceSessionBean.getInvoiceStatus(1);

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
            invoiceSessionBean.getInvoiceStatus(7);
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
        InvoiceStatus invoiceStatus = invoiceSessionBean.getInvoiceStatus("testDescription");

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
            invoiceSessionBean.getInvoiceStatus("unrecognized");
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: the description is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByDescriptionNull() throws Exception {
        try {
            invoiceSessionBean.getInvoiceStatus(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
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
        InvoiceStatus[] invoiceStatuses = invoiceSessionBean.getAllInvoiceStatus();

        assertEquals("The returned numbers of invoice statuses is not correct", 5, invoiceStatuses.length);
    }

    /**
     * Test <code>searchInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceAccuracy1() throws Exception {
        Invoice[] i =
            invoiceSessionBean.searchInvoices(InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(1),
                InvoiceSearchDepth.INVOICE_ALL);
        assertEquals("the returned valus is not as expected", 0, i.length);

    }

    /**
     * Test <code>searchInvoice</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceAccuracy2() throws Exception {
        Invoice[] i =
            invoiceSessionBean.searchInvoices(InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(1),
                InvoiceSearchDepth.INVOICE_ONLY);

        assertEquals("the returned valus is not as expected", 1, i.length);
        assertEquals("the returned valus is not as expected", 1, i[0].getId());
    }

    /**
     * Test <code>searchInvoice</code> for failure. Condition: filter is null. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceFilterNull() throws Exception {
        try {
            invoiceSessionBean.searchInvoices(null, InvoiceSearchDepth.INVOICE_ALL);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>searchInvoice</code> for failure. Condition: depth is null. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchInvoiceDepthNull() throws Exception {
        try {
            invoiceSessionBean.searchInvoices(InformixInvoiceFilterFactory.createClientIdFilter(1), null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getSessionContext</code> for accuracy. Condition: normal. Expect: normal.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSessionContext() throws Exception {
        assertNotNull("The session context has to be set.", invoiceSessionBean.getSessionContext());
    }

}
