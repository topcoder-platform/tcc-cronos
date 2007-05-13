/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be the base exception for all exceptions thrown by the ClientUtility. This exception can be used
 * by the application to simply their exception processing by catching a single exception regardless of the actual
 * subclass.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 1964026565626764803L;

	/**
     * <p>
     * Constructs the exception with given message.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    protected ClientUtilityException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception with given message and cause.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    protected ClientUtilityException(String message, Exception cause) {
        super(message, cause);
    }
}
