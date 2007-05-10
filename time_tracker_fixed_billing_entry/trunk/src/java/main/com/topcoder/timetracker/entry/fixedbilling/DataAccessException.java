/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while this component is interacting with the persistent store.  It is
 * thrown by all the DAO and Manager interfaces (and their respective implementations). The subclasses are also thrown
 * by the DAO and Manager interfaces.
 * </p>
 *
 * <p>
 * Thread Safety: This is an exception class, immutable, and therefore thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class DataAccessException extends BaseException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = 1582882539949468049L;

	/**
     * <p>
     * Constructor accepting a message.
     * </p>
     *
     * @param message The message of the exception.
     */
    public DataAccessException(String message) {
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
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
