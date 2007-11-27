/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.UserTypeManager;
import com.topcoder.timetracker.user.Util;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application. It is responsible for
 * looking up the local interface of the SessionBean for UserTypeManager, and delegating any calls to the bean.
 * </p>
 * <p>
 * Thread Safety: - This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeManagerDelegate implements UserTypeManager {

    /**
     * <p>
     * This is the local interface for the UserTypeManager business services. All business calls are delegated
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
     * Utilized In: All UserTypeManagerDelegate methods
     * </p>
     * <p>
     * Valid Values: Non-null UserTypeManager (after initialization)
     * </p>
     */
    private UserTypeManagerLocal local;

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
    public UserTypeManagerDelegate(String namespace) throws ConfigurationException {
        local =
            (UserTypeManagerLocal) Util.createLocalObject(namespace, UserTypeManagerLocalHome.class,
                UserTypeManagerLocal.class);
    }

    /**
     * <p>
     * Defines a user type to be recognized within the persistent store managed by this utility. A unique user type
     * id will automatically be generated and assigned to the user type.
     * </p>
     *
     * @param userType
     *            The type for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if type is null or has id != 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUserType(UserType userType) throws DataAccessException {
        local.createUserType(userType);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided UserType parameter.
     * </p>
     * <p>
     * The implementation will set the UserType' modification details to the current date. These modification
     * details will also reflect in the persistent store. The modification user is the responsibility of the
     * calling application.
     * </p>
     * <p>
     * The given entity should have an id specified (id &gt; 0), or else IllegalArgumentException will be thrown.
     * </p>
     *
     * @param userType
     *            The type for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if type is null or has id &lt;= 0
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUserType(UserType userType) throws DataAccessException {
        local.updateUserType(userType);
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the user type with the specified id.
     * </p>
     * <p>
     * The given entity should have an id specified (id &gt; 0), or else IllegalArgumentException will be thrown.
     * </p>
     *
     * @param userTypeId
     *            The id of the type for which the operation should be performed.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             if typeId <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUserType(long userTypeId) throws DataAccessException {
        local.removeUserType(userTypeId);
    }

    /**
     * <p>
     * Retrieves a UserType object that reflects the data in the persistent store on the Time Tracker UserType with
     * the specified userTypeId.
     * </p>
     *
     * @param userTypeId
     *            The id of the type to retrieve.
     * @return The type with specified id.
     * @throws com.topcoder.timetracker.user.UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType getUserType(long userTypeId) throws DataAccessException {
        return local.getUserType(userTypeId);
    }

    /**
     * <p>
     * Searches the persistent store for any user types that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the UserTypeFilterFactory or a composite Search Filters (such as AndFilter, OrFilter and NotFilter
     * from Search Builder component) that combines the filters created using UserTypeFilterFactory.
     * </p>
     *
     * @param filter
     *            The filter used to search for types.
     * @return The types satisfying the conditions in the search filter.
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] searchUserTypes(Filter filter) throws DataAccessException {
        return local.searchUserTypes(filter);
    }

    /**
     * <p>
     * This is a batch version of the createUserType method.
     * </p>
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values or has ids != 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserTypes(UserType[] userTypes) throws DataAccessException {
        local.addUserTypes(userTypes);
    }

    /**
     * <p>
     * This is a batch version of the updateUserType method.
     * </p>
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values or has id <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserTypes(UserType[] userTypes) throws DataAccessException {
        local.updateUserTypes(userTypes);
    }

    /**
     * <p>
     * This is a batch version of the removeUserType method.
     * </p>
     *
     * @param userTypeIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserTypes(long[] userTypeIds) throws DataAccessException {
        local.removeUserTypes(userTypeIds);
    }

    /**
     * <p>
     * This is a batch version of the getUserType method.
     * </p>
     *
     * @param userTypeIds
     *            An array of userTypeIds for which user type should be retrieved.
     * @return The UserTypes corresponding to the provided ids.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws com.topcoder.timetracker.user.BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserType[] getUserTypes(long[] userTypeIds) throws DataAccessException {
        return local.getUserTypes(userTypeIds);
    }

    /**
     * <p>
     * Retrieves all the UserTypes that are currently in the persistent store.
     * </p>
     *
     * @return An array of user types retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] getAllUserTypes() throws DataAccessException {
        return local.getAllUserTypes();
    }

}
