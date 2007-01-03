/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.user;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines the necessary methods that a User DAO should support. Create, Retrieve, Update, Delete
 * and Enumerate (CRUDE) methods, and their respective batch-mode equivalents are specified. There is also a search
 * method that utilizes Filter classes from the Search Builder 1.2 component.
 * </p>
 * <p>
 * Thread Safety:
 * Implementations need not necessarily be thread safe. Each implementation should specify whether
 * it is thread-safe or not. The application should pick the correct implementation for it's requirements.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public interface UserDAO {

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Username. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_USERNAME = "search_username";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Password. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_PASSWORD = "search_password";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's First Name. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_FIRST_NAME = "search_first_name";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Last Name. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_LAST_NAME = "search_last_name";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Phone Number. Filters from the Search
     * Builder component may use this constant when building their search parameters. Implementations of the
     * UserDAO interface should be able to recognize search filters bearing the provided constant and adjust their
     * searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_PHONE_NUMBER = "search_phone_number";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Email Address. Filters from the Search
     * Builder component may use this constant when building their search parameters. Implementations of the
     * UserDAO interface should be able to recognize search filters bearing the provided constant and adjust their
     * searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_EMAIL = "search_email";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Street Address Line 1. Filters from the
     * Search Builder component may use this constant when building their search parameters. Implementations of the
     * UserDAO interface should be able to recognize search filters bearing the provided constant and adjust their
     * searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_STREET_ADDRESS1 = "search_street_address1";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Street Address Line 2. Filters from the
     * Search Builder component may use this constant when building their search parameters. Implementations of the
     * UserDAO interface should be able to recognize search filters bearing the provided constant and adjust their
     * searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_STREET_ADDRESS2 = "search_street_address2";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's City. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_CITY = "search_city";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's State. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_STATE = "search_state";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Zip Code. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the UserDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_ZIP_CODE = "search_zip_code";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Date of Creation.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the UserDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_CREATED_DATE = "search_created_date";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's Creator.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the UserDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_CREATED_USER = "search_created_user";

    /**
     * <p>
     * This is a constant for a search filter field name for the User's last Date of Modification.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the UserDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_MODIFICATION_DATE = "search_modification_date";

    /**
     * <p>
     * This is a constant for a search filter field name for the Administrative User (not necessarily the Time
     * Tracker User entity) that performed the Last Modification to the Time Tracker User.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the UserDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchUsers method.
     * </p>
     *
     */
    String SEARCH_MODIFICATION_USER = "search_modification_user";

    /**
     * <p>
     * Creates a datastore entry for the given user. An id is automatically generated by the DAO and assigned to
     * the user. The User is also considered to have been created by the specified username.
     * </p>
     *
     *
     *
     * @return The same user Object, with an assigned id.
     * @param user The user to create within the datastore.
     * @param username The username of the user responsible for creating the User entry within the datastore.
     * @throws IllegalArgumentException if the user or username is null, or if username is an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    User createUser(User user, String username) throws UserDAOException;

    /**
     * <p>
     * Retrieves a User from the datastore with the provided id. If no User with that id exists, then a null is
     * returned.
     * </p>
     *
     *
     *
     * @param id The id of the user to retrieve from the datastore.
     * @return the retrieved user or null, if not found.
     * @throws IllegalArgumentException if id is <=0
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    User retrieveUser(long id) throws UserDAOException;

    /**
     * <p>
     * Updates the given User in the data store. The User is considered to have been modified by the specified
     * username.
     * </p>
     *
     *
     *
     * @param user The User entity to modify.
     * @param username The username of the user responsible for performing the update.
     * @throws IllegalArgumentException if the user is null, or the username is null, or the username is an empty
     *         String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws UserNotFoundException if the Time Tracker User to update was not found in the data store.
     */
    void updateUser(User user, String username) throws UserDAOException, UserNotFoundException;

    /**
     * <p>
     * Removes the specified User from the data store.
     * </p>
     *
     *
     *
     * @param user The user to delete.
     * @throws IllegalArgumentException if the user is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws UserNotFoundException if the Time Tracker User to delete was not found in the data store.
     */
    void deleteUser(User user) throws UserDAOException, UserNotFoundException;

    /**
     * <p>
     * Enumerates all the Users that are present within the data store.
     * </p>
     *
     *
     *
     * @return A list of all the Users within the data store.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    User[] listUsers() throws UserDAOException;

    /**
     * <p>
     * Returns a list of all the users within the datastore that satisfy the filters that are provided. The filters
     * are defined using classes from the Search Builder v1.2 component and com.cronos.timetracker. common.search
     * package.
     * </p>
     *
     *
     *
     * @return A list of users that satisfy the search criterion.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     */
    User[] searchUsers(Filter filter) throws UserDAOException;

    /**
     * <p>
     * Creates a datastore entry for each of the given Users. An id is automatically generated by the DAO and
     * assigned to the user. The User is also considered to have been created by the specified username. While in
     * batch mode, the operation can be done atomically, or separately. If done atomically, then a failure at any
     * one of the specified entries will mean that the entire batch will be rolled back. Otherwise, only the user
     * where a failure occurred will be rolled back.
     * </p>
     *
     *
     *
     * @return The same User objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the User in the returned array corresponds to the index of the
     *         User in the method argument.
     * @param users The users to create.
     * @param username The username of the one responsible for creating the users.
     * @param atomicBatchMode Whether the users will be created in atomic batch mode or not.
     * @throws IllegalArgumentException if users is null, or username is null, or username is an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.Exception if a problem occurs while accessing the datastore.
     */
    User[] createUsers(User[] users, String username, boolean atomicBatchMode) throws UserDAOException,
            BatchUserDAOException;

    /**
     * <p>
     * Retrieves the users with the specified ids from the datastore.
     * </p>
     *
     *
     * @return the array of uesrs with given id.
     * @param ids The ids of the Users to retrieve.
     * @throws IllegalArgumentException if the ids is null.
     * @throws UserDAOException if database error occurs.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    User[] retrieveUsers(long[] ids) throws UserDAOException, BatchUserDAOException;

    /**
     * <p>
     * Updates the given Users in the data store. The companies are considered to have been modified by the
     * specified username. While in batch mode, the operation can be done atomically, or separately. If done
     * atomically, then a failure at any one of the specified entries will mean that the entire batch will be
     * rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * </p>
     *
     *
     *
     * @param users The Users to update.
     * @param username The username of the one responsible for modifying the users.
     * @param atomicBatchMode Whether the users will be created in atomic batch mode or not.
     * @throws IllegalArgumentException if the users is null, or the username is null or an empty String.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    void updateUsers(User[] users, String username, boolean atomicBatchMode) throws UserDAOException,
            BatchUserDAOException;

    /**
     * <p>
     * Deletes the specified Users from the data store. While in batch mode, the operation can be done atomically,
     * or separately. If done atomically, then a failure at any one of the specified entries will mean that the
     * entire batch will be rolled back. Otherwise, only the user where a failure occurred will be rolled back.
     * </p>
     *
     *
     *
     * @param users The users to delete.
     * @param atomicBatchMode Whether the users will be created in atomic batch mode or not.
     * @throws IllegalArgumentException if users is null.
     * @throws UserDAOException if a problem occurs while accessing the datastore.
     * @throws BatchUserDAOException if a problem occurs with multiple entities while processing them in batch
     *         mode.
     */
    void deleteUsers(User[] users, boolean atomicBatchMode) throws UserDAOException, BatchUserDAOException;
}
