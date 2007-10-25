/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This class extends <code>BaseRuntimeException</code>. Represents a base exception for all
 * other exceptions defined in this component. It is thrown by all <code>ProjectServices</code>
 * methods.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServicesException extends BaseRuntimeException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public ProjectServicesException(String msg) {
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
    public ProjectServicesException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
