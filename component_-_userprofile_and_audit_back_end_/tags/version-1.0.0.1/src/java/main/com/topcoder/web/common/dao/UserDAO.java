/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao;

import java.util.List;

import com.topcoder.web.common.model.User;

/**
 * <p>
 * This interface represents a DAO for User entities. It defines methods for searching user by ID, handle, email, first
 * name and/or last name, and also methods for saving user to persistence and checking whether user handle can be
 * changed.
 * </p>
 * <p>
 * <strong>Changes:</strong>
 * <li>Specified generic parameter for List return type.</li>
 * <li>Added argument validation requirement for all methods.</li>
 * <li>Added methods for searching user by email and checking whether the user handle can be changed.</li>
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when entities passed
 * to them are used by the caller in thread safe manner.
 * </p>
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface UserDAO {
    /**
     * <p>
     * Retrieves the user with the specified ID.
     * </p>
     * <p>
     * <strong>Changes:</strong> Throws IllegalArgumentException if id is null.
     * </p>
     * @param id
     *            the ID of the user to be retrieved
     * @return the user with the specified ID (null if not found)
     * @throws IllegalArgumentException
     *             if id is null
     */
    public User find(Long id);

    /**
     * <p>
     * Retrieves the user with the specified user name. This method matches both active and inactive users.
     * </p>
     * <p>
     * <strong>Changes:</strong> Throws IllegalArgumentException if userName is null/empty.
     * </p>
     * @param userName
     *            the user name (handle)
     * @param ignoreCase
     *            true if case insensitive comparison must be used, false otherwise
     * @return the matched user (null if not found)
     * @throws IllegalArgumentException
     *             if userName is null/empty
     */
    public User find(String userName, boolean ignoreCase);

    /**
     * <p>
     * Retrieves the user with the specified user name. If activeRequired is true, only active user is matched.
     * </p>
     * <p>
     * <strong>Changes:</strong>
     * <li>Using StringBuilder instead of StringBuffer for better performance.</li>
     * <li>Throws IllegalArgumentException if userName is null/empty.</li>
     * </p>
     * @param userName
     *            the user name (handle)
     * @param ignoreCase
     *            true if case insensitive comparison must be used, false otherwise
     * @param activeRequired
     *            true if only active user should be retrieved, false if both active and inactive users should be
     *            checked
     * @return the matched user (null if not found)
     * @throws IllegalArgumentException
     *             if userName is null/empty
     */
    public User find(String userName, boolean ignoreCase, boolean activeRequired);

    /**
     * <p>
     * Retrieves the users with the specified handle, first name, last name and primary email address. All parameters
     * are optional. Case insensitive comparison is used. If none found, an empty list is returned.
     * </p>
     * <p>
     * <strong>Changes:</strong>
     * <li>Specified generic parameter for the return type.</li>
     * <li>Updated Criteria to address properties moved from User to UserProfile.</li>
     * </p>
     * @param handle
     *            the handle (null/empty if should be ignored)
     * @param firstName
     *            the first name (null/empty if should be ignored)
     * @param lastName
     *            the last name (null/empty if should be ignored)
     * @param email
     *            the primary email address (null/empty if should be ignored)
     * @return the retrieved users that meet the specified criteria (not null, doesn't contain null)
     */
    public List<User> find(String handle, String firstName, String lastName, String email);

    /**
     * <p>
     * Saves or update the given user.
     * </p>
     * <p>
     * <strong>Changes:</strong>
     * <li>Renamed parameter "u" to "user".</li>
     * <li>Specified generic parameters for all generic types in the code.</li>
     * <li>Throws IllegalArgumentException if user is null.</li>
     * <li>Removed log.debug() calls.</li>
     * @param user
     *            the user entity to be saved to updated
     * @throws IllegalArgumentException
     *             if user is null
     */
    public void saveOrUpdate(User user);

    /**
     * <p>
     * Retrieves the user with the specified primary email address.
     * </p>
     * @param email
     *            the primary email address of the user
     * @return the user with the specified email (null if not found)
     * @throws IllegalArgumentException
     *             if email is null/empty
     */
    public User find(String email);

    /**
     * <p>
     * Checks whether the handle of the user can be changed. Currently change of handle is not allowed for all users,
     * thus this method always returns false.
     * </p>
     * @param handle
     *            the current handle of the user
     * @return true if the handle of the user can be changed, false otherwise (this implementation always returns false)
     * @throws IllegalArgumentException
     *             if handle is null/empty
     */
    public boolean canChangeHandle(String handle);
}
