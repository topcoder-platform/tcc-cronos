/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is thrown for any configuration error in this component.
 * </p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * @author mekanizumu, sokol
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CompleteProfileActionConfigurationException extends BaseRuntimeException {

    /**
     * <p>
     * Creates an instance of the class with a message.
     * </p>
     * @param message the error message
     */
    public CompleteProfileActionConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates an instance of the class with a message and cause.
     * </p>
     * @param message the error message
     * @param cause the error cause
     */
    public CompleteProfileActionConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates an instance of the class with a message and the exception data.
     * </p>
     * @param message the error message
     * @param exceptionData the exception data
     */
    public CompleteProfileActionConfigurationException(String message, ExceptionData exceptionData) {
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
    public CompleteProfileActionConfigurationException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
