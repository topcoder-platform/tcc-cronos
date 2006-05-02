/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;

/**
 * <p>
 * This interface represents a general framework for managing pluggable user sources. The interface provides
 * a mapping between the name of a user store and a UserStore instance.  UserStore instances
 * may be added by the add() method, and removed by the remove() method.
 * </p>
 *
 * <p>
 * The interface provides the following functionality:
 * <ul>
 * <li>adding a user store to the set of managed stores</li>
 * <li>removing a user store from the set of managed stores</li>
 * <li>getting a managed user store names</li>
 * <li>retrieve a managed UserStore instance by its given unique name</li>
 * </ul>
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface UserStoreManager {
    /**
     * <p>
     * Returns true if the given user store name is managed by this manager, false otherwise.
     * </p>
     *
     * @return true if the given user store name is managed by this manager, false otherwise.
     * @param name user store name to query
     * @throws NullPointerException if userStoreName is null
     * @throws IllegalArgumentException if userStoreName is the empty String
     */
    boolean contains(String name);


    /**
     * <p>
     * Adds the given user store with given name to the manager. After this point, this user store
     * is considered to be "managed" by this UserStoreManager. One UserStore may be managed by more
     * than one UserStoreManager, however within a given UserStoreManager, the names of all the
     * managed stores must be unique.
     * </p>
     *
     * @return true if userStore was added, false if a user store with the given name was already
     *         managed by this manager
     * @param name unique name of user store to add
     * @param userStore UserStore instance to manage by this manager
     * @throws NullPointerException if name or userStore is null
     * @throws IllegalArgumentException if name is the empty String
     */
    boolean add(String name, UserStore userStore);


    /**
     * <p>
     * Removes the user store with the given name from the manager. After this point, this manager
     * no longer manages this named store. The name can be re-used for another UserStore in this or
     * any other manager.
     * </p>
     *
     * @return true if the UserStore instance referenced by the given name was removed from this
     *         manager, false if the given name was not managed by this manager
     * @param name name of the user store to add
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     */
    boolean remove(String name);


    /**
     * <p>
     * Returns a collection of all user store names managed by this manager. If this manager
     * doesn't manage any UserStores, will return an empty Collection.
     * </p>
     *
     * @return all user store names managed by this manager (Collection of String instances).
     */
    Collection getUserStoreNames();


    /**
     * <p>
     * Returns the UserStore instance for the given user store name.
     * </p>
     *
     * @param name unique name of user store to retrieve
     * @return the UserStore instance referenced by the given user store name
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws UnknownUserStoreException if no user store exists for the given name
     */
    UserStore getUserStore(String name) throws UnknownUserStoreException;
}
