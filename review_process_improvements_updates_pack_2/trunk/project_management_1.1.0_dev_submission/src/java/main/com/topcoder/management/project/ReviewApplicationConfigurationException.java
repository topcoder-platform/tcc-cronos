/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown when any error occurred during configuration. It's thrown by ApplicationsManagerImpl and
 * AbstractInformixReviewApplicationPersistence.
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
public class ReviewApplicationConfigurationException extends BaseRuntimeException {
    /**
     * Create a new exception.
     *
     * @param message the error message
     */
    public ReviewApplicationConfigurationException(String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message the error message
     * @param cause the error cause data
     */
    public ReviewApplicationConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with specified error message and exception data.
     *
     * @param message the error message
     * @param data the exception data
     */
    public ReviewApplicationConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates an exception with specified error message, error cause and exception data.
     *
     * @param message the error message
     * @param cause the error cause data
     * @param data the exception data
     */
    public ReviewApplicationConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
