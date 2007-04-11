/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * An exception thrown by the <i>IM Persistence</i> component when an error related to the configuration manager or
 * object factory occurs. Examples include missing namespaces, missing required attributes, or invalid object
 * specifications.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class ConfigurationException extends BaseException {
    /**
     * Creates a new <code>ConfigurationException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>ConfigurationException</code> with the specified message and cause.
     *
     * @param message a description of the reason for this exception
     * @param cause the lower-level exception that caused this exception to be thrown
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
