/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.common.PaymentTerm;

/**
 * A mocked implementation for testing.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MyCommonManager implements CommonManager {

    /**
     * Represents the payment term map.
     * <p>
     * The key is the id and the value is PaymentTerm instance.
     * </p>
     */
    private Map payment;

    /**
     * Constructor.
     *
     */
    public MyCommonManager() {
        payment = new HashMap();

        PaymentTerm term = new PaymentTerm();
        term.setActive(true);
        term.setCreationDate(new Date());
        term.setCreationUser("user");
        term.setModificationDate(new Date());
        term.setModificationUser("user");
        term.setDescription("des");
        term.setTerm(10);
        term.setId(1);

        payment.put(new Long(1), term);
    }

    /**
     * Add a PaymentTerm instance.
     */
    public void addPaymentTerm(PaymentTerm term) throws CommonManagementException {
        payment.put(new Long(term.getId()), term);
    }

    /**
     * Update the PaymentTerm instance.
     */
    public void updatePaymentTerm(PaymentTerm term) throws CommonManagementException {
        payment.put(new Long(term.getId()), term);
    }

    /**
     * Get the PaymentTerm instance.
     */
    public PaymentTerm retrievePaymentTerm(long id) throws CommonManagementException {
        return (PaymentTerm) payment.get(new Long(id));
    }

    /**
     * Get the PaymentTerm instance with lds.
     */
    public PaymentTerm[] retrievePaymentTerms(long[] ids) throws CommonManagementException {
        List list = new ArrayList();

        for (int i = 0; i < ids.length; i++) {
            list.add(payment.get(new Long(ids[i])));
        }

        return (PaymentTerm[]) list.toArray(new PaymentTerm[list.size()]);
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#retrieveAllPaymentTerms()
     */
    public PaymentTerm[] retrieveAllPaymentTerms() throws CommonManagementException {
        return new PaymentTerm[0];
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#retrieveActivePaymentTerms()
     */
    public PaymentTerm[] retrieveActivePaymentTerms() throws CommonManagementException {
        return new PaymentTerm[0];
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#retrieveRecentlyCreatedPaymentTerms()
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms() throws CommonManagementException {
        return new PaymentTerm[0];
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#retrieveRecentlyCreatedPaymentTerms(int)
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int arg0) throws CommonManagementException {
        return new PaymentTerm[0];
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#retrieveRecentlyModifiedPaymentTerms()
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms() throws CommonManagementException {
        return new PaymentTerm[0];
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#retrieveRecentlyModifiedPaymentTerms(int)
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int arg0) throws CommonManagementException {
        return new PaymentTerm[0];
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#deletePaymentTerm(long)
     */
    public void deletePaymentTerm(long arg0) throws CommonManagementException {
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#deletePaymentTerms(long[])
     */
    public void deletePaymentTerms(long[] arg0) throws CommonManagementException {
    }

    /**
     * @see com.topcoder.timetracker.common.CommonManager#deleteAllPaymentTerms()
     */
    public void deleteAllPaymentTerms() throws CommonManagementException {
        payment.clear();
    }
}