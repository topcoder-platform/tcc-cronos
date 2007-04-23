/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This exception should be thrown if data which should be inserted to database table is incorrect. For example if
 * amount is null.
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
public class InvalidDataException extends DataAccessException {

    /** Serial version UID. */
    private static final long serialVersionUID = 850836936364951197L;

    /**
     * <p>
     * Constructor which create exception with given text message.
     * </p>
     *
     * @param message
     *            An exception representing the cause of this exception.
     */
    public InvalidDataException(String message) {
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
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
