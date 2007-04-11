/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

/**
 * Exception thrown by {@link RoleCategoryPersistence RoleCategoryPersistence} when an indicated category does not
 * exist in persistence.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class CategoryNotFoundException extends RoleCategoryPersistenceException {
    /**
     * Creates a new <code>CategoryNotFoundException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
