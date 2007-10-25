/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This class extends <code>BaseRuntimeException</code>. Called by all
 * <code>RegistrationServices</code> methods if an unforeseen error occurs. All exceptions that
 * occur from other <b>TopCoder</b> components are deemed to be exceptional, and would be wrapped
 * in this exception.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is thread safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationServiceException extends BaseRuntimeException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public RegistrationServiceException(String msg) {
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
    public RegistrationServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
