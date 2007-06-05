/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.service.RegistrationValidationException;

/**
 * <p>
 * This class extends <code>RegistrationValidationException</code>. Called by
 * all namespace constructors in all validators if a configuration-related error
 * occurs, such as a namespace not being recognized by ConfigManager, or missing
 * required values, or errors while constructing the managers and services and
 * validators.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class RegistrationValidationConfigurationException extends
        RegistrationValidationException {

    /**
     * Creates a new exception instance with this error message.
     *
     *
     * @param msg
     *            error message
     */
    public RegistrationValidationConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of
     * error.
     *
     *
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public RegistrationValidationConfigurationException(String msg,
            Throwable cause) {
        super(msg, cause);
    }
}