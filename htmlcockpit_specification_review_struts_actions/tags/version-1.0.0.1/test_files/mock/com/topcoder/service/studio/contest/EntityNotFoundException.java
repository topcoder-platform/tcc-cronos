/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception extends the <code>ContestManagementException</code>, and it is thrown
 * when the entity cannot be found in the persistence, but it's supposed to be.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * This class is not thread safe and exception class is not expected to be used concurrently.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class EntityNotFoundException extends ContestManagementException {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = -5171862615676587681L;

    /**
     * <p>Creates exception with the given error message.</p>
     *
     * @param message the error message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>Creates exception with the given error message and cause exception.</p>
     *
     * @param message the error message
     * @param cause the cause exception
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Creates exception with the given error message and exception data.</p>
     *
     * @param message the error message
     * @param data the exception data
     */
    public EntityNotFoundException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>Creates exception with the given error message, cause exception and exception data.</p>
     *
     * @param message the error message
     * @param cause the cause exception
     * @param data the exception data
     */
    public EntityNotFoundException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
