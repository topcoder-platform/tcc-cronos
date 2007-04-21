/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager;
import com.topcoder.timetracker.invoice.servicedetail.TransactionCreationException;

/**
 * Mock for <code>ServiceDetailManager</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockServiceDetail implements ServiceDetailManager {

    /**
     * Mock method.
     *
     * @param detail
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException,
        TransactionCreationException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param details
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void addServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException,
        TransactionCreationException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException, TransactionCreationException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param id
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void deleteServiceDetail(long id, boolean audit) throws DataAccessException,
        TransactionCreationException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param ids
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void deleteServiceDetails(long[] ids, boolean audit) throws DataAccessException,
        TransactionCreationException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @return array of one element service detail
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException,
        TransactionCreationException {
        return new InvoiceServiceDetail[0];
    }

    /**
     * Mock method.
     *
     * @param id
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public InvoiceServiceDetail retrieveServiceDetail(long id) throws DataAccessException,
        TransactionCreationException {

        return null;
    }

    /**
     * Mock method.
     *
     * @param invoiceId
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long invoiceId) throws DataAccessException,
        TransactionCreationException {

        return null;
    }

    /**
     * Mock method.
     *
     * @param ids
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] ids) throws DataAccessException,
        TransactionCreationException {

        return null;
    }

    /**
     * Mock method.
     *
     * @param detail
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void updateServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException,
        TransactionCreationException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param details
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws TransactionCreationException
     *             not thrown
     */
    public void updateServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException,
        TransactionCreationException {
        // nothing to do
    }

}
