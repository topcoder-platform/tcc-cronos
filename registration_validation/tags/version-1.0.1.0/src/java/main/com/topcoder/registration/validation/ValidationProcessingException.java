/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.service.RegistrationValidationException;

/**
 * <p>
 * Extends <code>RegistrationValidationException</code>. Called by all
 * validation methods in case an unexpected system error occurs during
 * validation.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationProcessingException extends
        RegistrationValidationException {

    /**
     * Creates a new exception instance with this error message.
     *
     *
     * @param msg
     *            error message
     */
    public ValidationProcessingException(String msg) {
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
    public ValidationProcessingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
