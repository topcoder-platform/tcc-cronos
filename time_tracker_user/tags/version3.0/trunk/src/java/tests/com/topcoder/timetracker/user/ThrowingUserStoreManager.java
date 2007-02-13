/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;

/**
 * <p>
 * Dummy implementation of the UserStoreManager interface, for unit testing purposes only.
 * This implementation always throws exceptions.
 * </p>
 *
 * @see UserStoreManager
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class ThrowingUserStoreManager implements UserStoreManager {


    /**
      * Dummy implementation.
      * @param name ignored.
      * @return false always.
      */
    public boolean contains(String name) {
        return false;
    }


    /**
      * Dummy implementation.
      * @param name ignored.
      * @param userStore ignored.
      * @return false always.
      */
    public boolean add(String name, UserStore userStore) {
        return false;
    }


    /**
      * Dummy implementation.
      * @param name ignored.
      * @return false always.
      */
    public boolean remove(String name) {
        return false;
    }


    /**
      * Dummy implementation.
      * @return null always.
      */
    public Collection getUserStoreNames() {
        return null;
    }


    /**
      * Dummy implementation that always throws an exception.
      * @param name ignored.
      * @return throws UnknownUserStoreException always.
      * @throws UnknownUserStoreException always.
      */
    public UserStore getUserStore(String name) throws UnknownUserStoreException {
        throw new UnknownUserStoreException("Always thrown from this method.");
    }
}
