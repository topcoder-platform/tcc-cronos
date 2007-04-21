/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval Time Tracker Invoice
 * Status data from a persistent store. It is also responsible for generating ids for any entities within it's
 * scope, whenever an id is required.
 * </p>
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public interface InvoiceStatusDAO {

    /**
     * <p>
     * Returns all invoice statuses that are currently in the persistent store.
     * </p>
     *
     * @return the invoices statuses contained in db, a void arrays if there aren't invoice statuses
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public InvoiceStatus[] getAllInvoiceStatus() throws InvoiceDataAccessException;

    /**
     * <p>
     * Returns the invoice status from id.
     * </p>
     *
     * @param id
     *            the id of invoice statuses from what is retrieved
     *
     * @return the invoice status that has the id used
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if an invoice status doesn't exist with the specified id
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public InvoiceStatus getInvoiceStatus(long id) throws InvoiceDataAccessException;

    /**
     * <p>
     * Return the invoice status from description. (It's assumed that it's unique for each invoice)
     * </p>
     *
     * @return the invoice status that has the description used
     * @param description
     *            the description of invoice status from what is retrieved
     * @throws InvoiceUnrecognizedEntityException
     *             if an invoice status doesn't exist with the specified description
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws IllegalArgumentException
     *             if description is null or empty.
     */
    public InvoiceStatus getInvoiceStatus(String description) throws InvoiceDataAccessException;
}
