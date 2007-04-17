
package com.topcoder.timetracker.common;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.PaymentTerm;
/**
 * <p><strong>Usage:</strong> This interface defines the contract to manage the PaymentTerms in the persistence for Time Tracker Application. Contains operations like add, update, retrieve, delete.</p>
 * <p><strong>&nbsp;Thread-safety:</strong> implementations of this interface should be thread-safe.</p>
 * 
 */
public interface CommonManager {
/**
 * <strong>Usage:</strong> Add the given PaymentTerm in the data store. Set the id, creation date, modification date. If modification user is not set should be set to creation user value.
 * 
 * 
 * @param paymentTerm the PaymentTerm to add
 * @throws CommonManagementException if PaymentTerm already exists or any other error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException  if the given PaymentTerm is null; creation user is null, empty string or length >64; description is null, empty or length >64; term <=0
 */
    public void addPaymentTerm(PaymentTerm paymentTerm);
/**
 * <strong>Usage:</strong> Update the given PaymentTerm in the data store. Should set the modification date.
 * 
 * 
 * @param paymentTerm the PaymentTerm to update
 * @throws CommonManagementException if PaymentTerm is not added yet or any other error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException  if the given PaymentTerm is null; Date and String attributes of PaymentTerm are null; String attributes are empty; description length >64; creation user length >64; modification user length >64; term <=0; id<=0
 */
    public void updatePaymentTerm(PaymentTerm paymentTerm);
/**
 * <strong>Usage: </strong>Retrieve the PaymentTerm corresponding to given id from the data store.
 * 
 * 
 * @return the PaymentTerm corresponding to given id
 * @param id the id of PaymentTerm to be retrieved
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if id<0
 */
    public PaymentTerm retrievePaymentTerm(long id) throws CommonManagementException ;
/**
 * <strong>Usage: </strong>Retrieve an array with the PaymentTerms corresponding to given ids from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with PaymentTerms corresponding to given ids
 * @param ids the array with ids of PaymentTerms to be retrieved
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if ids contains negative values
 */
    public PaymentTerm[] retrievePaymentTerms(long[] ids);
/**
 * <strong>Usage: </strong>Retrieve an array with all the PaymentTerms from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the arraw with all the PaymentTerms
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 */
    public PaymentTerm[] retrieveAllPaymentTerms();
/**
 * <strong>Usage: </strong>Retrieve an array with all the active PaymentTerms from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the arraw with all the active PaymentTerms
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 */
    public PaymentTerm[] retrieveActivePaymentTerms();
/**
 * <strong>Usage:</strong> Get an array with recently created PaymentTerms with configured recent days from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with recently created PaymentTerms with configured recent days
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms();
/**
 * <strong>Usage:</strong> Get an array with recently created PaymentTerms with specified recent days from the data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with recently created PaymentTerms with specified recent days
 * @param recentDays the nubmer of recent days
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
 */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays);
/**
 * <strong>Usage:</strong> Get an array with recently modified PaymentTerms with configured recent days from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with recently modified PaymentTerms with configured recent days
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms();
/**
 * <strong>Usage:</strong> Get an array with recently modified PaymentTerms with specified recent days from the data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with recently modified PaymentTerms with specified recent days
 * @param recentDays the nubmer of recent days
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
 */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays);
/**
 * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id from the data store
 * <p>.</p>
 * 
 * 
 * @param id the id of PaymentTerm to be deleted
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if id<0
 */
    public void deletePaymentTerm(long id);
/**
 * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id of the provided PaymentTerm from the data store.
 * 
 * 
 * @param paymentTerm the PaymentTerm to be deleted
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if id of PaymentTerm<0
 */
    public void deletePaymentTerm(PaymentTerm paymentTerm);
/**
 * <strong>Usage: </strong>Delete the PaymentTerms corresponding to the given ids from the data store.
 * 
 * 
 * @param ids the array with ids of PaymentTerms to be deleted
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if ids contains negative values
 */
    public void deletePaymentTerms(long[] ids);
/**
 * <strong>Usage: </strong>Delete the PaymentTerms corresponding to ids of given PaymentTerms from the data store.
 * 
 * 
 * @param paymentTerms the array with PaymentTerms to be deleted
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 * @throws IllegalArgumentException if ids of PaymentTerms contains negative values
 */
    public void deletePaymentTerms(PaymentTerm[] paymentTerms);
/**
 * <strong>Usage: </strong>Delete all the PaymentTerms from the data store.
 * 
 * 
 * @throws CommonManagementException if any error occurs during the execution. Wraps the underlying exceptions.
 */
    public void deleteAllPaymentTerms();
}


