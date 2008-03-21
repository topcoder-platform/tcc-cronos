/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if the authorization check for a user request fails.
 * </p>
 * <p>
 * This exception must have the "@ApplicationException(rollback=true)" annotation so that if any error occurs the
 * transaction could be automatically rolled back by EJB container. It also makes sure the exception is directly thrown
 * back to client without any wrapping.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class AuthorizationException extends DocumentManagerException {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -5161495533096800780L;

    /**
     * <p>
     * Creates a new exception instance with the given error message.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     */
    public AuthorizationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new exception instance with the given message and cause.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param cause
     *            Underlying cause of the error. Can be null, which means that initial exception is nonexistent or
     *            unknown.
     */
    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception instance with the given error message and data.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param data
     *            The additional data to attach to the exception - This can be null and if this is null, a new
     *            ExceptionData() will be used instead.
     */
    public AuthorizationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new exception instance with the given error message, cause and the additional data to attach.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param cause
     *            Underlying cause of the error. Can be null, which means that initial exception is nonexistent or
     *            unknown.
     * @param data
     *            The additional data to attach to the exception - This can be null and if this is null, a new
     *            ExceptionData() will be used instead.
     */
    public AuthorizationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
