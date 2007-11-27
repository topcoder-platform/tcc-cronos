/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.UserStatusManager;
import com.topcoder.timetracker.user.Util;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application. It is responsible for
 * looking up the local interface of the SessionBean for UserStatusManager, and delegating any calls to the bean.
 * </p>
 * <p>
 * Thread Safety: - This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusManagerDelegate implements UserStatusManager {

    /**
     * <p>
     * This is the local interface for the UserStatusManager business services. All business calls are delegated
     * here.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not modified after initialization
     * </p>
     * <p>
     * Utilized In: All UserStatusManagerDelegate methods
     * </p>
     * <p>
     * Valid Values: Non-null UserStatusManager (after initialization)
     * </p>
     */
    private UserStatusManagerLocal local;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace
     *            The namespace to use.
     * @throws IllegalArgumentException
     *             if namespace is null or an empty String.
     * @throws ConfigurationException
     *             if a problem occurs while constructing the instance.
     */
    public UserStatusManagerDelegate(String namespace) throws ConfigurationException {
        local =
            (UserStatusManagerLocal) Util.createLocalObject(namespace, UserStatusManagerLocalHome.class,
                UserStatusManagerLocal.class);
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
     *             if status is null or has id != 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUserStatus(UserStatus userStatus) throws DataAccessException {
        local.createUserStatus(userStatus);
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
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a status with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUserStatus(UserStatus userStatus) throws DataAccessException {
        local.updateUserStatus(userStatus);
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
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a status with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             if statusId <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUserStatus(long userStatusId) throws DataAccessException {
        local.removeUserStatus(userStatusId);
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
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a status with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus getUserStatus(long userStatusId) throws DataAccessException {
        return local.getUserStatus(userStatusId);
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
        return local.searchUserStatuses(filter);
    }

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
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        local.addUserStatuses(userStatuses);
    }

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
     *
     * @throws IllegalArgumentException
     *             if userStatuses is null or contains null values or has id <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        local.updateUserStatuses(userStatuses);
    }

    /**
     * <p>
     * This is a batch version of the removeUserStatus method.
     * </p>
     *
     * @param userStatusIds
     *            An array of ids for which the operation should be performed.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a UserStatus with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             if userStatusIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserStatuses(long[] userStatusIds) throws DataAccessException {
        local.removeUserStatuses(userStatusIds);
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
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserStatus[] getUserStatuses(long[] userStatusIds) throws DataAccessException {
        return local.getUserStatuses(userStatusIds);
    }

    /**
     * <p>
     * Retrieves all the UserStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of user statuses retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserStatus[] getAllUserStatuses() throws DataAccessException {
        return local.getAllUserStatuses();
    }

}
