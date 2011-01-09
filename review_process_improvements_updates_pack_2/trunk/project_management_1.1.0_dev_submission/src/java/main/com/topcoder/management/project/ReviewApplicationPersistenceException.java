/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;


/**
 * <p>
 * This exception is thrown when any error occurred during persistence operations of ReviewerStatistics entities. It's
 * thrown by ReviewerStatisticsPersistence implementations.
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
public class ReviewApplicationPersistenceException extends ApplicationsManagerException {
    /**
     * Create a new exception.
     *
     * @param message the error message
     */
    public ReviewApplicationPersistenceException(String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message the error message
     * @param cause the error cause data
     */
    public ReviewApplicationPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
