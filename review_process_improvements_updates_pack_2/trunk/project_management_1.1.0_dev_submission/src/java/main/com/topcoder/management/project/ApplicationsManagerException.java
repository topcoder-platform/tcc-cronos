/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * <p>
 * This exception is thrown when any error occurred during managing ReviewApplication entities. It's thrown by
 * ApplicationsManager implementations.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it not thread safe, as its parent class is not thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.1
 * @since 1.1
 */
public class ApplicationsManagerException extends BaseCriticalException {
    /**
     * Create a new exception.
     *
     * @param message the error message
     */
    public ApplicationsManagerException(String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message the error message
     * @param cause the inner error cause
     */
    public ApplicationsManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
