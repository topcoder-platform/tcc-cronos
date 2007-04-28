/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

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
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationException extends DataAccessException {
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
