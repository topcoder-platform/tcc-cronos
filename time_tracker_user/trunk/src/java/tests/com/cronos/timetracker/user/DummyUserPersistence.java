/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * <p>
 * Dummy implementation of the UserPersistence interface, for unit testing purposes only.
 * It uses a Map to store the users that are added.
 * </p>
 *
 * @see UserPersistence
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class DummyUserPersistence implements UserPersistence {

    /** Map from user name to User object. */
    private Map users = new HashMap();

    /**
      * Adds the user to the internal set of users.
      * @param user the user to add to this class.
      */
    public void addUser(User user) {
        users.put(user.getName(), user);
    }


    /**
      * Removes the user from the internal set of users.
      * @param user the user to remove.
      */
    public void removeUser(User user) {
        users.remove(user.getName());
    }


    /**
      * Returns the users that this persistence "stores".
      * @return a Collection of User objects
      */
    public Collection getUsers() {
        return Collections.unmodifiableSet(new HashSet(users.values()));
    }
}
