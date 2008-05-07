/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception extends the <code>{@link BaseCriticalException}</code>. This class should be the parent exception
 * of (almost) all exceptions thrown by <code>{@link PrerequisiteService}</code>.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PrerequisiteServiceException extends BaseCriticalException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -8114377431176924816L;

    /**
     * <p>
     * Creates a <code>PrerequisiteServiceException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message
     */
    public PrerequisiteServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a <code>PrerequisiteServiceException</code> instance with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     */
    public PrerequisiteServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a <code>PrerequisiteServiceException</code> instance with error message and exception data.
     * </p>
     *
     * @param message
     *            the error message
     * @param data
     *            the exception data
     */
    public PrerequisiteServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a <code>PrerequisiteServiceException</code> instance with error message and inner cause and exception
     * data.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     */
    public PrerequisiteServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }

}
