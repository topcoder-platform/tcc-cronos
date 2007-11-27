/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage, and
 * searching of UserType data from a persistent store. It is also responsible for generating ids for any entities
 * within it's scope, whenever an id is required.
 * </p>
 * <p>
 * Thread Safety: Implementations are required to be thread-safe.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public interface UserTypeDAO {

    /**
     * <p>
     * Defines a set of user types to be recognized within the persistent store managed by this utility. A unique
     * user type id will automatically be generated and assigned to the user types.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void addUserTypes(UserType[] userTypes) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided UserType parameter.
     * </p>
     *
     * @param userTypes
     *            An array of user types for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if userTypes is null or contains null values.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     */
    public void updateUserTypes(UserType[] userTypes) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the user types with the specified ids.
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
    public void removeUserTypes(long[] userTypeIds) throws DataAccessException;

    /**
     * <p>
     * Retrieves an array of <code>UserType</code> objects that reflects the data in the persistent store on the
     * <code>UserType</code> with the specified Ids.
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
    public UserType[] getUserTypes(long[] userTypeIds) throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any user types that satisfy the criteria that was specified in the
     * provided search filter. The provided filter should be created using either the filters that are created
     * using the <code>UserTypeFilterFactory</code> or a composite Search Filters (such as AndFilter, OrFilter
     * and NotFilter from Search Builder component) that combines the filters created using UserTypeFilterFactory.
     * </p>
     *
     * @param filter
     *            The filter used to search for type.
     * @return The types satisfying the conditions in the search filter.
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] searchUserTypes(Filter filter) throws DataAccessException;

    /**
     * <p>
     * Retrieves all the <code>UserType</code>s that are currently in the persistent store.
     * </p>
     *
     * @return An array of user types retrieved from the persistent store.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public UserType[] getAllUserTypes() throws DataAccessException;
}
