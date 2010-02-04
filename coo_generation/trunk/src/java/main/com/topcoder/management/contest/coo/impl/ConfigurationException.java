/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is used to signal that there is error in configuration, e.g.
 * some missing/invalid values.
 * </p>
 * <p>
 * It is thrown in constructors of non-entity classes defined in this component
 * (e.g those defined not in com.topcoder.management.contest.coo package).
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> exceptions are not thread safe and they are
 * not expected to be used concurrently.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConfigurationException extends BaseException {
    /**
     * Create exception with the given message.
     *
     * @param message The exception message
     *
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Create exception with the given message and cause.
     *
     * @param message The exception message
     * @param cause The exception cause
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create exception with the given message and exception data.
     *
     * @param message The exception message
     * @param data The exception data
     */
    public ConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create exception with the given message, cause and exception data.
     *
     * @param message The exception message
     * @param cause The exception cause
     * @param data The exception data
     */
    public ConfigurationException(String message, Throwable cause,
            ExceptionData data) {
        super(message, cause, data);
    }
}
