/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is a custom exception that will be thrown by <code>RegistrationEntitiesToResourceConverter</code>
 * implementations if a conversion error occurs.
 * </p>
 *
 * <p>
 * It will be thrown by <code>RegistrationEntitiesToResourceConverter</code> implementations and in the
 * <code>register()</code> and <code>unregister()</code> methods from the
 * <code>ResourceManagerRegistrationPersistence</code> class.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   The class is mutable and not thread-safe. The application throwing the exception should handle it in a thread-safe
 *   manner.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RegistrationEntitiesToResourceConversionException extends WebServiceWrapperForResourceManagementException {

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public RegistrationEntitiesToResourceConversionException(String message) {
        super(message);
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and inner cause.
     * </p>
     *
     * @param message the error message
     * @param cause the cause of this exception
     */
    public RegistrationEntitiesToResourceConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and exception data.
     * </p>
     *
     * @param data the exception data
     * @param message the error message
     */
    public RegistrationEntitiesToResourceConversionException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and inner cause and exception data.
     * </p>
     *
     * @param message the error message
     * @param cause the cause of this exception
     * @param data the exception data
     */
    public RegistrationEntitiesToResourceConversionException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

