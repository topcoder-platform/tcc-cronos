/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

/**
 * <p>
 * This exception will be thrown by the ClientUtiltiy and the implementations of ClientDAO when they encounter
 * configuration error.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationException extends ClientUtilityException {
    /**
     * <p>
     * Constructs the exception with given message.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception with given message and cause.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public ConfigurationException(String message, Exception cause) {
        super(message, cause);
    }
}
