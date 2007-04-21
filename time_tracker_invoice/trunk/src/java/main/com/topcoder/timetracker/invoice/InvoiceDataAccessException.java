/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

/**
 * <p>
 * This exception is thrown when a problem occurs while this component is interacting with the persistent store. It
 * is thrown by all the DAO and Manager interfaces (and their respective implementations). This exception is thrown
 * by all DAOs and Managers interfaces in this component.
 * </p>
 * <p>
 * Thread Safety: This is an exception class, immutable, and therefore thread-safe.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceDataAccessException extends InvoiceException {

    /** Serial version UID. */
    private static final long serialVersionUID = -6877677874009148647L;

    /**
     * <p>
     * Constructor accepting a message.
     * </p>
     *
     * @param message
     *            The message of the exception.
     */
    public InvoiceDataAccessException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor accepting a message and cause.
     * </p>
     *
     * @param message
     *            The message of the exception.
     * @param cause
     *            The cause of the exception.
     */
    public InvoiceDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
