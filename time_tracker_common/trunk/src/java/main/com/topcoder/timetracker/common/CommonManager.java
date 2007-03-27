/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This interface defines the contract to manage the <code>PaymentTerm</code>s in the persistence for
 * Time Tracker Application.
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
 *   <li>Enumerate recently created <code>PaymentTerm</code>s by configured recent days. The recent days must be
 *   positive int value or equal -1.</li>
 *   <li>Enumerate recently modified <code>PaymentTerm</code>s by specified recent days. The recent days must be
 *   positive int value or equal -1.</li>
 *   <li>Enumerate recently modified <code>PaymentTerm</code>s by configured recent days. The recent days must be
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
public interface CommonManager {
    /**
     * <p>
     * Add the given <code>PaymentTerm</code> into the data store. The previous values of creation date and modification
     * date of the give <code>PaymentTerm</code> should be ignored and should be set to current date.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  The validation behavior depends on the concrete implementation.
     *  For current implementation {@link SimpleCommonManager}, validation will be performed on given
     *  <code>PaymentTerm</code> first, and <code>IllegalArgumentException</code> is thrown:
     *  <ul>
     *   <li>if the given <code>PaymentTerm</code> is null;</li>
     *   <li>or its creation user is null, empty string or length greater than 64;</li>
     *   <li>or its modification user is not null but is empty or length greater than 64;</li>
     *   <li>or its description is null, empty or length greater than 64;</li>
     *   <li>or its term is not positive.</li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  Some fields of given <code>PaymentTerm</code> have values altered through calling this method:
     *  <ul>
     *   <li>
     *   The creation date and modification date of given <code>PaymentTerm</code> should both be set to current date
     *   whatever the previous dates are. And this is what the current implementation {@link SimpleCommonManager} does.
     *   </li>
     *   <li>
     *   If modification user of given <code>PaymentTerm</code> is null, it should be set to creation user value.
     *   And this is what the current implementation {@link SimpleCommonManager} does.
     *   </li>
     *   <li>
     *   The unique identifier of given <code>PaymentTerm</code> should be set to some unique id on the data store
     *   perspective. Current implementation {@link SimpleCommonManager} use <code>IDGenerator</code> to generate
     *   the unique id.
     *   </li>
     *   <li>
     *   After success adding, the changed status of given <code>PaymentTerm</code> should be set as false.
     *   And this is what the current implementation {@link SimpleCommonManager} does.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to add.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is not valid.
     * @throws CommonManagementException if <code>PaymentTerm</code> already exists or any other error occurs
     *         during the execution.
     */
    void addPaymentTerm(PaymentTerm paymentTerm) throws CommonManagementException;

    /**
     * <p>
     * Update the given <code>PaymentTerm</code> within the data store. The previous value of modification
     * date of the give <code>PaymentTerm</code> should be ignored and should be set to current date.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  The validation behavior depends on the concrete implementation.
     *  For current implementation {@link SimpleCommonManager}, validation will be performed on given
     *  <code>PaymentTerm</code> first, and <code>IllegalArgumentException</code> is thrown:
     *  <ul>
     *   <li>if the given <code>PaymentTerm</code> is null;</li>
     *   <li>or its id is not positive.</li>
     *   <li>or its creation date is null or &gt;= current date;</li>
     *   <li>or its creation user is null, empty string or length greater than 64;</li>
     *   <li>or its modification user is null, empty string or length greater than 64;</li>
     *   <li>or its description is null, empty string or length greater than 64;</li>
     *   <li>or its term is not positive.</li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  Some fields of given <code>PaymentTerm</code> have values altered through calling this method:
     *  <ul>
     *   <li>
     *   The modification date of given <code>PaymentTerm</code> should be set to current date whatever the previous
     *   date is. And this is what the current implementation {@link SimpleCommonManager} does.
     *   </li>
     *   <li>
     *   After successfully validating, if the changed status of given <code>PaymentTerm</code> is found to be false,
     *   this method should not update it again. And this is what the current implementation
     *   {@link SimpleCommonManager} does.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of given <code>PaymentTerm</code> should be set as false.
     *   And this is what the current implementation {@link SimpleCommonManager} does.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to update.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is invalid.
     * @throws CommonManagementException if PaymentTerm is not added yet or any other error occurs during
     *         the execution.
     */
    void updatePaymentTerm(PaymentTerm paymentTerm) throws CommonManagementException;

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If the corresponding <code>PaymentTerm</code> is not found, whether throw exception or return null
     *  depends on the concrete implementation. For current implementation {@link SimpleCommonManager}, null
     *  is returned in such case.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>PaymentTerm</code> should have the changed status set as false. And this is what current
     *  implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @param id the id of PaymentTerm to retrieve.
     *
     * @return the <code>PaymentTerm</code> corresponding to given id. May be null if nothing is found.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws CommonManagementException if any error occurs during the execution.
     */
    PaymentTerm retrievePaymentTerm(long id) throws CommonManagementException;

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code>s corresponding to given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If some ids are not found within the data store, whether simply ignore the missing ids or throw exceptions
     *      depends on the concrete implementations. For current implementation {@link SimpleCommonManager}, the
     *      missing ids will be ignored.
     *      </li>
     *      <li>
     *      If the given array contains duplicate ids, whether return duplicate <code>PaymentTerm</code>s or terminate
     *      the duplicates depends on the concrete implementations. For current implementation
     *      {@link SimpleCommonManager}, it will return duplication.
     *      </li>
     *      <li>
     *      If given ids array is empty or no <code>PaymentTerm</code>s are found in the data store, an empty array of
     *      <code>PaymentTerm</code> should be returned. And this is what the current implementation
     *      {@link SimpleCommonManager} does.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code> to be retrieved.  Can be empty.
     *
     * @return the array with <code>PaymentTerm</code> corresponding to given ids. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws CommonManagementException if error occurs during the execution.
     */
    PaymentTerm[] retrievePaymentTerms(long[] ids) throws CommonManagementException;

