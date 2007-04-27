/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <sup>This exception is thrown when there are problems with configuration.</sup>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class ConfigurationException extends BaseException {
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
