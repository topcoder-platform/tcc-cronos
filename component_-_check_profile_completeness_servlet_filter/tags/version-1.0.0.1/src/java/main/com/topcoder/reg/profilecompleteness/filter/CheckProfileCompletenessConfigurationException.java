/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is thrown, when the configuration for profile completeness was not properly set or
 * invalid.
 * <p>
 * <b>Thread-safety</b>
 * <p>
 * The class inherits non thread-safety from the base class, but will be used in a thread-safe
 * manner.
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class CheckProfileCompletenessConfigurationException extends BaseRuntimeException {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 3693259361706262625L;

    /**
     * The constructor with message parameter.
     *
     * @param message
     *            the exception message
     */
    public CheckProfileCompletenessConfigurationException(String message) {
        super(message);
    }

    /**
     * The constructor with message and cause parameter.
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public CheckProfileCompletenessConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor with message and exception data parameter.
     *
     * @param message
     *            the exception message
     * @param data
     *            the exception data
     */
    public CheckProfileCompletenessConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * The constructor with message,cause and exception data parameter.
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     * @param data
     *            the exception data
     */
    public CheckProfileCompletenessConfigurationException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