    /**
     * <p>
     * Return an array with all the <code>PaymentTerm</code>s within the datastore.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code> exist in data store, an empty array should be returned.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @return the array with all the <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws CommonManagementException if error occurs during the execution.
     */
    PaymentTerm[] retrieveAllPaymentTerms() throws CommonManagementException;

    /**
     * <p>
     * Retrieve an array with all the active <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no active <code>PaymentTerm</code> is found, an empty array should be returned.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @return the array with all the active <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying
     *         exceptions.
     */
    PaymentTerm[] retrieveActivePaymentTerms() throws CommonManagementException;

    /**
     * <p>
     * Get an array with recently created <code>PaymentTerm</code>s with configured recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code>s are found within the range of configured recent days, an empty array should be
     *  returned. And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @return the array with recently created <code>PaymentTerm</code>s with configured recent days. May be empty, but
     *         not null.
     *
     * @throws CommonManagementException if error occurs during the execution.
     */
    PaymentTerm[] retrieveRecentlyCreatedPaymentTerms() throws CommonManagementException;

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
     *      And this is what the current implementation {@link SimpleCommonManager} does.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array should be
     *      returned. And this is what the current implementation {@link SimpleCommonManager} does.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently created PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws CommonManagementException if error occurs during the execution.
     */
    PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) throws CommonManagementException;

    /**
     * <p>
     * Get an array with recently modified <code>PaymentTerm</code>s with configured recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code>s are found within the range of configured recent days, an empty array should be
     *  returned. And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s should have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @return the array with recently modified PaymentTerms with configured recent days. May be empty, but not null.
     *
     * @throws CommonManagementException if any error occurs during the execution.
     */
    PaymentTerm[] retrieveRecentlyModifiedPaymentTerms() throws CommonManagementException;

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
     *      And this is what the current implementation {@link SimpleCommonManager} does.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array should be
     *      returned. And this is what the current implementation {@link SimpleCommonManager} does.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently modified PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws CommonManagementException if error occurs during the execution.
     */
    PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) throws CommonManagementException;

    /**
     * <p>
     * Delete the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If this id doesn't exist in the persistence, <code>PaymentTermNotFoundException</code> should be raised.
     *  And this is what the current implementation {@link SimpleCommonManager} does.
     * </p>
     *
     * @param id the id of <code>PaymentTerm</code> to be deleted.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws CommonManagementException if error occurs during the execution.
     */
    void deletePaymentTerm(long id) throws CommonManagementException;

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
     *      {@link SimpleCommonManager} does.
     *      </li>
     *      <li>
     *      If any of the given ids doesn't exist in the persistence, <code>PaymentTermNotFoundException</code>
     *      should be raised. And this is what the current implementation {@link SimpleCommonManager} does.
     *      </li>
     *      <li>
     *      If users delete a <code>PaymentTerm</code> twice, that is, the given array contain duplicate ids,
     *      <code>PaymentTermNotFoundException</code> should be raised. And this is what the current implementation
     *      {@link SimpleCommonManager} does.
     *      </li>
     *  </ul>
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code>s to be deleted. Can be empty.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws CommonManagementException if any error occurs during the execution.
     */
    void deletePaymentTerms(long[] ids) throws CommonManagementException;

    /**
     * <p>
     * Delete all the <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * @throws CommonManagementException if error occurs during the execution.
     */
    void deleteAllPaymentTerms() throws CommonManagementException;
}
