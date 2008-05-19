/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.registration.RegistrationPersistenceException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This custom exception is the base exception of all custom checked exceptions defined in this component.
 * It will not be directly thrown by any class.
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
public class WebServiceWrapperForResourceManagementException extends RegistrationPersistenceException {

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public WebServiceWrapperForResourceManagementException(String message) {
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
    public WebServiceWrapperForResourceManagementException(String message, Throwable cause) {
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
    public WebServiceWrapperForResourceManagementException(String message, ExceptionData data) {
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
    public WebServiceWrapperForResourceManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

