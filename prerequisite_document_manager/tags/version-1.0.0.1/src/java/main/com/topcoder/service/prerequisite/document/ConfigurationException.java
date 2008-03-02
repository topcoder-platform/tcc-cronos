/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if the configuration is bad: missing required properties or properties in wrong format.
 * </p>
 * <p>
 * This exception must have the "@ApplicationException" annotation so that if any error occurs the exception is directly
 * thrown back to client without any wrapping.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException
public class ConfigurationException extends BaseRuntimeException {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 681341708420844200L;

    /**
     * Creates a new exception instance with the given error message.
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception instance with the given message and cause.
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param cause
     *            Underlying cause of the error. Can be null, which means that initial exception is nonexistent or
     *            unknown.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception instance with the given error message and data.
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param data
     *            The additional data to attach to the exception - This can be null and if this is null, a new
     *            ExceptionData() will be used instead.
     */
    public ConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new exception instance with the given error message, cause and the additional data to attach.
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
    public ConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
