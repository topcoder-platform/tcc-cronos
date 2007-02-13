/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;

/**
 * <p>
 * The interface represents the general persistence framework for loading and saving imported User entities. Note
 * that User Roles are persisted by the Authorization Component, so this component is responsible only
 * to persist imported Users themselves.
 * </p>
 *
 * <p>
 * Implementations of this interface can use any persistence mechanism to store imported users, including
 * SQL Database, LDAP or flat files.
 * </p>
 *
 * <p>
 * The following operations are supported:
 * <ul>
 * <li>addUser - adds a user to the backing persistence layer</li>
 * <li>removeUser -  removes a user from the backing persistence layer</li>
 * <li>getUsers - retrieves all imported users from the backing persistence layer</li>
 * </ul>
 * </p>
 *
 * <p>
 * This interface is utilized by the UserManager class to store newly imported User entities.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface UserPersistence {

    /**
     * <p>
     * Adds the given user to the persistent store.  If this user already existed (by its unique id),
     * implementations should throw a PersistenceException.
     * </p>
     *
     * @param user User instance to add to the persistence storage
     * @throws NullPointerException if user is null
     * @throws PersistenceException if any error occurs while adding the user, which may wrap an underlying
     *      exception (e.g., SQLException)
     */
    void addUser(User user) throws PersistenceException;


    /**
     * <p>
     * Removes the given user from the persistent store. The id of the user is used to uniquely
     * identify it within persistent storage. If the user didn't exist in the store in the first
     * place, nothing happens.
     * </p>
     *
     * @param user User instance to remove from persistence storage
     * @throws NullPointerException if user is null
     * @throws PersistenceException if any error occurs during adding, which may wrap an underlying
     *             exception (e.g., SQLException)
     */
    void removeUser(User user) throws PersistenceException;


    /**
     * <p>
     * Retrieves all existing imported users from persistent storage. Each User will be fully populated
     * (id, name, userStoreName)  If no users existed in the persistent store, an empty Collection
     * must be returned.
     * </p>
     *
     * @return Collection of User instances stored within persistence storage
     * @throws PersistenceException if any error occurs during adding, which may wrap an underlying
     *      exception (e.g., SQLException)
     */
    Collection getUsers() throws PersistenceException;
}

