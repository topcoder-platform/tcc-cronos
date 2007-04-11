/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

/**
 * An exception thrown by {@link RoleCategoryPersistence RoleCategoryPersistence} when an indicated manager does
 * not exist in persistence.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class ManagerNotFoundException extends RoleCategoryPersistenceException {
    /**
     * Creates a new <code>ManagerNotFoundException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public ManagerNotFoundException(String message) {
        super(message);
    }
}
