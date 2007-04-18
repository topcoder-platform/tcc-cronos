/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceException;
import com.topcoder.timetracker.invoice.InvoiceManager;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;

/**
 * A mocked InvoiceManager class for testing.
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyInvoiceManager implements InvoiceManager {

    /**
     * Store the Invoice into Map.
     *
     * <p>
     * The key is the id (Long) and the value is the Invoice instance.
     * </p>
     */
    private Map invoiceMap = new HashMap();

    /**
     * Represents the number of Invoice instance to be added.
     */
    private static int count = 0;

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager #addInvoice(com.topcoder.timetracker.invoice.Invoice,
     *      boolean)
     */
    public void addInvoice(Invoice invoice, boolean arg1) throws InvoiceDataAccessException {
        count++;
        invoice.setId(count);
        this.invoiceMap.put(new Long(count), invoice);
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#updateInvoice(com.topcoder.timetracker.invoice.Invoice,
     *      boolean)
     */
    public void updateInvoice(Invoice invoice, boolean arg1) throws InvoiceUnrecognizedEntityException,
            InvoiceDataAccessException {
        this.invoiceMap.put(new Long(invoice.getId()), invoice);
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#getInvoice(long)
     */
    public Invoice getInvoice(long id) throws InvoiceUnrecognizedEntityException, InvoiceDataAccessException {
        return (Invoice) this.invoiceMap.get(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#searchInvoices(com.topcoder.search.builder.filter.Filter,
     *      com.topcoder.timetracker.invoice.InvoiceSearchDepth)
     */
    public Invoice[] searchInvoices(Filter arg0, InvoiceSearchDepth arg1) throws InvoiceDataAccessException {
        return new Invoice[0];
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#getAllInvoices()
     */
    public Invoice[] getAllInvoices() throws InvoiceDataAccessException {
        return (Invoice[]) this.invoiceMap.values().toArray(new Invoice[this.invoiceMap.size()]);
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#canCreateInvoice(long)
     */
    public boolean canCreateInvoice(long arg0) throws InvoiceDataAccessException, InvoiceUnrecognizedEntityException {
        return true;
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#getAllInvoiceStatus()
     */
    public InvoiceStatus[] getAllInvoiceStatus() throws InvoiceDataAccessException {
        Set set = new HashSet();

        Invoice[] all = this.getAllInvoices();

        for (int i = 0; i < all.length; i++) {
            set.add(all[i].getInvoiceStatus());
        }

        return (InvoiceStatus[]) set.toArray(new InvoiceStatus[set.size()]);
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#getInvoiceStatus(long)
     */
    public InvoiceStatus getInvoiceStatus(long id) throws InvoiceUnrecognizedEntityException,
            InvoiceDataAccessException {
        List list = new ArrayList();

        Invoice[] all = this.getAllInvoices();

        for (int i = 0; i < all.length; i++) {
            list.add(all[i].getInvoiceStatus());
        }

        for (int i = 0; i < list.size(); i++) {

            InvoiceStatus instance = (InvoiceStatus) list.get(i);

            if (instance.getId() == id) {
                return instance;
            }
        }

        return null;
    }

    /**
     * @see com.topcoder.timetracker.invoice.InvoiceManager#getInvoiceStatus(java.lang.String)
     */
    public InvoiceStatus getInvoiceStatus(String des) throws InvoiceDataAccessException, InvoiceException {
        List list = new ArrayList();

        Invoice[] all = this.getAllInvoices();

        for (int i = 0; i < all.length; i++) {
            list.add(all[i].getInvoiceStatus());
        }

        for (int i = 0; i < list.size(); i++) {

            InvoiceStatus instance = (InvoiceStatus) list.get(i);

            if (instance.getDescription().equals(des)) {
                return instance;
            }
        }

        return null;
    }

}