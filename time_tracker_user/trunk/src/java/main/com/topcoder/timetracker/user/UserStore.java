/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.Response;

import java.util.Collection;
import com.topcoder.security.authenticationfactory.AuthenticateException;

/**
 * <p>
 * This interface provides the framework for a pluggable user store, which is a way to importing
 * users into the Time Tracker application.  Implementations of this interface may use a SQL database,
 * LDAP, flat files, or any other persistence mechanism to retrieve users to be imported.
 * </p>
 *
 * <p>
 * This interface provides the following classes of operation:
 * <ul>
 * <li>Connection - a way to connect to the user store (setConnectionString method).</li>
 * <li>Enumeration - a way to enumerate all existing users in the user store (getNames method)
 * </li>
 * <li>Search - a way to find specific users by full or partial name in the user store (contains,
 * search methods)</li>
 * <li>Authentication - a way to authenticate a user against a given password (authenticate method)
 * </li>
 * </ul>
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface UserStore {
    /**
     * <p>
     * Enumerates all usernames contained within the user store.
     * </p>
     *
     * @return Collection of String instances representing the usernames
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws PersistenceException if any error occurs during the enumeration, which may wrap an
     *             underlying exception (e.g., SQLException)
     */
    Collection getNames() throws PersistenceException;


    /**
     * <p>
     * Searches for specific users by name, or partial name, in the user store. The % character can
     * be used as a wildcard when specifying a username. For example, to search for users whose
     * username starts with a certain substring, you can use this call:
     *
     * <pre>
     * store.search(&quot;startswith%&quot;);
     * </pre>
     *
     * As another example, to search for users whose username contains a certain substring, you can
     * use this call:
     *
     * <pre>
     * store.search(&quot;%middle%&quot;);
     * </pre>
     *
     * </p>
     *
     * @param pattern a SQL-type "LIKE" pattern which can optionally include '%', to represent any
     *            number of characters
     * @return Collection of String instances representing the usernames found in the user store
     *         that match the pattern
     * @throws NullPointerException if pattern is null
     * @throws IllegalArgumentException if pattern is the empty String
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws PersistenceException if any error occurs during the search, which may wrap an
     *             underlying exception (e.g., SQLException)
     */
    Collection search(String pattern) throws PersistenceException;


    /**
     * <p>
     * Searches for a specific user by their username in the user store. This search is
     * case-sensitive and is an *exact match*.
     * </p>
     *
     * @param name username of user to find
     * @return true if there is a user with the given username within the user store, false
     *        otherwise
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws PersistenceException if any error occurs during the search, which may wrap an
     *        underlying exceptoin (e.g., SQLException)
     */
    boolean contains(String name) throws PersistenceException;


    /**
     * <p>
     * Authenticate the user with the given username against the given password. Note that password
     * is of Object type, because it can have different structures for different user stores (for
     * example, an encrypted password might be represented as an array of bytes).
     * </p>
     * <p>
     * Different user stores may provide different authentication mechanisms. For example, a
     * UserStore might support an external Authenticator implementation (from the Authentication
     * Factory component) or it might be simple and built into the UserStore itself.
     * </p>
     *
     * @return a Response instance (from Authentication Factory component) with the 'successful
     *         flag' set to true if authentication was successful, false otherwise. Nothing else in
     *         the Response object is required to be filled in.
     * @param name username of the user to authenticate
     * @param password password of the user to authenticate
     * @throws NullPointerException if name or password is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws AuthenticateException if any error occurs during authentication, which may wrap an
     *             underlying exception (e.g., NamingException or SQLException)
     */
    Response authenticate(String name, Object password) throws AuthenticateException;


    /**
     * <p>
     * Sets the connection String to the underlying user store. For example, this might be the
     * JDBC connection string, or a URL defining where to contact an LDAP server.  This method
     * should be called immedidately after instantiating UserStore objects, otherwise, the other
     * methods in this interface may throw IllegalStateException.
     * </p>
     *
     * @param connection the connection String (specific for each implementation of UserStore)
     * @throws NullPointerException if connection is null
     * @throws IllegalArgumentException if connection is the empty String
     */
    void setConnectionString(String connection);
}
