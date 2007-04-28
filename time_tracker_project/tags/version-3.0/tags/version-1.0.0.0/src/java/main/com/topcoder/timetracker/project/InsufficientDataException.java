/*
 * InsufficientDataException.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is thrown when some required fields are not set when creating or updating an entry in the
 * persistence. It is thrown by the utility classes.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class InsufficientDataException extends BaseException {
    /**
     * <p>
     * Creates a new instance of this custom exception with the given message. The message can be null, however.
     * </p>
     *
     * @param message the message describing the exception
     */
    public InsufficientDataException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this custom exception with the given message and cause. The message and cause can be
     * null, however.
     * </p>
     *
     * @param message the message describing the exception
     * @param cause the cause of the exception
     */
    public InsufficientDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
