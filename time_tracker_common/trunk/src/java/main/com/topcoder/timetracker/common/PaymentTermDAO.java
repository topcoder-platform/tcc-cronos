
package com.topcoder.timetracker.common;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.common.DuplicatePaymentTermException;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAOException;
import com.topcoder.timetracker.common.PaymentTermNotFoundException;
/**
 * <p><strong>Usage: </strong>This interface defines the necessary methods that a PaymentTerm DAO should support. Add, retrieve, update and delete methods are provided.</p>
 * <p><strong>Thread-safety:</strong> implementations of this interface should be thread-safe.</p>
 * 
 */
public interface PaymentTermDAO {
/**
 * <strong>Usage:</strong> Add the given PaymentTerm in the data store.
 * 
 * 
 * @param paymentTerm the PaymentTerm to add
 * @throws PaymentTermDAOException if any problem occurs while accessing the data store
 * @throws IllegalArgumentException  if the given PaymentTerm is null; creation user is null, empty string or length >64; description is null, empty or length >64; term <=0
 * @throws DuplicatePaymentTermException if PaymentTerm is already added
 */
    public void addPaymentTerm(PaymentTerm paymentTerm);
/**
 * <strong>Usage:</strong> Update the given PaymentTerm in the data store.
 * 
 * 
 * @param paymentTerm the PaymentTerm to update
 * @throws PaymentTermDAOException if PaymentTerm is not added yet or any other problem occurs while accessing the data store
 * @throws IllegalArgumentException  if the given PaymentTerm is null; Date and String attributes of PaymentTerm are null; String attributes are empty; description length >64; creation user length >64; modification user length >64; term <=0; id<=0
 * @throws PaymentTermNotFoundException if the PaymentTerm to update was not found in the data store
 */
    public void updatePaymentTerm(PaymentTerm paymentTerm);
/**
 * <strong>Usage: </strong>Retrieve the PaymentTerm corresponding to given id from the data store.
 * 
 * 
 * @return the PaymentTerm corresponding to given id
 * @param id the id of PaymentTerm to be retrieved
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if id<0
 * @throws PaymentTermNotFoundException if there is no PaymentTerm with the given id in the data store
 */
    public PaymentTerm retrievePaymentTerm(long id);
/**
 * <strong>Usage: </strong>Retrieve the PaymentTerms corresponding to given ids from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with PaymentTerms corresponding to given ids
 * @param ids the array with ids of PaymentTerms to be retrieved
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if ids contains negative values
 * @throws PaymentTermNotFoundException if there is no PaymentTerm like one of the given ids in the data store
 */
    public PaymentTerm[] retrievePaymentTerms(long[] ids);
/**
 * <p><strong>Usage: </strong>Returns an array with all the PaymentTerms within the datastore. If nothing is found, return zero length array.</p>
 * 
 * 
 * @return the arraw with all the PaymentTerms
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 */
    public PaymentTerm[] retrieveAllPaymentTerms();
/**
 * <strong>Usage: </strong>Retrieve an array with all the active PaymentTerms from the data store. If nothing is found, return zero length array.
 * 
 * 
 * @return the arraw with all the active PaymentTerms
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 */
    public PaymentTerm[] retrieveActivePaymentTerms();
/**
 * <strong>Usage:</strong> Get an array with recently created PaymentTerms with specified recent days from the data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with recently created PaymentTerms with specified recent days
 * @param recentDays the nubmer of recent days
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
 */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays);
/**
 * <strong>Usage:</strong> Get an array with recently modified PaymentTerms with specified recent days from the data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found, return zero length array.
 * 
 * 
 * @return the array with recently modified PaymentTerms with specified recent days
 * @param recentDays the nubmer of recent days
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
 */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays);
/**
 * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id from the data store
 * <p>.</p>
 * 
 * 
 * @param id the id of PaymentTerm to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if id<0
 * @throws PaymentTermNotFoundException if there is no PaymentTerm with the given id in the data store
 */
    public void deletePaymentTerm(long id);
/**
 * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id of the provided PaymentTerm from the data store.
 * 
 * 
 * @param paymentTerm the PaymentTerm to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if id of PaymentTerm<0
 * @throws PaymentTermNotFoundException if the PaymentTerm to delete was not found in the data store
 */
    public void deletePaymentTerm(PaymentTerm paymentTerm);
/**
 * <strong>Usage: </strong>Delete the PaymentTerms corresponding to the given ids from the data store.
 * 
 * 
 * @param ids the array with ids of PaymentTerms to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if ids contains negative values
 * @throws PaymentTermNotFoundException if there is no PaymentTerm like one of the given ids in the data store
 */
    public void deletePaymentTerms(long[] ids);
/**
 * <strong>Usage: </strong>Delete the PaymentTerms corresponding to ids of given PaymentTerms from the data store.
 * 
 * 
 * @param paymentTerm the array with PaymentTerms to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if ids of PaymentTerms contains negative values
 * @throws PaymentTermNotFoundException if there is no PaymentTerm like one from the given paymentTerms in the data store
 */
    public void deletePaymentTerms(PaymentTerm[] paymentTerm);
/**
 * <strong>Usage: </strong>Delete all the PaymentTerms from the data store.
 * 
 * 
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 */
    public void deleteAllPaymentTerms();
}


