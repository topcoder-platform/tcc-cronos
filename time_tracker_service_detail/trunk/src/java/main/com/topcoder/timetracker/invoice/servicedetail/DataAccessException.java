/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This exception should be thrown if appears problem with database like failed to create connection. This
 * exception is parent for all other exceptions connected with DAO class.
 * </p>
 * <p>
 * </p>
 * <p>
 * Class is thread safe because it is immutable
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class DataAccessException extends InvoiceServiceDetailException {

    /** Serial version UID. */
    private static final long serialVersionUID = -3759192454260451822L;

    /**
     * <p>
     * Constructor which create exception with given text message.
     * </p>
     *
     * @param message
     *            A string representing the message for this exception.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor which create exception with given text message and underlying cause.
     * </p>
     *
     * @param message
     *            A string representing the message for this exception.
     * @param cause
     *            An exception representing the cause of this exception.
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
