/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage, and
 * searching of Time Tracker User data from a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 *
 * <p>
 * Thread Safety : The implementations of this interface should be thread safe.
 * </p>
 *
 * @author ShindouHikaru, biotrail
 * @version 3.2
 */
public interface UserDAO {
    /**
     * <p>
     * Adds the specified users into the persistent store.
     * </p>
     *
     * <p>
     * Unique user ids will automatically be generated and assigned to the users.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * If transaction is required, then if any of the users fail to be added, then the entire batch operation will
     * be rolled back. Otherwise, the failure will occur only for those entities where an exception occurred.
     * </p>
     *
     * @param users
     *            An array of users for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required in the persistence
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void addUsers(User[] users, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Users array.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * If transaction is required, then if any of the users fail to be added, then the entire batch operation will
     * be rolled back. Otherwise, the failure will occur only for those entities where an exception occurred.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * @param user
     *            An array of users for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required in the persistence
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void updateUsers(User[] user, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the project with the specified user ids.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * If transaction is required, then if any of the users fail to be added, then the entire batch operation will
     * be rolled back. Otherwise, the failure will occur only for those entities where an exception occurred.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * @param userId
     *            An array of userIds for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void removeUsers(long[] userId, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Retrieves an array of <code>User</code> objects that reflects the data in the persistent store on the Time
     * Tracker User with the specified <code>userIds</code>.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * @param userIds
     *            An array of userIds for which the operation should be performed.
     * @return The users corresponding to the provided ids.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] getUsers(long[] userIds) throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any users that satisfy the criteria that was specified in the provided
     * search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>UserFilterFactory</code> returned by {@link UserDAO#getUserFilterFactory()}, or a composite Search
     * Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code> from Search
     * Builder component) that combines the filters created using <code>UserFilterFactory</code>.
     * </p>
     *
     * @param filter
     *            The filter used to search for users.
     * @return The users satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] searchUsers(Filter filter) throws DataAccessException;

    /**
     * <p>
     * Retrieves the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when
     * searching for users.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by the
     * given factory should be used with {@link UserDAO#searchUsers(Filter)}.
     * </p>
     *
     * @return the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when searching
     *         for users.
     */
    public UserFilterFactory getUserFilterFactory();

    /**
     * <p>
     * Retrieves all the users that are currently in the persistent store.
     * </p>
     *
     * @return An array of all users retrieved from the persistent store.
     *
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] getAllUsers() throws DataAccessException;
}
