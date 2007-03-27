/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Date;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;

/**
 * <p>
 * This class implements the <code>CommonManager</code> interface and it uses the <code>DatabasePaymentTermDAO</code>
 * object to manage the <code>PaymentTerm</code> in the persistence, and it also have a configurable recentDays used
 * in the <code>retrieveRecentlyCreatedPaymentTerms()</code> and <code>retrieveRecentlyModifiedPaymentTerms</code>
 * methods. Contains operations like add, update, retrieve, delete.
 * </p>
 *
 * <p>
 *  <strong>Delegation:</strong>
 *  <ul>
 *   <li>
 *   The retrieve and delete operations are simply delegated to <code>DatabasePaymentTermDAO</code>.
 *   </li>
 *   <li>
 *   The add and update operations are delegated <code>DatabasePaymentTermDAO</code> after have set some necessary
 *   values on the given <code>PaymentTerm</code>.
 *   </li>
 *  </ul>
 * </p>
 *
 * <p>
 *  <strong>Sample Configuration:</strong>
 *  All properties are required and must have non-empty(trimmed) values.
 *  <pre>
 *  &lt;Config name="com.topcoder.timetracker.common.SimpleCommonManager"&gt;
 *       &lt;!-- namespace of ObjectFactory --&gt;
 *       &lt;Property name="of_namespace"&gt;
 *          &lt;Value&gt;ObjectFactoryNS&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;!-- key of PaymentTermDAO within ObjectFactory --&gt;
 *       &lt;Property name="payment_term_dao_key"&gt;
 *          &lt;Value&gt;PaymentTermDAOKey&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;!-- recent days, must be positive int value(which should not exceed 2147483647) or -1 --&gt;
 *       &lt;Property name="recent_days"&gt;
 *          &lt;Value&gt;3&lt;/Value&gt;
 *       &lt;/Property&gt;
 *   &lt;/Config&gt;
 *  </pre>
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  Thread safety of this class depends on the persistence layer. But implementations of <code>PaymentTermDAO</code>
 *  are required to be thread safe so thread safety should not be a concern here.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class SimpleCommonManager implements CommonManager {

    /**
     * <p>
     * The name of property which gives the namespace of {@link ObjectFactory}.
     * </p>
     */
    private static final String OF_NAMESPACE = "of_namespace";

    /**
     * <p>
     * The name of property which gives the key of {@link PaymentTerm} within the {@link ObjectFactory}.
     * </p>
     */
    private static final String PAYMENTTERMDAO_KEY = "payment_term_dao_key";

    /**
     * <p>
     * The name of property which gives the connection name within {@link DBConnectionFactory}.
     * </p>
     */
    private static final String RECENT_DAYS = "recent_days";

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represents the recent days used in the {@link this#retrieveRecentlyCreatedPaymentTerms()}
     *  and {@link this#retrieveRecentlyModifiedPaymentTerms()} methods.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Must be positive int value value or -1.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Initialized in the constructor.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final int recentDays;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represents the Data Access Object used to manage the {@link PaymentTerm} in the persistence.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Can not be null.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Initialized in the constructor. Used in all methods.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final PaymentTermDAO paymentTermDAO;

    /**
     * <p>
     * Constructor. Load the configuration values from default namespace
     * <b>"com.topcoder.timetracker.common.SimpleCommonManager"</b>.
     * </p>
     *
     * <p>
     * See class doc for sample configuration.
     * </p>
     *
     * @throws CommonManagerConfigurationException if any configured value is invalid or any required
     *         value is missing within the default namespace.
     *
     * @see SimpleCommonManager#SimpleCommonManager(String)
     */
    public SimpleCommonManager() throws CommonManagerConfigurationException {
        this(SimpleCommonManager.class.getName());
    }

    /**
     * <p>
     * Constructor to load configuration values from the given namespace.
     * </p>
     *
     * <p>
     * See class doc for sample configuration.
     * </p>
     *
     * @param namespace the namespace to load configuration values. Must not be null or empty(trimmed).
     *
     * @throws IllegalArgumentException if argument is null or empty(trimmed) string.
     * @throws CommonManagerConfigurationException if any configured value is invalid or any required value
     *         is missing within the given namespace.
     */
    public SimpleCommonManager(String namespace) throws CommonManagerConfigurationException {
        //Create PaymentTermDAO
        String paymentTermDaoKey = Helper.getPropertyValue(namespace, PAYMENTTERMDAO_KEY);
        try {
            this.paymentTermDAO = (PaymentTermDAO)
                (Helper.getObjectFactory(namespace, OF_NAMESPACE).createObject(paymentTermDaoKey));
        } catch (InvalidClassSpecificationException e) {
            throw new CommonManagerConfigurationException("Error occurs while instantiating PaymentTermDAO.", e);
        } catch (ClassCastException e) {
            throw new CommonManagerConfigurationException(
                    "The object created by ObjectFactory is not type of PaymentTermDAO.", e);
        }

        //Get recent days
        try {
            int recent = Integer.parseInt(Helper.getPropertyValue(namespace, RECENT_DAYS));
            Helper.validateRecentDays(recent);
            this.recentDays = recent;
        } catch (NumberFormatException e) {
            throw new CommonManagerConfigurationException("The recent days configured is not valid number format.", e);
        } catch (IllegalArgumentException e) {
            throw new CommonManagerConfigurationException(e.getMessage());
        }
    }

    /**
     * <p>
     * Constructor with a given <code>PaymentTermDAO</code> and the number of recent days.
     * </p>
     *
     * @param paymentTermDAO the PaymentTermDAO implementation that will be used
     * @param recentDays the number of recent days
     *
     * @throws IllegalArgumentException if the paymentTermDAO; or recentDays argument is not positive and not -1.
     */
    public SimpleCommonManager(PaymentTermDAO paymentTermDAO, int recentDays) {
        Helper.validateNotNull(paymentTermDAO, "The PaymentTermDAO used by SimpleCommonManager");
        Helper.validateRecentDays(recentDays);
        this.paymentTermDAO = paymentTermDAO;
        this.recentDays = recentDays;
    }

    /**
     * <p>
     * Add the given <code>PaymentTerm</code> into the data store. The previous values of creation date and modification
     * date of the give <code>PaymentTerm</code> will be ignored and will be set to current date.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  Validation will be performed on given <code>PaymentTerm</code> first,
     *  <code>IllegalArgumentException</code> is thrown:
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
     *  Some fields of given <code>PaymentTerm</code> will have values altered through calling this method:
     *  <ul>
     *   <li>
     *   If modification user of given <code>PaymentTerm</code> is null, it will be set to creation user value.
     *   </li>
     *   <li>
     *   The unique identifier of given <code>PaymentTerm</code> will be set to the id retrieved through
     *   <code>IDGenerator</code>.
     *   </li>
     *   <li>
     *   After success adding, the changed status of given <code>PaymentTerm</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to add.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is not valid.
     * @throws PaymentTermDAOException if any other problem occurs while accessing the data store.
     * @throws DuplicatePaymentTermException if <code>PaymentTerm</code> is already added.
     */
    public void addPaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {
        Helper.validatePaymentTerm(paymentTerm, "PaymentTerm to be added", false, false);

        //If the modification user has not been set, set it as the creation user.
        String modificationUser = paymentTerm.getModificationUser();
        if (modificationUser == null) {
            paymentTerm.setModificationUser(paymentTerm.getCreationUser());
        } else {
            Helper.validateStringWithMaxLength(modificationUser, "The modification user of PaymentTerm to be added");
        }

        //Sets the creation date and modification date to current date.
        Date date = new Date();
        paymentTerm.setCreationDate(date);
        paymentTerm.setModificationDate(date);

        this.paymentTermDAO.addPaymentTerm(paymentTerm);
    }

    /**
     * <p>
     * Update the given <code>PaymentTerm</code> within the data store. The previous value of modification
     * date of the give <code>PaymentTerm</code> will be ignored and will be set to current date.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  Validation will be performed on given <code>PaymentTerm</code> first,
     *  <code>IllegalArgumentException</code> is thrown:
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
     *  <strong>Changed status:</strong>
     *  <ul>
     *   <li>
     *   After successfully validating, if the changed status of given <code>PaymentTerm</code> is found to be false,
     *   this method will simply return.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of given <code>PaymentTerm</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm the <code>PaymentTerm</code> to update.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is not valid.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if the <code>PaymentTerm</code> to update was not found in the data store.
     */
    public void updatePaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {
        String usage = "PaymentTerm to be updated";
        Helper.validatePaymentTerm(paymentTerm, usage, true, true);

        Date currentDate = new Date();

        //Check creationDate < currentDate(which will be set as modificationDate)
        Helper.validateNotExceed(paymentTerm.getCreationDate(), currentDate, Boolean.FALSE,
                "The creation date of " + usage, "current date");


        //Set modification date as current date.
        paymentTerm.setModificationDate(currentDate);

        this.paymentTermDAO.updatePaymentTerm(paymentTerm);
    }

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  Null is returned if the corresponding <code>PaymentTerm</code> is not found.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>PaymentTerm</code>(if not null) will have the changed status set as false.
     * </p>
     *
     * @param id the id of PaymentTerm to retrieve.
     *
     * @return the <code>PaymentTerm</code> corresponding to given id. May be null if nothing is found.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm retrievePaymentTerm(long id) throws PaymentTermDAOException {
        Helper.validatePositive(id, "The id of PaymentTerm to retrieve");
        return this.paymentTermDAO.retrievePaymentTerm(id);
    }

    /**
     * <p>
     * Retrieve the <code>PaymentTerm</code>s corresponding to given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If some ids are not found within the data store, they will be simply ignored and this method continues to
     *      retrieve <code>PaymentTerm</code> for next id. So if some ids are not found, the returned array will
     *      have the length less than the length of the passed in array of ids.
     *      </li>
     *      <li>
     *      If the given array contains duplicate ids, the returned array of <code>PaymentTerm</code> will also contains
     *      duplication.
     *      </li>
     *      <li>
     *      If given ids array is empty or no <code>PaymentTerm</code>s are found in the data store, an empty array of
     *      <code>PaymentTerm</code> is returned.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @param ids the array with ids of <code>PaymentTerm</code> to be retrieved. Can be empty.
     *
     * @return the array with <code>PaymentTerm</code> corresponding to given ids. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the given array is null or contains non-positive values.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrievePaymentTerms(long[] ids) throws PaymentTermDAOException {
        if (!Helper.validateIdsArray(ids, "The id of PaymentTerm to retrieve")) {
            return new PaymentTerm[0];
        }
        return this.paymentTermDAO.retrievePaymentTerms(ids);
    }

    /**
     * <p>
     * Return an array with all the <code>PaymentTerm</code>s within the datastore.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code> exist in data store, an empty array is returned.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @return the array with all the <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveAllPaymentTerms() throws PaymentTermDAOException {
        return this.paymentTermDAO.retrieveAllPaymentTerms();
    }

    /**
     * <p>
     * Retrieve an array with all the active <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no active <code>PaymentTerm</code> is found, an empty array is returned.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @return the array with all the active <code>PaymentTerm</code>s. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store
     */
    public PaymentTerm[] retrieveActivePaymentTerms() throws PaymentTermDAOException {
        return this.paymentTermDAO.retrieveActivePaymentTerms();
    }

    /**
     * <p>
     * Get an array with recently created <code>PaymentTerm</code>s with configured recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code>s are found within the range of configured recent days, an empty array is
     *  returned.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @return the array with recently created <code>PaymentTerm</code>s with configured recent days. May be empty, but
     *         not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms() throws PaymentTermDAOException {
        return this.paymentTermDAO.retrieveRecentlyCreatedPaymentTerms(this.recentDays);
    }

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
     *      If given recent days is -1, all <code>PaymentTerm</code>s within data store will be returned.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array is returned.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently created PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) throws PaymentTermDAOException {
        Helper.validateRecentDays(recentDays);
        return this.paymentTermDAO.retrieveRecentlyCreatedPaymentTerms(recentDays);
    }

    /**
     * <p>
     * Get an array with recently modified <code>PaymentTerm</code>s with configured recent days from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If no <code>PaymentTerm</code>s are found within the range of configured recent days, an empty array is
     *  returned.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @return the array with recently modified PaymentTerms with configured recent days. May be empty, but not null.
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms() throws PaymentTermDAOException {
        return this.paymentTermDAO.retrieveRecentlyModifiedPaymentTerms(this.recentDays);
    }

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
     *      If given recent days is -1, all <code>PaymentTerm</code>s within data store will be returned.
     *      </li>
     *      <li>
     *      If no <code>PaymentTerm</code>s are found within the range of recent days, an empty array is returned.
     *      </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  All returned <code>PaymentTerm</code>s will have the changed status set as false.
     * </p>
     *
     * @param recentDays the number of recent days.
     *
     * @return the array with recently modified PaymentTerms with specified recent days. May be empty, but not null.
     *
     * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) throws PaymentTermDAOException {
        Helper.validateRecentDays(recentDays);
        return this.paymentTermDAO.retrieveRecentlyModifiedPaymentTerms(recentDays);
    }

    /**
     * <p>
     * Delete the <code>PaymentTerm</code> corresponding to given id from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If this id doesn't exist in the persistence, <code>PaymentTermNotFoundException</code> will be raised.
     * </p>
     *
     * @param id the id of <code>PaymentTerm</code> to be deleted.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     * @throws PaymentTermNotFoundException if there is no <code>PaymentTerm</code> with the given id in the data store.
     */
    public void deletePaymentTerm(long id) throws PaymentTermDAOException {
        this.deletePaymentTerms(new long[]{id});
    }

    /**
     * <p>
     * Delete the <code>PaymentTerm</code>s corresponding to the given ids from the data store.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *      <li>
     *      If the given array is empty, nothing happen.
     *      </li>
     *      <li>
     *      If any of the given ids doesn't exist in the persistence, <code>PaymentTermNotFoundException</code>
     *      will be raised.
     *      </li>
     *      <li>
     *      If you delete a <code>PaymentTerm</code> twice, that is, the given array contain duplicate ids,
     *      <code>PaymentTermNotFoundException</code> will be raised.
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
    public void deletePaymentTerms(long[] ids) throws PaymentTermDAOException {
        if (!Helper.validateIdsArray(ids, "The id of PaymentTerm to be deleted")) {
            return;
        }
        this.paymentTermDAO.deletePaymentTerms(ids);
    }

    /**
     * <p>
     * Delete all the <code>PaymentTerm</code>s from the data store.
     * </p>
     *
     * @throws PaymentTermDAOException if error occurs while accessing the data store.
     */
    public void deleteAllPaymentTerms() throws PaymentTermDAOException {
        this.paymentTermDAO.deleteAllPaymentTerms();
    }
}
