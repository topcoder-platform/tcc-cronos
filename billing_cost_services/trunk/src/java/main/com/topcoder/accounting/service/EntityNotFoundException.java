/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown in the updateBillingCostExportDetails method of BillingCostAuditService if any given
 * billingCostExportDetailId is not found in persistence. Extends BillingCostServiceException.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class EntityNotFoundException extends BillingCostServiceException {
    /**
     * Creates a new exception instance with this error message.
     *
     * @param message
     *            error message
     */
    public EntityNotFoundException(String message) {
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
    public EntityNotFoundException(String message, Throwable cause) {
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
    public EntityNotFoundException(String message, ExceptionData data) {
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
    public EntityNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
