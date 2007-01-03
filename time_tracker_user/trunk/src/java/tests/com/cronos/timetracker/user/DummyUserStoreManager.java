/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Dummy implementation of the UserStoreManager interface, for unit testing purposes only.
 * It cannot be pre-configured, and uses a Map as its backing store.
 * </p>
 *
 * @see UserStoreManager
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class DummyUserStoreManager implements UserStoreManager {


    /** This is the backing of this entire class - maps from store name to UserStore instance. */
    private Map userStores = new HashMap();


    /**
     * <p>
     * Returns true if given user store name is present within the manager, false otherwise.
     * </p>
     *
     * @return true if given user store name is present within the manager, false otherwise
     * @param name user store name to query
     */
    public boolean contains(String name) {
        // if the map has a non-null value, that indicates we know about it.
        return userStores.get(name) != null;
    }


    /**
     * <p>
     * Adds given user store with given name to the manager.
     * </p>
     *
     * @return true if userStore is added, false if userStore is already present within the manager
     * @param name unique name of user store to add
     * @param userStore UserStore instance
     */
    public boolean add(String name, UserStore userStore) {
        // if it's already there, return false
        if (contains(name)) {
            return false;
        }
        // capture it.
        userStores.put(name, userStore);
        return true;
    }


    /**
     * <p>
     * Removes user store with given name from the manager.
     * </p>
     *
     * @return true if userStore is removed, false if userStore is not present within the manager
     * @param name name of user store to add
     */
    public boolean remove(String name) {
        // remove it, and if it was there, return true.
        return userStores.remove(name) != null;
    }


    /**
     * Returns all user stores from the manager (Collection of String instances).
     *
     * @return all user stores from the manager
     */
    public Collection getUserStoreNames() {
        return Collections.unmodifiableSet(userStores.keySet());
    }


    /**
     * <p>
     * Returns UserStore instance for given user store name.
     * </p>
     *
     * @param name unique name of user store to retrieve
     * @return UserStore instance for given user store name
     * @throws UnknownUserStoreException if no user store exists for given name
     */
    public UserStore getUserStore(String name) throws UnknownUserStoreException {
        // retrieve from the map
        UserStore store = (UserStore) userStores.get(name);
        if (store == null) {
            throw new UnknownUserStoreException("user store " + name + " not found.");
        }

        return store;
    }
}
