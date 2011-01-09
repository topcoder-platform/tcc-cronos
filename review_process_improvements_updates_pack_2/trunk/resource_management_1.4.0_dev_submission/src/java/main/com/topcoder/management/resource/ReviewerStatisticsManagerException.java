/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown when any error occurred during managing ReviewerStatistics entities. It's thrown by
 * ReviewerStatisticsManager implementations.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it not thread safe, as its parent class is not thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsManagerException extends BaseCriticalException {
    /**
     * Create a new exception.
     *
     * @param message the error message
     */
    public ReviewerStatisticsManagerException(String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message the error message
     * @param cause the inner error cause
     */
    public ReviewerStatisticsManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with specified error message and exception data.
     *
     * @param message the error message
     * @param data the exception data
     */
    public ReviewerStatisticsManagerException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates an exception with specified error message, error cause and exception data.
     *
     * @param message the error message cause
     * @param cause the error cause
     * @param data the exception data
     */
    public ReviewerStatisticsManagerException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
