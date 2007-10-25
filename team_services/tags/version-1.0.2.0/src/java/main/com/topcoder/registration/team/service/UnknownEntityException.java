/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This class extends <code>BaseRuntimeException</code>. Called by all <code>TeamServices</code>
 * methods that take a <code>teamId</code>, <code>projectId</code>, <code>positioned</code>,
 * or <code>resourceId</code> when that ID is not found in the manager.
 * </p>
 * <p>
 * Thread safety: This class is thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class UnknownEntityException extends BaseRuntimeException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public UnknownEntityException(String msg) {
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
    public UnknownEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
