/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by various actions in their PostConstruct methods if these classes are not properly
 * initialized (e.g. while required property is not specified or property value has invalid format).
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProfileActionConfigurationException extends BaseRuntimeException {

    /**
     * <p>
     * Creates an instance of the class with a message.
     * </p>
     * @param message the error message
     */
    public ProfileActionConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates an instance of the class with a message and cause.
     * </p>
     * @param message the error message
     * @param cause the error cause
     */
    public ProfileActionConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates an instance of the class with a message and the exception data.
     * </p>
     * @param message the error message
     * @param exceptionData the exception data
     */
    public ProfileActionConfigurationException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * <p>
     * Creates an instance of the class with a message, cause and the exception data.
     * </p>
     * @param message the error message
     * @param cause the error cause
     * @param exceptionData the exception data
     */
    public ProfileActionConfigurationException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
