/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage, and
 * searching of Time Tracker Invoice data from a persistent store. It is also responsible for generating ids for
 * any entities within it's scope, whenever an id is required.
 * </p>
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public interface InvoiceDAO {

    /**
     * <p>
     * Adds the specified invoice into the persistent store. Unique invoice id will automatically be generated and
     * assigned to the invoice. There is also the option to perform an audit. If an audit is specified, then the
     * audit must be rolled back in the case that the operation fail.
     * </p>
     *
     * @param invoice
     *            invoice for which the operation should be performed
     * @param audit
     *            Indicates whether an audit should be performed
     *
     * @throws IllegalArgumentException
     *             if invoice is null
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void addInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Invoice. There is also the
     * option to perform an audit. If an audit is specified, then the audit must be rolled back in the case that
     * the operation fails.
     * </p>
     *
     * @param invoice
     *            invoice for which the operation should be performed
     * @param audit
     *            Indicates whether an audit should be performed
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if the invoices with the provided id was not found in the data store
     *
     * @throws IllegalArgumentException
     *             if invoice is null
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void updateInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException;

    /**
     * <p>
     * Retrieves an Invoice that reflects the data in the persistent store on the Time Tracker Invoice with the
     * specified invoice Id.
     * </p>
     *
     * @param invoiceId
     *            The id of the invoice to retrieve
     *
     * @return The invoice with specified id
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if a invoice with the provided id was not found in the data store
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice getInvoice(long invoiceId) throws InvoiceDataAccessException;

    /**
     * <p>
     * Searches the persistent store for any invoices that satisfy the criteria that was specified in the provided
     * search filter with the specified depth.
     * </p>
     *
     *
     *
     * @param filter
     *            The filter used to search for invoices
     * @param depth
     *            the mode of the invoices returned: INVOICE_ONLY: will return only invoices and no entries,
     *            INVOICE_ALL: will return the invoice and the Entries associated with the Invoice
     *
     * @return The invoices satisfying the conditions in the search filter
     *
     * @throws IllegalArgumentException
     *             if filter is null; if depth doesn't belong to the depths known
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice[] searchInvoices(Filter filter, InvoiceSearchDepth depth) throws InvoiceDataAccessException;

    /**
     * <p>
     * Retrieves all the invoices that are currently in the persistent store.
     * </p>
     *
     * @return An array of all invoices retrieved from the persistent store
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice[] getAllInvoices() throws InvoiceDataAccessException;

    /**
     * <p>
     * Looks at the particular project and check all entries (Time, Expense and Fixed billing) if there are any
     * that are PENDING then it will return false, else it will return true.
     * </p>
     *
     * @param projectId
     *            the project's id to check
     *
     * @return true if can create Invoice and false otherwise
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     * @throws InvoiceUnrecognizedEntityException
     *             if project with the provided id was not found in the data store
     * @throws IllegalArgumentException
     *             is projectId < 0
     */
    public boolean canCreateInvoice(long projectId) throws InvoiceDataAccessException;
}
