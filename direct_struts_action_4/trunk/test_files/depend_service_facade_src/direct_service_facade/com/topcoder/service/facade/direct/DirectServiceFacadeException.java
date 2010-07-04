/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of DirectServiceFacade when some other error occurred. Also this
 * exception is used as a base class for other specific custom exceptions.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class DirectServiceFacadeException extends BaseCriticalException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -9085306320880768645L;

    /**
     * <p>
     * Creates a new instance of this exception with the given message.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     */
    public DirectServiceFacadeException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message and cause.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     */
    public DirectServiceFacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message and exception data.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param data
     *            the exception data
     */
    public DirectServiceFacadeException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause and exception data.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     * @param data
     *            the exception data
     */
    public DirectServiceFacadeException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
