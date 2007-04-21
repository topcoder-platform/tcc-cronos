/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.common.PaymentTerm;

/**
 * Mock for <code>CommonManager</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockCommonManager implements CommonManager {

    /**
     * Mock method.
     *
     * @param paymentTerm
     *            not used
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public void addPaymentTerm(PaymentTerm paymentTerm) throws CommonManagementException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public void deleteAllPaymentTerms() throws CommonManagementException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param id
     *            not used
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public void deletePaymentTerm(long id) throws CommonManagementException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param ids
     *            not used
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public void deletePaymentTerms(long[] ids) throws CommonManagementException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrieveActivePaymentTerms() throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrieveAllPaymentTerms() throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param id
     *            not used
     *
     * @return a PaymentTerm with suitable id
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm retrievePaymentTerm(long id) throws CommonManagementException {
        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(id);
        return paymentTerm;
    }

    /**
     * Mock method.
     *
     * @param ids
     *            not used
     *
     * @return null
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrievePaymentTerms(long[] ids) throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms() throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param recentDays
     *            not used
     *
     * @return null
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @return a PaymentTerm with suitable id
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms() throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param recentDays
     *            not used
     *
     * @return null
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) throws CommonManagementException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param paymentTerm
     *            not used
     *
     * @throws CommonManagementException
     *             not thrown
     */
    public void updatePaymentTerm(PaymentTerm paymentTerm) throws CommonManagementException {
        // nothing to do

    }

}
