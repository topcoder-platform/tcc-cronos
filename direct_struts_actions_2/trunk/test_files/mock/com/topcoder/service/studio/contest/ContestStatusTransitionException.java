/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception extends the <code>ContestManagementException</code>, and it is thrown
 * when it's not allowed to update contest from one status to another.
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
public class ContestStatusTransitionException extends ContestManagementException {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = -7498990281644606125L;

    /**
     * <p>Creates exception with the given error message.</p>
     *
     * @param message the error message
     */
    public ContestStatusTransitionException(String message) {
        super(message);
    }

    /**
     * <p>Creates exception with the given error message and cause exception.</p>
     *
     * @param message the error message
     * @param cause the cause exception
     */
    public ContestStatusTransitionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Creates exception with the given error message and exception data.</p>
     *
     * @param message the error message
     * @param data the exception data
     */
    public ContestStatusTransitionException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>Creates exception with the given error message, cause exception and exception data.</p>
     *
     * @param message the error message
     * @param cause the cause exception
     * @param data the exception data
     */
    public ContestStatusTransitionException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
