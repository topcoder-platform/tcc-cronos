/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.Response;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Dummy implementation of the UserStore interface, for unit testing purposes only.
 * It uses a Set to retrieve names from its "persistent store"
 * </p>
 *
 * @see UserStore
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class DummyUserStore implements UserStore {

    /** The connection string as set by setConnectionString(). */
    private String connectionString;

    /** The set of names that this user store knows about. */
    private Set names = new HashSet();


    /**
     * Since this is not a real store, this is the only way to "add" users. Note that this
     * method is not part of the public interface, but is in fact the only way to add
     * users to the store.
     * @param name The username to add.  Not validated/verified against null or empty.
     */
    public void add(String name) {
        names.add(name);
    }


    /**
      * Returns the set of names we're managing in memory.
      * @return the set of names we're managing in memory.
      */
    public Collection getNames() {
        return Collections.unmodifiableSet(names);
    }


    /**
      * Returns a list of the single pattern given.
      * @param pattern the pattern to "find"
      * @return a list of the single pattern given.
      */
    public Collection search(String pattern) {
        return Collections.singletonList(pattern);
    }


    /**
      * Returns true if the name is in the memory list, false if not.
      * @param name the name to find in memory.
      * @return true if the name is in the memory list, false if not.
      */
    public boolean contains(String name) {
        return names.contains(name);
    }


    /**
      * Dummy implementation; matches 'username1' with 'password1', etc.
      * @param name username to "authenticate"
      * @param password password to "authenticate"
      * @return a Response object with the success flag set if name and password match.
     * @throws AuthenticateException If the password is not a String
      */
    public Response authenticate(String name, Object password) throws AuthenticateException {
        if (!(password instanceof String)) {
            throw new AuthenticateException("Password is wrong class");
        }
        String passwordString = (String) password;
        boolean success = false;
        for (int i = 1; i <= 3; ++i) {
            if (name.equals("username" + i)) {
                success = passwordString.equals("password" + i);
                break;
            }
        }
        if (name.equals("username")) {
            success = passwordString.equals("password");
        }
        return new Response(success);
    }


    /**
      * Dummy implementation.
      * @param connection the connection string; stored for later use.
      */
    public void setConnectionString(String connection) {
        this.connectionString = connection;
    }


    /**
     * @return the connection string, as set by setConnectionString();
     */
    public Object getConnectionString() {
        return connectionString;
    }
}
