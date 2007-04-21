/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

/**
 * <p>
 * This exception extends the <code>InvoiceException</code>, and it is thrown if the configured value is
 * invalid, or any required configuration value is missing.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong> Exceptions are thread-safe.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceConfigurationException extends InvoiceException {

    /** Serial version UID. */
    private static final long serialVersionUID = 7564812132267332861L;

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message.
     * </p>
     *
     * @param message
     *            the error message
     */
    public InvoiceConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     */
    public InvoiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
