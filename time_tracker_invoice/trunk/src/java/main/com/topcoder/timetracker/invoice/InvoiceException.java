/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception extends the <code>BaseException</code>. It is also the parent exception class for all the
 * other custom exceptions in this design.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong> Exceptions are thread-safe.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceException extends BaseException {

    /** Serial version UID. */
    private static final long serialVersionUID = 6635139555434067536L;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message
     *            the error message
     */
    public InvoiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     */
    public InvoiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
