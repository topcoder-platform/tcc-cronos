/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This interface defines the necessary methods and contracts that a <code>PaymentTerm</code> DAO should support.
 * </p>
 *
 * <p>
 *  <strong>Supported operations:</strong>
 *  <ul>
 *   <li>Add a <code>PaymentTerm</code> into data store.</li>
 *   <li>Update a <code>PaymentTerm</code> within data store.</li>
 *   <li>Enumerate all <code>PaymentTerm</code>s within data store.</li>
 *   <li>Enumerate <code>PaymentTerm</code>s within data store by ids.</li>
 *   <li>Enumerate active <code>PaymentTerm</code>s.</li>
 *   <li>Enumerate recently created <code>PaymentTerm</code>s by specified recent days. The recent days must be
 *   positive int value or equal -1.</li>
 *   <li>Enumerate recently modified <code>PaymentTerm</code>s by specified recent days. The recent days must be
 *   positive int value or equal -1.</li>
 *   <li>Delete <code>PaymentTerm</code>s from data store by ids.</li>
 *   <li>Delete all <code>PaymentTerm</code>s from data store.</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  implementations of this interface should be thread-safe.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public interface PaymentTermDAO {
    /**
     * <p>
     * Add the given <code>PaymentTerm</code> into the data store.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  The validation behavior depends on the concrete implementation.
     *  For current implementation <code>DatabasePaymentTermDAO</code>, validation will be performed on given
     *  <code>PaymentTerm</code> first, and <code>IllegalArgumentException</code> is thrown:
     *  <ul>
     *   <li>If the given <code>PaymentTerm</code> is null;</li>
     *      <li>If the creation user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the modification user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the description of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the creation date of given <code>PaymentTerm</code> is null or != modification date;</li>
     *      <li>If the modification date of given <code>PaymentTerm</code> is null or &gt; current date;</li>
     *      <li>If the term of given <code>PaymentTerm</code> is not positive.</li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  Some fields of given <code>PaymentTerm</code> may have values altered through calling this method:
     *  <ul>
     *   <li>
     *   The unique identifier of given <code>PaymentTerm</code> should be set to some unique id on the data store
     *   perspective. Current implementation <code>DatabasePaymentTermDAO</code> use <code>IDGenerator</code> to
     *   generate the unique id.
     *   </li>
     *   <li>
     *   After success adding, the changed status of given <code>PaymentTerm</code> should be set as false.
     *   And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to add.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is invalid.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws DuplicatePaymentTermException if given <code>PaymentTerm</code> is already added.
     */
    void addPaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException;

    /**
     * <p>
     * Update the given <code>PaymentTerm</code> in the data store.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  The validation behavior depends on the concrete implementation.
     *  For current implementation <code>DatabasePaymentTermDAO</code>, validation will be performed on given
     *  <code>PaymentTerm</code> first, and <code>IllegalArgumentException</code> is thrown:
     *  <ul>
     *      <li>If the given <code>PaymentTerm</code> is null;</li>
     *      <li>If the creation user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the modification user of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the description of given <code>PaymentTerm</code> is null, empty(trimmed), or with length
     *      greater than 64;</li>
     *      <li>If the creation date of given <code>PaymentTerm</code> is null
     *      or &gt;= modification date;</li>
     *      <li>If the modification date of given <code>PaymentTerm</code> is null
     *      or &gt; current date;</li>
     *      <li>If the term of given <code>PaymentTerm</code> is not positive.</li>
     *      <li>If the id of given <code>PaymentTerm</code> is not positive.</li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  <ul>
     *   <li>
     *   After successfully validating, if the changed status of given <code>PaymentTerm</code> is found to be false,
     *   this method should not update it again. And this is what the current implementation
     *   <code>DatabasePaymentTermDAO</code> does.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of given <code>PaymentTerm</code> should be set as false.
     *   And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to update.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is invalid.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if the given <code>PaymentTerm</code> to update was not found
     *         in the data store.
     */
    void updatePaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException;

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If the corresponding <code>PaymentTerm</code> is not found, null should be returned. And this is what the
     *  current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>PaymentTerm</code> should have the changed status set as false. And this is what current
     *  implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @param id The id of <code>PaymentTerm</code> to be retrieved.
     *
     * @return the <code>PaymentTerm</code> corresponding to given id. May be null.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    PaymentTerm retrievePaymentTerm(long id) throws PaymentTermDAOException;

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code>s corresponding to given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If some ids are not found within the data store, they should be simply ignored and continues to next id.
     *      And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *      <li>
     *      If the given array contains duplicate ids, duplicate <code>PaymentTerm</code>s should be returned. And this
     *      is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *      <li>
     *      If given ids array is empty or no <code>PaymentTerm</code>s are found in the data store, an empty array of
     *      <code>PaymentTerm</code> should be returned. And this is what the current implementation
     *      <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code> to be retrieved. Can be empty.
     *
     * @return the array with <code>PaymentTerm</code> corresponding to given ids. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    PaymentTerm[] retrievePaymentTerms(long[] ids) throws PaymentTermDAOException;

    /**
     * <p>
     * Return an array with all the <code>PaymentTerm</code>s within the datastore.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code> exist in data store, an empty array should be returned.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @return the array with all the <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    PaymentTerm[] retrieveAllPaymentTerms() throws PaymentTermDAOException;

    /**
     * <p>
     * Retrieve an array with all the active <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no active <code>PaymentTerm</code> is found, an empty array should be returned.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @return the array with all the active <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    PaymentTerm[] retrieveActivePaymentTerms() throws PaymentTermDAOException;

    /**
     * <p>
     * Get an array with recently created <code>PaymentTerm</code>s with specified recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  The given recent days must be a positive value or equal to -1,
     *  <ul>
     *      <li>
     *      If given recent days is -1, all <code>PaymentTerm</code>s within data store should be returned.
     *      And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array should be
     *      returned. And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently created PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) throws PaymentTermDAOException;

    /**
     * <p>
     * Get an array with recently modified <code>PaymentTerm</code>s with specified recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  The given recent days must be a positive value or equal to -1,
     *  <ul>
     *      <li>
     *      If given recent days is -1, all <code>PaymentTerm</code>s within data store should be returned.
     *      And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array should be
     *      returned. And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently modified PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) throws PaymentTermDAOException;

    /**
     *  <p>
     * Delete the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If this id doesn't exist in the persistence, <code>PaymentTermNotFoundException</code> should be raised.
     *  And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     * </p>
     *
     * @param id the id of <code>PaymentTerm</code> to be deleted.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if there is no <code>PaymentTerm</code> with the given id in the data store.
     */
    void deletePaymentTerm(long id) throws PaymentTermDAOException;

    /**
     * <p>
     * Delete the <code>PaymentTerm</code>s corresponding to the given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If the given array is empty, nothing should happen. And this is what the current implementation
     *      <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *      <li>
     *      If any of the given ids doesn't exist in the persistence, <code>PaymentTermNotFoundException</code>
     *      should be raised. And this is what the current implementation <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *      <li>
     *      If users delete a <code>PaymentTerm</code> twice, that is, the given array contain duplicate ids,
     *      <code>PaymentTermNotFoundException</code> should be raised. And this is what the current implementation
     *      <code>DatabasePaymentTermDAO</code> does.
     *      </li>
     *  </ul>
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code>s to be deleted. Can be empty.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if there is no <code>PaymentTerm</code> like one of the given ids in the
     *         data store.
     */
    void deletePaymentTerms(long[] ids) throws PaymentTermDAOException;

    /**
     * <p>
     * Delete all the <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    void deleteAllPaymentTerms() throws PaymentTermDAOException;
}
