/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a default implementation of the <code>UserStatusManager</code> interface. It utilizes instances of the
 * <code>UserStatusDAO</code> in order to fulfill the necessary CRUDE and search operations defined for the Time
 * Tracker User component.
 * </p>
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementation. Since the DAO is required to be
 * thread-safe, this class is thread safe in those aspects.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusManagerImpl implements UserStatusManager {

    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and modify the persistent store when dealing
     * with the Time Tracker User Status data.
     * </p>
     * <p>
     * Initial Value: From the constructor
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: all public methods of this class.
     * </p>
     * <p>
     * Valid Values: Non-Null UserStatusDAO instances.
     * </p>
     */
    private UserStatusDAO userStatusDao;

    /**
     * <p>
     * Constructor with <code>UserStatusDAO</code> as the parameter.
     * </p>
     *
     * @param userStatusDao
     *            the given UserStatusDAO
     * @throws IllegalArgumentException
     *             if the userStatusDao is null
     */
    public UserStatusManagerImpl(UserStatusDAO userStatusDao) {
        Util.checkNull(userStatusDao, "userStatusDao");

        this.userStatusDao = userStatusDao;
    }

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
    public void createUserStatus(UserStatus userStatus) throws DataAccessException {
        UserStatus[] userStatuses = new UserStatus[] {userStatus};
        try {
            addUserStatuses(userStatuses);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to create the user status.");
        }
    }

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
    public void updateUserStatus(UserStatus userStatus) throws DataAccessException {
        UserStatus[] userStatuses = new UserStatus[] {userStatus};
        try {
            updateUserStatuses(userStatuses);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to update the user status.");
        }
    }

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
    public void removeUserStatus(long userStatusId) throws DataAccessException {
        try {
            userStatusDao.removeUserStatuses(new long[] {userStatusId});
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to remove the user status.");
        }
    }

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
    public UserStatus getUserStatus(long userStatusId) throws DataAccessException {
        if (userStatusId <= 0) {
            throw new IllegalArgumentException("userStatusId is not positive");
        }
        try {
            UserStatus[] userStatuses = userStatusDao.getUserStatuses(new long[] {userStatusId});
            return userStatuses[0];
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to get the user status.");
        }
    }

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
    public UserStatus[] searchUserStatuses(Filter filter) throws DataAccessException {
        return userStatusDao.searchUserStatuses(filter);
    }

    /**
     * <p>
     * This is a batch version of the createUserStatus method.
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
    public void addUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        Util.updateDates(userStatuses, true);
        userStatusDao.addUserStatuses(userStatuses);
    }

    /**
     * <p>
     * This is a batch version of the updateUserStatus method.
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
    public void updateUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        Util.updateDates(userStatuses, true);
        userStatusDao.updateUserStatuses(userStatuses);
    }

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
    public void removeUserStatuses(long[] userStatusIds) throws DataAccessException {
        userStatusDao.removeUserStatuses(userStatusIds);
    }

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
    public UserStatus[] getUserStatuses(long[] userStatusIds) throws DataAccessException {
        return userStatusDao.getUserStatuses(userStatusIds);
    }

    /**
     * <p>
     * Retrieves all the <code>UserStatus</code>es that are currently in the persistent store.
     * </p>
     *
     * @return An array of user statuses retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus[] getAllUserStatuses() throws DataAccessException {
        return userStatusDao.getAllUserStatuses();
    }
}
