/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceManager;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;


/**
 * <p>
 * Defines a mock class which implements the <code>InvoiceManager</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockInvoiceManager implements InvoiceManager {
    /** Represents the flag which will make all the methods of this mock class always throw exception. */
    private boolean alwaysThrowException = false;

    /**
     * Creates a new MockInvoiceManager object.
     */
    public MockInvoiceManager() {
    }

    /**
     * <p>
     * Sets the flag which will make all the methods of this mock class always throw exception.
     * </p>
     */
    public void setAlwaysThrowException() {
        alwaysThrowException = true;
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param invoice ignored.
     * @param audit ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void addInvoice(Invoice invoice, boolean audit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param projectId ignored.
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public boolean canCreateInvoice(long projectId) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public InvoiceStatus[] getAllInvoiceStatus() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Invoice[] getAllInvoices() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Retrieves a Invoice object for given invoice id.
     * </p>
     *
     * @param invoiceId the invoice id.
     *
     * @return a Invoice object for given invoice id.
     *
     * @throws InvoiceDataAccessException if it is required to be thrown.
     */
    public Invoice getInvoice(long invoiceId) throws InvoiceDataAccessException {
        if (alwaysThrowException) {
            throw new InvoiceDataAccessException("InvoiceDataAccessException");
        }

        Invoice invoice = TestHelper.BuildInvoice();

        if (invoice.getId() == invoiceId) {
            return invoice;
        }

        return null;
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param id ignored.
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public InvoiceStatus getInvoiceStatus(long id) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param description ignored.
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public InvoiceStatus getInvoiceStatus(String description) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param filter ignored.
     * @param depth ignored.
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Invoice[] searchInvoices(Filter filter, InvoiceSearchDepth depth) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param invoice ignored.
     * @param audit ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void updateInvoice(Invoice invoice, boolean audit) {
        throw new UnsupportedOperationException("not implemented.");
    }
}
