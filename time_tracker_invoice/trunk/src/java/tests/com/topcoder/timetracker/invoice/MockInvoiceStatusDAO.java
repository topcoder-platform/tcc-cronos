/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import java.util.Date;

/**
 * Mock for <code>InvoiceStatusDAO</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockInvoiceStatusDAO implements InvoiceStatusDAO {

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws InvoiceDataAccessException
     *             not thrown
     */
    public InvoiceStatus[] getAllInvoiceStatus() throws InvoiceDataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param id
     *            not used
     *
     * @return null
     *
     * @throws InvoiceDataAccessException
     *             not thrown
     */
    public InvoiceStatus getInvoiceStatus(long id) throws InvoiceDataAccessException {
        return new InvoiceStatus(4, "descr", "tc", "tc", new Date(), new Date());
    }

    /**
     * Mock method.
     *
     * @param description
     *            not used
     *
     * @return null
     *
     * @throws InvoiceDataAccessException
     *             not thrown
     */
    public InvoiceStatus getInvoiceStatus(String description) throws InvoiceDataAccessException {
        return null;
    }

}
