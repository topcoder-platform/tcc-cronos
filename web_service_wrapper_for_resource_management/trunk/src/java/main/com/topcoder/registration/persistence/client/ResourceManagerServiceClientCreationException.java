/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.client;

import com.topcoder.registration.persistence.WebServiceWrapperForResourceManagementException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is a custom exception that will be thrown by the <code>ResourceManagerServiceClient</code> constructors
 * if errors occur when creating the client ( when creating a <code>ResourceManagerService</code> instance or when
 * creating a proxy).
 * </p>
 *
 * <p>
 * It will also be thrown from the constructor, that takes a configuration argument, from
 * <code>ResourceManagerRegistrationPersistence</code> class.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResourceManagerServiceClientCreationException extends WebServiceWrapperForResourceManagementException {

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public ResourceManagerServiceClientCreationException(String message) {
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
    public ResourceManagerServiceClientCreationException(String message, Throwable cause) {
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
    public ResourceManagerServiceClientCreationException(String message, ExceptionData data) {
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
    public ResourceManagerServiceClientCreationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

