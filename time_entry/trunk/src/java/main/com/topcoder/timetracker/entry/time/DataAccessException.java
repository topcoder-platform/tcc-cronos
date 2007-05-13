/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while this component is interacting with the persistent store.
 * </p>
 *
 * <p>
 * It is thrown by all the DAO and Manager interfaces (and their respective implementations).
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class DataAccessException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 195056781039534376L;

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
