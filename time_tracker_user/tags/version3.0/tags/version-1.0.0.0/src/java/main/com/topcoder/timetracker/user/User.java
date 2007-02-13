/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.user;

import com.topcoder.security.authorization.persistence.GeneralPrincipal;

/**
 * <p>
 * The class represents an imported user entity for the Time Tracker application. It extends
 * GeneralPrincipal from the Authorization Component but adds one more parameter - the name of user
 * store where this user was imported from.
 * </p>
 *
 * <p>
 * This way, the relationship between the imported users and the original user store can be
 * maintained and stored within a data source (specifically, using a UserPersistence implementation.)
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class User extends GeneralPrincipal {

    /**
     * The user store name this user was imported from; this value is set by the constructor, and
     * retrieved by the getUserStoreName() method. Will never be null.
     */
    private final String storeName;


    /**
     * <p>
     * Creates a new User entity with the given ID, username and user store name.
     * </p>
     *
     * @param id unique ID for this User. Uniqueness is not maintained internally, but in the
     *  persistent store (databaes) itself.
     * @param name the username of this User
     * @param storeName the user store name this user was imported from
     * @throws NullPointerException if name or storeName is null
     * @throws IllegalArgumentException if id is non-positive or name is the empty String (after trim)
     *  or storeName is the empty String (no trim)
     */
    public User(int id, String name, String storeName) {
        // handles IAE of ID and name.length; down-cast to int so that the
        // database table will be OK.
        super(id, name);

        // the super constructor does not detect null names. bug?
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }

        if (storeName == null) {
            throw new NullPointerException("storeName cannot be null.");
        }

        if (storeName.length() == 0) {
            throw new IllegalArgumentException("storeName cannot be empty.");
        }
        this.storeName = storeName;
    }


    /**
     * Returns the user store that this user was imported from.
     *
     * @return the user store that this user was imported from.
     */
    public String getUserStoreName() {
        return storeName;
    }
}