/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a default implementation of the UserTypeManager interface. It utilizes instances of the UserTypeDAO in
 * order to fulfill the necessary CRUDE and search operations defined for the Time Tracker User component.
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
public class UserTypeManagerImpl implements UserTypeManager {

    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and modify the persistent store when dealing
     * with the Time Tracker User Type data.
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
     * Valid Values: Non-Null UserTypeDAO instances.
     * </p>
     */
    private UserTypeDAO userTypeDao;

    /**
     * <p>
     * The constructor that receives <code>UserTypeDAO</code> as parameter.
     * </p>
     *
     * @param userTypeDao
     *            the given UserTypeDAO
     * @throws IllegalArgumentException
     *             if the userTypeDao is null
     */
    public UserTypeManagerImpl(UserTypeDAO userTypeDao) {
        Util.checkNull(userTypeDao, "userTypeDao");

        this.userTypeDao = userTypeDao;
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
     *             if type is null or has id > 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void createUserType(UserType userType) throws DataAccessException {
        UserType[] userTypes = new UserType[] {userType};
        try {
            addUserTypes(userTypes);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to create the user type.");
        }
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
     *             if type is null or has id <=0
     * @throws UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void updateUserType(UserType userType) throws DataAccessException {
        UserType[] userTypes = new UserType[] {userType};
        try {
            updateUserTypes(userTypes);
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to update the user type.");
        }
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
     * @throws UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             if typeId <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void removeUserType(long userTypeId) throws DataAccessException {
        try {
            userTypeDao.removeUserTypes(new long[] {userTypeId});
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to remove the user type.");
        }
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
     * @throws IllegalArgumentException
     *             if userTypeId <= 0
     * @throws UnrecognizedEntityException
     *             if a type with the provided id was not found in the data store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType getUserType(long userTypeId) throws DataAccessException {
        if (userTypeId <= 0) {
            throw new IllegalArgumentException("userTypeId is not positive");
        }
        try {
            UserType[] userTypes = userTypeDao.getUserTypes(new long[] {userTypeId});
            return userTypes[0];
        } catch (BatchOperationException e) {
            throw Util.convertBatchExceptionToSingleException(e, "Failed to get the user type.");
        }
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
        return userTypeDao.searchUserTypes(filter);
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
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserTypes(UserType[] userTypes) throws DataAccessException {
        Util.updateDates(userTypes, true);
        userTypeDao.addUserTypes(userTypes);
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
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserTypes(UserType[] userTypes) throws DataAccessException {
        Util.updateDates(userTypes, true);
        userTypeDao.updateUserTypes(userTypes);
    }

    /**
     * <p>
     * This is a batch version of the removeUserType method.
     * </p>
     *
     * @param userTypeIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypeIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void removeUserTypes(long[] userTypeIds) throws DataAccessException {
        userTypeDao.removeUserTypes(userTypeIds);
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
     *             if userTypeIds is null or contains values <= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public UserType[] getUserTypes(long[] userTypeIds) throws DataAccessException {
        return userTypeDao.getUserTypes(userTypeIds);
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
        return userTypeDao.getAllUserTypes();
    }
}
