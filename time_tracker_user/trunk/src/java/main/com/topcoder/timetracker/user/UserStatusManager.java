/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details involving a
 * UserStatus. CRUDE and search methods are provided to manage the UserStatuses inside a persistent store.
 * </p>
 * <p>
 * It is also possible to search the persistent store for statuses based on different search criteria.
 * </p>
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public interface UserStatusManager {

    /**
     * <p>
     * Defines a user status to be recognized within the persistent store managed by this utility. A unique user
     * status id will automatically be generated and assigned to the user status.
     * </p>
     *
     * @param userStatus
     *            The status for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if status is null or has id > 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUserStatus(UserStatus userStatus) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided UserStatus parameter.
     * </p>
     * <p>
     * The implementation will set the UserStatus' modification details to the current date. These modification
     * details will also reflect in the persistent store. The modification user is the responsibility of the
     * calling application.
     * </p>
     * <p>
     * The given entity should have an id specified (id &gt; 0), or else IllegalArgumentException will be thrown.
     * </p>
     *
     * @param userStatus
     *            The status for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if status is null or has id <=0
     * @throws UnrecognizedEntityException
     *             if a status with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUserStatus(UserStatus userStatus) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the user status with the specified id.
     * </p>
     * <p>
     * The given entity should have an id specified (id &gt; 0), or else IllegalArgumentException will be thrown.
     * </p>
     *
     * @param userStatusId
     *            The id of the status for which the operation should be performed.
     * @throws UnrecognizedEntityException
     *             if a status with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             if statusId <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUserStatus(long userStatusId) throws DataAccessException;

    /**
     * <p>
     * Retrieves a UserStatus object that reflects the data in the persistent store on the Time Tracker UserStatus
     * with the specified userStatusId.
     * </p>
     *
     * @param userStatusId
     *            The id of the status to retrieve.
     * @return The status with specified id.
     * @throws IllegalArgumentException
     *             if userStatusId <= 0
     * @throws UnrecognizedEntityException
     *             if a status with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus getUserStatus(long userStatusId) throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any user statuses that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the UserStatusFilterFactory or a composite Search Filters (such as AndFilter, OrFilter and NotFilter
     * from Search Builder component) that combines the filters created using UserStatusFilterFactory.
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
     * This is a batch version of the createUserStatus method.
     * </p>
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param userStatuses
     *            An array of user statuses for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values or has ids != 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserStatuses(UserStatus[] userStatuses) throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the updateUserStatus method.
     * </p>
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param userStatuses
     *            An array of user statuses for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values or has id <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserStatuses(UserStatus[] userStatuses) throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the removeUserStatus method.
     * </p>
     *
     * @param userStatusIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserStatuses(long[] userStatusIds) throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the getUserStatus method.
     * </p>
     *
     * @param userStatusIds
     *            An array of userStatusIds for which user status should be retrieved.
     * @return The UserStatuses corresponding to the provided ids.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserStatus[] getUserStatuses(long[] userStatusIds) throws DataAccessException;

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