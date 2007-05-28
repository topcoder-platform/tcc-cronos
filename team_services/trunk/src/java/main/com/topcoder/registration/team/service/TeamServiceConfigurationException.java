/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This class extends <code>BaseRuntimeException</code>. Called by the
 * <code>TeamServicesImpl</code> constructors if a configuration-related error occurs, such as a
 * namespace not being recognized by <b>Config Manager</b>, or missing required values, or errors
 * while constructing the managers and services.
 * </p>
 * <p>
 * Thread safety: This class is thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamServiceConfigurationException extends BaseRuntimeException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public TeamServiceConfigurationException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error.
     * </p>
     * @param msg
     *            the error message
     * @param cause
     *            cause of error
     */
    public TeamServiceConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
