/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate;
import com.topcoder.timetracker.invoice.delegate.MockUserTransaction;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocal;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocalHome;
import com.topcoder.timetracker.invoice.ejb.InvoiceSessionBean;
import com.topcoder.timetracker.invoice.informix.TestHelper;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

import junit.framework.TestCase;

/**
 * Demo of the component.
 *
 * @author enefem21
 * @version 1.0
 */
public class Demo extends TestCase {

    /** Context used in this unit test. */
    private Context context;

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
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        DBConnectionFactoryImpl connectionFactoryImpl =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection conn = connectionFactoryImpl.createConnection();
        Statement stat = conn.createStatement();
        stat.execute("delete from invoice where invoice_id > 5");

        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Demo.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // Create an InvoiceManagerDelegate
        InvoiceManager invoiceManager =
            new InvoiceManagerDelegate("com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate");

        // Create a new Invoice
        Invoice invoice = new Invoice();
        invoice.setCreationDate(new Date());
        invoice.setModificationDate(new Date());
        invoice.setCreationUser("tc");
        invoice.setModificationUser("tc");
        invoice.setCompanyId(7);
        invoice.setDueDate(new Date());
        invoice.setExpenseEntries(new ExpenseEntry[] {new ExpenseEntry()});
        invoice.setFixedBillingEntries(new FixedBillingEntry[] {new FixedBillingEntry()});
        invoice.setInvoiceDate(new Date());
        invoice.setInvoiceNumber("1067");

        // Set the status and retrieve the status from the description
        invoice.setInvoiceStatus(invoiceManager.getInvoiceStatus("testDescription"));
        invoice.setPaid(false);
        invoice.setPaymentTerm(new PaymentTerm());
        invoice.setProjectId(198);
        invoice.setPurchaseOrderNumber("1896");
        invoice.setSalesTax(new BigDecimal(2000));
        invoice.setServiceDetails(new InvoiceServiceDetail[] {new InvoiceServiceDetail()});

        // Store in persistence with auditing
        invoiceManager.addInvoice(invoice, true);

        // Search the Invoices with an invoice date within a given inclusive
        // date range and return only informations about Invoice object
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2006);
        Date from = calendar.getTime();
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        Date to = calendar.getTime();

        // Create the filter
        Filter invoiceDateFilter = InformixInvoiceFilterFactory.createInvoiceDateFilter(from, to);
        Invoice[] invoices = invoiceManager.searchInvoices(invoiceDateFilter, InvoiceSearchDepth.INVOICE_ONLY);

        // Look if is possible to create Invoice
        boolean canCreateInvoice = invoiceManager.canCreateInvoice(210);

        // Enumerate all invoices
        invoices = invoiceManager.getAllInvoices();

        // Retrieve an invoice by id
        invoice = invoiceManager.getInvoice(1);

        // Update an existing invoice
        invoiceManager.updateInvoice(invoice, true);

        // Enumerate all statuses
        InvoiceStatus[] invoiceStatuses = invoiceManager.getAllInvoiceStatus();
        // Retrieve an InvoiceStatus by id
        InvoiceStatus invoiceStatus = invoiceManager.getInvoiceStatus(5);

        // use the NOT,AND and OR filters for group several filters and search with them
        Filter filter =
            new AndFilter(InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(1),
                InformixInvoiceFilterFactory.createCompanyIdFilter(7));
        invoices = invoiceManager.searchInvoices(filter, InvoiceSearchDepth.INVOICE_ONLY);
        filter =
            new OrFilter(InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(1), InformixInvoiceFilterFactory
                .createCompanyIdFilter(7));
        invoices = invoiceManager.searchInvoices(filter, InvoiceSearchDepth.INVOICE_ONLY);
        filter = new NotFilter(InformixInvoiceFilterFactory.createCompanyIdFilter(7));
        invoices = invoiceManager.searchInvoices(filter, InvoiceSearchDepth.INVOICE_ONLY);

    }
}
