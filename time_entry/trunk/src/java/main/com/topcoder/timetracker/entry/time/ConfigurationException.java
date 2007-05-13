/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.time;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown if there is a problem with constructing a class within
 * this component due to configuration problems.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -1163661162833649546L;

	/**
     * <p>
     * Constructor accepting a message.
     * </p>
     *
     * @param message The message of the exception.
     */
    public ConfigurationException(String message) {
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
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
