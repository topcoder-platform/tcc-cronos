/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

/**
 * <p>A simple data structure encapsulating a username/ID pair used in {@link RoleCategoryPersistence role-category
 * persistence}. Instances of this class correspond to rows in the <code>principal</code> table.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class User {
    /**
     * The user ID, corresponding to the <code>principal.principal_id</code> column. This member is initialized in
     * the constructor, is immutable, and will not be negative.
     */
    private final long id;

    /**
     * The username, corresponding to the <code>principal.principal_name</code> column. This member is initialized
     * in the constructor, is immutable, and can be <code>null</code> or an empty string.
     */
    private final String name;

    /**
     * Constructs a new <code>User</code> with the specified ID and username.
     *
     * @param id the user ID
     * @param name the username
     * @throws IllegalArgumentException if <code>id</code> is negative
     */
    public User(long id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("user ID must not be negative");
        }

        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the (non-negative) user ID.
     *
     * @return the (non-negative) user ID
     */
    public long getId() {
        return this.id;
    }

    /**
     * Retrieves the username. The returned value may be <code>null</code> or an empty string.
     *
     * @return the username
     */
    public String getName() {
        return this.name;
    }
}
