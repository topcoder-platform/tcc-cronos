/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;

/**
 * <p>
 * This is a mock implementation of <code>UserRetrieval</code>.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class MockUserRetrieval implements UserRetrieval {

    /**
     * Constructs an instance of this class.
     */
    public MockUserRetrieval() {

    }

    /**
     * <p>
     * Retrieves the external user with the given id.
     * </p>
     * @param id
     *            the id of the user we are interested in.
     * @return the external user who has the given id, or null if not found.
     * @throws IllegalArgumentException
     *             if id is not positive.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying
     *             exception.
     */
    public ExternalUser retrieveUser(long id) throws RetrievalException {
        if (id > 100) {
            throw new RetrievalException("Error occurred.");
        }
        return new ExternalUserImpl(1, "tomek", "f", "l", "tomek@topcoder.com");
    }

    /**
     * <p>
     * Retrieves the external user with the given handle.
     * </p>
     * @param handle
     *            the handle of the user we are interested in.
     * @return the external user who has the given handle, or null if not found.
     * @throws IllegalArgumentException
     *             if handle is <code>null</code> or empty after trim.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying
     *             exception.
     */
    public ExternalUser retrieveUser(String handle) throws RetrievalException {
        return null;
    }

    /**
     * <p>
     * Retrieves the external users with the given ids.
     * </p>
     * @param ids
     *            the ids of the users we are interested in.
     * @return an array of external users who have the given ids. If none of the given ids were
     *         found, an empty array will be returned. The index of the entries in the array will
     *         not necessarily directly correspond to the entries in the ids array.
     * @throws IllegalArgumentException
     *             if ids is <code>null</code> or any entry is not positive.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying
     *             exception.
     */
    public ExternalUser[] retrieveUsers(long[] ids) throws RetrievalException {
        return null;
    }

    /**
     * <p>
     * Retrieves the external users with the given handles.
     * </p>
     * @param handles
     *            the handles of the users we are interested in.
     * @return an array of external users who have the given handles. If none of the given handles
     *         were found, an empty array will be returned. The entries in the array will not
     *         necessarily directly correspond to the entries in the handles array.
     * @throws IllegalArgumentException
     *             if handles is <code>null</code> or any entry is <code>null</code>, or empty
     *             after trim.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying
     *             exception.
     */
    public ExternalUser[] retrieveUsers(String[] handles) throws RetrievalException {
        return null;
    }

    /**
     * <p>
     * Retrieves the external users with the given handles, ignoring case when doing the retrieval.
     * </p>
     * @param handles
     *            the handles of the users we are interested in finding by the lower-case version of
     *            their handle.
     * @return an array of external users who have the given handles. If none of the given handles
     *         were found, an empty array will be returned. The entries in the array will not
     *         necessarily directly correspond to the entries in the handles array.
     * @throws IllegalArgumentException
     *             if handles is <code>null</code> or any entry is <code>null</code>, or empty
     *             after trim.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying
     *             exception.
     */
    public ExternalUser[] retrieveUsersIgnoreCase(String[] handles) throws RetrievalException {
        return null;
    }

    /**
     * <p>
     * Retrieves the external users whose first and last name start with the given first and last
     * name.
     * </p>
     * @param firstName
     *            the first name of the user(s) to find, or the empty string to represent "any first
     *            name". Both firstName and lastName cannot be empty.
     * @param lastName
     *            the last name of the user(s) to find, or the empty string to represent "any last
     *            name". Both firstName and lastName cannot be empty.
     * @return an array of external users whose first name and last name start with the given first
     *         and last name. If no users match, an empty array will be returned.
     * @throws IllegalArgumentException
     *             if firstName or lastName is <code>null</code>, or if BOTH are empty after
     *             trim. (I.e., if one is empty after trim, and the other is not empty after trim,
     *             this is not an exception.)
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying
     *             exception.
     */
    public ExternalUser[] retrieveUsersByName(String firstName, String lastName)
        throws RetrievalException {
        return null;
    }
}
