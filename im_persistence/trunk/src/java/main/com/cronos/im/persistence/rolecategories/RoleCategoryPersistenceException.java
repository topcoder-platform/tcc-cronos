/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import com.topcoder.util.errorhandling.BaseException;

/**
 * An exception thrown by {@link RoleCategoryPersistence RoleCategoryPersistence} to indicate a general failure in
 * interacting with the database.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class RoleCategoryPersistenceException extends BaseException {
    /**
     * Creates a new <code>RoleCategoryPersistenceException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public RoleCategoryPersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>RoleCategoryPersistenceException</code> with the specified message and cause.
     *
     * @param message a description of the reason for this exception
     * @param cause the lower-level exception that caused this exception to be thrown
     */
    public RoleCategoryPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
