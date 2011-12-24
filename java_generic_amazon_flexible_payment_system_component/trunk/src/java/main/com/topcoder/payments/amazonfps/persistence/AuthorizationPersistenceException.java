/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence;

import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The <code>AuthorizationPersistenceException</code> exception is thrown by implementations of
 * <code>AuthorizationPersistence</code> when some error occurs while accessing the persistence.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AuthorizationPersistenceException extends AmazonFlexiblePaymentManagementException {
    /**
     * Creates a new instance of <code>AuthorizationPersistenceException</code> with the specified detail message.
     *
     * @param message
     *          the detail message
     */
    public AuthorizationPersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of <code>AuthorizationPersistenceException</code> with the specified detail message and
     * cause.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     */
    public AuthorizationPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of <code>AuthorizationPersistenceException</code> with the specified detail message and
     * exception data.
     *
     * @param message
     *          the detail message
     * @param data
     *          the exception data
     */
    public AuthorizationPersistenceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of <code>AuthorizationPersistenceException</code> with the specified detail message,
     * cause and exception data.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param data
     *          the exception data
     */
    public AuthorizationPersistenceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
