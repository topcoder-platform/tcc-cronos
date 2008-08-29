/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is used to indicate the Client and ClientStatus entity does not exist in persistence when
 * updating/deleting it. It extends from ManagerException.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class ClientEntityNotFoundException extends ManagerException {
    /**
     * Constructs an instance of this exception.
     *
     */
    public ClientEntityNotFoundException() {
        // empty
    }

    /**
     * Constructs an instance of this exception with error message.
     *
     * @param message
     *            the error message
     */
    public ClientEntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of this exception with error message and error cause.
     *
     *
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     */
    public ClientEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an instance of this exception with error message, error cause and the exception data.
     *
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     * @param exceptionData
     *            an object carrying the details of this exception
     */
    public ClientEntityNotFoundException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
