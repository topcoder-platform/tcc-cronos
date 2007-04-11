/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

/**
 * An exception thrown by {@link InformixRoleCategoryPersistence#updateCategories updateCategories} when the
 * category fails validation.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class CategoryValidationException extends RoleCategoryPersistenceException {
    /**
     * Constructs a new <code>CategoryValidationException</code> with the specified message.
     *
     * @param message a description of the reason for the exception
     */
    public CategoryValidationException(String message) {
        super(message);
    }
}
