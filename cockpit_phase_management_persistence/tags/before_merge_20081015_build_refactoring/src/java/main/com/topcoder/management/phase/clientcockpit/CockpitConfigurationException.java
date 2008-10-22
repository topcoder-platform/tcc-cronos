/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.ConfigurationException;


/**
 * <p>
 * This exception is thrown when some errors occur in the configuration: properties are missing or in the bad
 * format.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is not thread safe since the base class
 * <code>ConfigurationException</code> is not thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class CockpitConfigurationException extends ConfigurationException {
    /**
     * <p>
     * This constructor initializes a new exception with the given message.
     * </p>
     *
     * @param message the message containing a description of why the exception was thrown. May be
     *        <code>null</code> or empty string (trimmed).
     */
    public CockpitConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * This constructor initializes a new exception with the given message and cause.
     * </p>
     *
     * @param message the message containing a description of why the exception was thrown. May be
     *        <code>null</code> or empty string(trimmed).
     * @param cause the initial <code>java.lang.Throwable</code> reason which triggered this exception to be
     *        thrown. It may be <code>null</code>.
     */
    public CockpitConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
