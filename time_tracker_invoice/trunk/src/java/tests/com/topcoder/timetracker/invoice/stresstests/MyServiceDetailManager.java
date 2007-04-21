/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager;
import com.topcoder.timetracker.invoice.servicedetail.TransactionCreationException;

/**
 * A mocked implementation for interface ServiceDetailManager.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MyServiceDetailManager implements ServiceDetailManager {
    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#addServiceDetail(com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail,
     *      boolean)
     */
    public void addServiceDetail(InvoiceServiceDetail arg0, boolean arg1) throws DataAccessException,
            TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#deleteServiceDetail(long, boolean)
     */
    public void deleteServiceDetail(long arg0, boolean arg1) throws DataAccessException, TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#deleteAllServiceDetails(boolean)
     */
    public void deleteAllServiceDetails(boolean arg0) throws DataAccessException, TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#updateServiceDetail(com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail,
     *      boolean)
     */
    public void updateServiceDetail(InvoiceServiceDetail arg0, boolean arg1) throws DataAccessException,
            TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#retrieveServiceDetail(long)
     */
    public InvoiceServiceDetail retrieveServiceDetail(long arg0) throws DataAccessException,
            TransactionCreationException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#retrieveServiceDetails(long)
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long arg0) throws DataAccessException,
            TransactionCreationException {
        return new InvoiceServiceDetail[0];
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#retrieveAllServiceDetails()
     */
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException,
            TransactionCreationException {
        return new InvoiceServiceDetail[0];
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#addServiceDetails(com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail[],
     *      boolean)
     */
    public void addServiceDetails(InvoiceServiceDetail[] arg0, boolean arg1) throws DataAccessException,
            TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#deleteServiceDetails(long[], boolean)
     */
    public void deleteServiceDetails(long[] arg0, boolean arg1) throws DataAccessException,
            TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#updateServiceDetails(com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail[],
     *      boolean)
     */
    public void updateServiceDetails(InvoiceServiceDetail[] arg0, boolean arg1) throws DataAccessException,
            TransactionCreationException {
    }

    /**
     * @see com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager#retrieveServiceDetails(long[])
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] arg0) throws DataAccessException,
            TransactionCreationException {
        return new InvoiceServiceDetail[0];
    }
}