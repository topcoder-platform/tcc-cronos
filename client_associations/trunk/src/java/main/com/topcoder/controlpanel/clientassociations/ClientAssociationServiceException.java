/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is created to represent any errors that might occur in the methods defined in the session beans, for
 * example a persistence error. This exception is thrown by the methods in the session beans.
 * </p>
 * <p>
 * This exception must have the "@ApplicationException(rollback=true)" annotation so that if any error occurs the
 * transaction could be automatically rolled back by ejb container.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe, as its parent is not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ClientAssociationServiceException extends BaseCriticalException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4865777819948210021L;

    /**
     * <p>
     * Creates a new instance of this exception.
     * </p>
     *
     * @param message
     *            the error message of this exception.
     */
    public ClientAssociationServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception.
     * </p>
     *
     * @param message
     *            the error message of this exception.
     * @param cause
     *            the inner cause of this exception.
     */
    public ClientAssociationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception.
     * </p>
     *
     * @param data
     *            the error message of this exception.
     * @param message
     *            the additional data to attach to the exception.
     */
    public ClientAssociationServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new instance of this exception.
     * </p>
     *
     * @param message
     *            the error message of this exception.
     * @param cause
     *            the inner cause of this exception.
     * @param data
     *            the additional data to attach to the exception.
     */
    public ClientAssociationServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
