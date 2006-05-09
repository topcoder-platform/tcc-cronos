/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.Response;

/**
 * <p>
 * Dummy implementation of the UserStore interface, for unit testing purposes only.
 * This implementation always throws exceptions.
 * </p>
 *
 * @see UserStore
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class ThrowingUserStore implements UserStore {


    /**
      * Dummy implementation that always throws an exception.
      * @return throws PersistenceException always.
      * @throws PersistenceException always.
      */
    public Collection getNames() throws PersistenceException {
        throw new PersistenceException("Always thrown from this method.");
    }


    /**
      * Dummy implementation that always throws an exception.
      * @param pattern ignored.
      * @return throws PersistenceException always.
      * @throws PersistenceException always.
      */
    public Collection search(String pattern) throws PersistenceException {
        throw new PersistenceException("Always thrown from this method.");
    }


    /**
      * Dummy implementation that always throws an exception.
      * @param name ignored.
      * @return throws PersistenceException always.
      * @throws PersistenceException always.
      */
    public boolean contains(String name) throws PersistenceException {
        throw new PersistenceException("Always thrown from this method.");
    }


    /**
      * Dummy implementation that always throws an exception.
      * @param name ignored.
      * @param password ignored.
      * @return throws AuthenticateException always.
      * @throws AuthenticateException always.
      */
    public Response authenticate(String name, Object password) throws AuthenticateException {
        throw new AuthenticateException("Always thrown from this method.");
    }


    /**
      * Dummy implementation.
      * @param connection ignored.
      */
    public void setConnectionString(String connection) {
    }


    public String getEmail(String name) {
        return null;
    }
}
