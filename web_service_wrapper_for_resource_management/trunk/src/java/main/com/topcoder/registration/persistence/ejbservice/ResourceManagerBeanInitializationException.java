/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.ejbservice;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is a custom runtime exception that will be thrown by the <code>ResourceManagerBean</code> class
 * in the <code>initialize</code> method,  if errors occur while creating the <code>ResourceManager</code>
 * instance or if any injected env-entry values are invalid.
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
public class ResourceManagerBeanInitializationException extends BaseRuntimeException {

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public ResourceManagerBeanInitializationException(String message) {
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
    public ResourceManagerBeanInitializationException(String message, Throwable cause) {
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
    public ResourceManagerBeanInitializationException(String message, ExceptionData data) {
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
    public ResourceManagerBeanInitializationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

