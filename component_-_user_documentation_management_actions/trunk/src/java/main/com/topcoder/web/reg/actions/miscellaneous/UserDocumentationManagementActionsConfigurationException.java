/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is thrown for any configuration error that occurs in this component.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because the base class is not thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class UserDocumentationManagementActionsConfigurationException extends BaseRuntimeException {
    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = -5708202978788517713L;

    /**
     * <p>
     * Create an instance of the class with a message.
     * </p>
     * @param message
     *            the error message
     */
    public UserDocumentationManagementActionsConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create an instance of the class with a message and the cause.
     * </p>
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     */
    public UserDocumentationManagementActionsConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Create an instance of the class with a message and the exception data.
     * </p>
     * @param message
     *            the error message
     * @param exceptionData
     *            the exception data
     */
    public UserDocumentationManagementActionsConfigurationException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * <p>
     * Create an instance of the class with a message, cause and the exception data.
     * </p>
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     * @param exceptionData
     *            the exception data
     */
    public UserDocumentationManagementActionsConfigurationException(String message, Throwable cause,
            ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
