/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>
 * Dummy implementation of the UserPersistence interface, for unit testing purposes only.
 * This implementation always throws exceptions.
 * </p>
 *
 * @see UserPersistence
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class ThrowingUserPersistence implements UserPersistence {
    /** Tells class which methods to throw on; 1=add, 2=remove, 4=getUsers, 7 = all. */
    private int throwMask = 7;

    /**
     * Set the throwMask to the argument. 1=add, 2=remove, 4=getUsers, 7 = all.
     * @param mask the new mask
     */
    public void setThrowMask(int mask) {
        this.throwMask = mask;
    }

    /**
      * Dummy implementation that always throws an exception.
      * @param user ignored.
      * @throws PersistenceException always.
      */
    public void addUser(User user) throws PersistenceException {
        if ((throwMask & 1) != 0) {
            throw new PersistenceException("Thrown from addUser.");
        }
    }


    /**
      * Dummy implementation that always throws an exception.
      * @param user ignored.
      * @throws PersistenceException always.
      */
    public void removeUser(User user) throws PersistenceException {
        if ((throwMask & 2) != 0) {
            throw new PersistenceException("Thrown from removeUser.");
        }
    }


    /**
      * Dummy implementation that always throws an exception.
      * @return throws PersistenceException always.
      * @throws PersistenceException always.
      */
    public Collection getUsers() throws PersistenceException {
        if ((throwMask & 4) != 0) {
            throw new PersistenceException("Thrown from getUsers.");
        }
        return Collections.EMPTY_LIST;
    }
}
