/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is the top-level application exception in this application. All other application exceptions in
 * that class will extend it. extends BaseCriticalException.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostServiceException extends BaseCriticalException {
    /**
     * Creates a new exception instance with this error message.
     *
     * @param message
     *            error message
     */
    public BillingCostServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     *
     * @param message
     *            error message
     * @param cause
     *            cause of error
     */
    public BillingCostServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with this error message and any additional data to attach to the exception.
     *
     * @param message
     *            error message
     * @param data
     *            additional data to attach to the exception
     */
    public BillingCostServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new exception instance with this error message, cause of error, and any additional data to attach
     * to the exception.
     *
     * @param message
     *            error message
     * @param cause
     *            cause of error
     * @param data
     *            additional data to attach to the exception
     */
    public BillingCostServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
