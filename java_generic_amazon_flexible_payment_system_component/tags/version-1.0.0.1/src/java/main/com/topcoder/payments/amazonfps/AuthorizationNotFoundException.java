/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The <code>AuthorizationNotFoundException</code> exception is thrown by implementations of
 * <code>AuthorizationPersistence</code> and <code>AmazonPaymentManager</code> when authorization with the
 * specified ID doesn't exist.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AuthorizationNotFoundException extends AmazonFlexiblePaymentManagementException {
    /**
     * The ID of the authorization that doesn't exist.
     * It is initialized in the constructor and never changed after that.
     * Can be any value. Has a getter.
     */
    private final long authorizationId;

    /**
     * Creates a new instance of <code>AuthorizationNotFoundException</code> with the specified detail message and
     * authorization id.
     *
     * @param message
     *          the detail message
     * @param authorizationId
     *          the ID of the authorization that doesn't exist
     */
    public AuthorizationNotFoundException(String message, long authorizationId) {
        super(message);
        this.authorizationId = authorizationId;
    }

    /**
     * Creates a new instance of <code>AuthorizationNotFoundException</code> with the specified detail message,
     * cause and authorization id.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param authorizationId
     *          the ID of the authorization that doesn't exist
     */
    public AuthorizationNotFoundException(String message, Throwable cause, long authorizationId) {
        super(message, cause);
        this.authorizationId = authorizationId;
    }

    /**
     * Creates a new instance of <code>AuthorizationNotFoundException</code> with the specified detail message,
     * exception data and authorization id.
     *
     * @param message
     *          the detail message
     * @param data
     *          the exception data
     * @param authorizationId
     *          the ID of the authorization that doesn't exist
     */
    public AuthorizationNotFoundException(String message, ExceptionData data, long authorizationId) {
        super(message, data);
        this.authorizationId = authorizationId;
    }

    /**
     * Creates a new instance of <code>AuthorizationNotFoundException</code> with the specified detail message,
     * cause, exception data and authorization id.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param data
     *          the exception data
     * @param authorizationId
     *          the ID of the authorization that doesn't exist
     */
    public AuthorizationNotFoundException(String message, Throwable cause, ExceptionData data, long authorizationId) {
        super(message, cause, data);
        this.authorizationId = authorizationId;
    }

    /**
     * Retrieves the ID of the authorization that doesn't exist.

     * @return the ID of the authorization that doesn't exist
     */
    public long getAuthorizationId() {
        return authorizationId;
    }
}
