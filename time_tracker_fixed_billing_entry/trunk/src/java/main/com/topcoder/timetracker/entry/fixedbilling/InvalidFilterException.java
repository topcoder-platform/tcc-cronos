/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

/**
 * <p>
 * This exception is thrown when there is request to perform a search with filters that cannot be recognized by the
 * given DAO or Manager class.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class InvalidFilterException extends DataAccessException {
    /**
     * <p>
     * Constructor accepting a message.
     * </p>
     *
     * @param message The message of the exception.
     */
    public InvalidFilterException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor accepting a message and cause.
     * </p>
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     */
    public InvalidFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
