/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;

import java.sql.Timestamp;


/**
 * <p>
 * This is a default implementation of the <code>FixedBillingStatusManager</code> interface. It utilizes instances of
 * the <code>FixedBillingStatusDAO</code> in order to fulfill the necessary CRUDE and search operations defined for
 * the Time Tracker FixedBillingStatuses.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementations Since the DAOs are required to be
 * thread-safe, this class is thread safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusManagerImpl implements FixedBillingStatusManager {
    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and  modify the persistent store when dealing with
     * the FixedBillingStatuses.
     * </p>
     */
    private final FixedBillingStatusDAO fixedBillingStatusDao;

    /**
     * <p>
     * Constructor accepting the necessary parameters for this implementation to work properly.
     * </p>
     *
     * @param fixedBillingStatusDao The FixedBillingStatusDAO to use.
     *
     * @throws IllegalArgumentException if fixedBillingStatusDao is null.
     */
    public FixedBillingStatusManagerImpl(FixedBillingStatusDAO fixedBillingStatusDao) {
        Helper.checkNull("fixedBillingStatusDao", fixedBillingStatusDao);
        this.fixedBillingStatusDao = fixedBillingStatusDao;
    }

    /**
     * <p>
     * Defines a FixedBillingStatus to be recognized within the persistent store managed by this utility. A unique
     * status id will automatically be generated and assigned to the status.
     * </p>
     *
     * <p>
     * The implementation will set the status' creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * @param status The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void createFixedBillingStatus(FixedBillingStatus status)
        throws DataAccessException {
        Helper.checkNull("status", status);

        Timestamp current = new Timestamp(System.currentTimeMillis());
        status.setModificationDate(current);
        status.setCreationDate(current);
        fixedBillingStatusDao.createFixedBillingStatuses(new FixedBillingStatus[] {status });
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided FixedBillingStatus parameter.
     * </p>
     *
     * <p>
     * The implementation will set the status' modification details to the current date.   These modification details
     * will also reflect in the persistent store. The creation and modification user is the responsibility of the
     * calling application.
     * </p>
     *
     * @param status The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateFixedBillingStatus(FixedBillingStatus status)
        throws DataAccessException {
        Helper.checkNull("status", status);
        status.setModificationDate(new Timestamp(System.currentTimeMillis()));
        fixedBillingStatusDao.updateFixedBillingStatuses(new FixedBillingStatus[] {status });
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the FixedBillingstatus with the specified
     * id.
     * </p>
     *
     * @param statusId The id of the status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statusId &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     */
    public void deleteFixedBillingStatus(long statusId)
        throws DataAccessException {
        fixedBillingStatusDao.deleteFixedBillingStatuses(new long[] {statusId });
    }

    /**
     * <p>
     * Retrieves a FixedBillingStatus object that reflects the data in the persistent store with the specified
     * statusId.
     * </p>
     *
     * @param statusId The id of the status to retrieve.
     *
     * @return The status with specified id.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus getFixedBillingStatus(long statusId)
        throws DataAccessException {
        return fixedBillingStatusDao.getFixedBillingStatuses(new long[] {statusId })[0];
    }

    /**
     * <p>
     * Searches the persistent store for any FixedBillingStatuses that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingStatusFilterFactory returned by getFixedBillingStatusEntryFilterFactory of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component) that
     * combines the filters created using FixedBillingStatusFilterFactory.
     * </p>
     *
     * @param criteria The filter used to search for statuses.
     *
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidFilterException if the filter cannot be recognized.
     */
    public FixedBillingStatus[] searchFixedBillingStatuses(Filter criteria)
        throws DataAccessException {
        return fixedBillingStatusDao.searchFixedBillingStatuses(criteria);
    }

    /**
     * <p>
     * This is a batch version of the createFixedBillingStatus method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public void createFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        Helper.checkObjectArray("statuses", statuses);

        Timestamp current = new Timestamp(System.currentTimeMillis());

        for (int i = 0; i < statuses.length; i++) {
            statuses[i].setModificationDate(current);
            statuses[i].setCreationDate(current);
        }

        fixedBillingStatusDao.createFixedBillingStatuses(statuses);
    }

    /**
     * <p>
     * This is a batch version of the updateFixedBillingStatus method.
     * </p>
     *
     * <p>
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of  statuses for which the operation should be performed.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if the operation is not atomic, and a problem occured for some (or all) of the
     *         operations.
     */
    public void updateFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        Helper.checkObjectArray("statuses", statuses);

        Timestamp current = new Timestamp(System.currentTimeMillis());

        for (int i = 0; i < statuses.length; i++) {
            statuses[i].setModificationDate(current);
        }

        fixedBillingStatusDao.updateFixedBillingStatuses(statuses);
    }

    /**
     * <p>
     * This is a batch version of the deleteFixedBillingStatus method.
     * </p>
     *
     * @param statusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteFixedBillingStatuses(long[] statusIds)
        throws DataAccessException {
        fixedBillingStatusDao.deleteFixedBillingStatuses(statusIds);
    }

    /**
     * <p>
     * This is a batch version of the getFixedBillingStatus  method.
     * </p>
     *
     * @param statusIds An array of statusIds for which time status should be retrieved.
     *
     * @return The statuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public FixedBillingStatus[] getFixedBillingStatuses(long[] statusIds)
        throws DataAccessException {
        return fixedBillingStatusDao.getFixedBillingStatuses(statusIds);
    }

    /**
     * <p>
     * Retrieves the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching
     * for FixedBillingStatuses.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingStatuses method.
     * </p>
     *
     * @return the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching for
     *         FixedBillingStatuses.
     */
    public FixedBillingStatusFilterFactory getFixedBillingStatusFilterFactory() {
        return fixedBillingStatusDao.getFixedBillingStatusFilterFactory();
    }

    /**
     * <p>
     * Retrieves all the FixedBillingStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of statuses retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus[] getAllFixedBillingStatuses()
        throws DataAccessException {
        return fixedBillingStatusDao.getAllFixedBillingStatuses();
    }
}
