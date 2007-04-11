/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

/**
 * An exception thrown by {@link RoleCategoryPersistence#getUsers getUsers} when an indicated role does not exist
 * in persistence.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class RoleNotFoundException extends RoleCategoryPersistenceException {
    /**
     * Creates a new <code>RoleNotFoundException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
