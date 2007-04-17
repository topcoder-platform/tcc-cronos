/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.common.PaymentTerm;


/**
 * <p>
 * <strong>Usage:</strong> This interface defines the contract to manage the PaymentTerms in the persistence for Time
 * Tracker Application. Contains operations like add, update, retrieve, delete.
 * </p>
 * 
 * <p>
 * <strong>&nbsp;Thread-safety:</strong> implementations of this interface should be thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockCommonManager implements CommonManager {
    /**
     * <strong>Usage:</strong> Add the given PaymentTerm in the data store. Set the id, creation date, modification
     * date. If modification user is not set should be set to creation user value.
     *
     * @param paymentTerm the PaymentTerm to add
     *
     * @throws CommonManagementException if PaymentTerm already exists or any other error occurs during the execution.
     *         Wraps the underlying exceptions.
     * @throws IllegalArgumentException if the given PaymentTerm is null;
     */
    public void addPaymentTerm(PaymentTerm paymentTerm) {
    }

    /**
     * <strong>Usage:</strong> Update the given PaymentTerm in the data store. Should set the modification date.
     *
     * @param paymentTerm the PaymentTerm to update
     *
     * @throws CommonManagementException if PaymentTerm is not added yet or any other error occurs during the
     *         execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if the given PaymentTerm is null;
     */
    public void updatePaymentTerm(PaymentTerm paymentTerm) {
    }

    /**
     * <strong>Usage: </strong>Retrieve the PaymentTerm corresponding to given id from the data store.
     *
     * @param id the id of PaymentTerm to be retrieved
     *
     * @return the PaymentTerm corresponding to given id
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if id
     */
    public PaymentTerm retrievePaymentTerm(long id) {
        PaymentTerm term = new PaymentTerm();
        term.setId(id);
        return term;
    }

    /**
     * <strong>Usage: </strong>Retrieve an array with the PaymentTerms corresponding to given ids from the data store.
     * If nothing is found, return zero length array.
     *
     * @param ids the array with ids of PaymentTerms to be retrieved
     *
     * @return the array with PaymentTerms corresponding to given ids
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if ids contains negative values
     */
    public PaymentTerm[] retrievePaymentTerms(long[] ids) {
        return null;
    }

    /**
     * <strong>Usage: </strong>Retrieve an array with all the PaymentTerms from the data store. If nothing is found,
     * return zero length array.
     *
     * @return the arraw with all the PaymentTerms
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     */
    public PaymentTerm[] retrieveAllPaymentTerms() {
        return null;
    }

    /**
     * <strong>Usage: </strong>Retrieve an array with all the active PaymentTerms from the data store. If nothing is
     * found, return zero length array.
     *
     * @return the arraw with all the active PaymentTerms
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     */
    public PaymentTerm[] retrieveActivePaymentTerms() {
        return null;
    }

    /**
     * <strong>Usage:</strong> Get an array with recently created PaymentTerms with configured recent days from the
     * data store. If nothing is found, return zero length array.
     *
     * @return the array with recently created PaymentTerms with configured recent days
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms() {
        return null;
    }

    /**
     * <strong>Usage:</strong> Get an array with recently created PaymentTerms with specified recent days from the data
     * store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found,
     * return zero length array.
     *
     * @param recentDays the nubmer of recent days
     *
     * @return the array with recently created PaymentTerms with specified recent days
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) {
        return null;
    }

    /**
     * <strong>Usage:</strong> Get an array with recently modified PaymentTerms with configured recent days from the
     * data store. If nothing is found, return zero length array.
     *
     * @return the array with recently modified PaymentTerms with configured recent days
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms() {
        return null;
    }

    /**
     * <strong>Usage:</strong> Get an array with recently modified PaymentTerms with specified recent days from the
     * data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is
     * found, return zero length array.
     *
     * @param recentDays the nubmer of recent days
     *
     * @return the array with recently modified PaymentTerms with specified recent days
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) {
        return null;
    }

    /**
     * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id from the data store
     * 
     * <p>
     * .
     * </p>
     *
     * @param id the id of PaymentTerm to be deleted
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if id
     */
    public void deletePaymentTerm(long id) {
    }

    /**
     * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id of the provided PaymentTerm from the
     * data store.
     *
     * @param paymentTerm the PaymentTerm to be deleted
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if id of PaymentTerm
     */
    public void deletePaymentTerm(PaymentTerm paymentTerm) {
    }

    /**
     * <strong>Usage: </strong>Delete the PaymentTerms corresponding to the given ids from the data store.
     *
     * @param ids the array with ids of PaymentTerms to be deleted
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if ids contains negative values
     */
    public void deletePaymentTerms(long[] ids) {
    }

    /**
     * <strong>Usage: </strong>Delete the PaymentTerms corresponding to ids of given PaymentTerms from the data store.
     *
     * @param paymentTerms the array with PaymentTerms to be deleted
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     * @throws IllegalArgumentException if ids of PaymentTerms contains negative values
     */
    public void deletePaymentTerms(PaymentTerm[] paymentTerms) {
    }

    /**
     * <strong>Usage: </strong>Delete all the PaymentTerms from the data store.
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
     */
    public void deleteAllPaymentTerms() {
    }
}
