/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage, and
 * searching of <code>UserStatus</code> data from a persistent store. It is also responsible for generating ids
 * for any entities within it's scope, whenever an id is required.
 * </p>
 * <p>
 * Thread Safety: Implementations are required to be thread-safe.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public interface UserStatusDAO {

    /**
     * <p>
     * Defines a set of user statuses to be recognized within the persistent store managed by this utility. A
     * unique user status id will automatically be generated and assigned to the user statuses.
     * </p>
     *
     * @param userStatuses
     *            An array of user statuses for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserStatuses(UserStatus[] userStatuses) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided UserStatuses parameter.
     * </p>
     *
     * @param userStatuses
     *            An array of user statuses for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserStatuses(UserStatus[] userStatuses) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the user status with the specified ids.
     * </p>
     *
     * @param userStatusIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserStatuses(long[] userStatusIds) throws DataAccessException;

    /**
     * <p>
     * Retrieves an array of <code>UserStatus</code> objects that reflects the data in the persistent store on
     * the <code>UserStatus</code> with the specified Ids.
     * </p>
     *
     * @param userStatusIds
     *            An array of userStatusIds for which time status should be retrieved.
     * @return The UserStatuses corresponding to the provided ids.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserStatus[] getUserStatuses(long[] userStatusIds) throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any user statuses that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the <code>UserStatusFilterFactory</code> or a composite Search Filters (such as AndFilter, OrFilter
     * and NotFilter from Search Builder component) that combines the filters created using
     * <code>UserStatusFilterFactory</code>.
     * </p>
     *
     * @param filter
     *            The filter used to search for statuses.
     * @return The statuses satisfying the conditions in the search filter.
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus[] searchUserStatuses(Filter filter) throws DataAccessException;

    /**
     * <p>
     * Retrieves all the <code>UserStatus</code>es that are currently in the persistent store.
     * </p>
     *
     * @return An array of user statuses retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus[] getAllUserStatuses() throws DataAccessException;
}
