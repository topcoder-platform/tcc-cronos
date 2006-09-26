/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown if a problem occurs during configuration.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class ConfigurationException extends BaseException {

    /**
     * Constructor accepting a message.
     * @param message
     *            the message of the exception.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructor accepting a message and cause.
     * @param message
     *            the message of the exception.
     * @param cause
     *            the cause of the exception.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
