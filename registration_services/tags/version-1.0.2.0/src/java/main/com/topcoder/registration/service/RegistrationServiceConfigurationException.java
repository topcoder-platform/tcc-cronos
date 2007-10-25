/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

/**
 * <p>
 * This class extends <code>RegistrationServiceException</code>. Called by the
 * <code>RegistrationServicesImpl</code> constructors if a configuration-related error occurs,
 * such as a namespace not being recognized by Config Manager, or missing required values, or errors
 * while constructing the managers and services.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is thread safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationServiceConfigurationException extends RegistrationServiceException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public RegistrationServiceConfigurationException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error.
     * </p>
     * @param msg
     *            the error message
     * @param cause
     *            the cause of error
     */
    public RegistrationServiceConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
