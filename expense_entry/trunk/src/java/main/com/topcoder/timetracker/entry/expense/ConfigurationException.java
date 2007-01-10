/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * Defines a custom exception which is thrown if error occurs when reading the configuration value. It happens when the
 * configuration value is missing or invalid.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class ConfigurationException extends BaseException {
    /**
     * <p>
     * Creates a new instance of <code>ConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>ConfigurationException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
