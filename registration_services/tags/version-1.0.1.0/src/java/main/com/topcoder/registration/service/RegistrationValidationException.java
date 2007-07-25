/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

/**
 * <p>
 * This class extends <code>RegistrationServiceException</code>. Called by the
 * <code>RegistrationValidator.validate</code> method if there is an unexpected error during the
 * call.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is thread safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationValidationException extends RegistrationServiceException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public RegistrationValidationException(String msg) {
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
    public RegistrationValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
