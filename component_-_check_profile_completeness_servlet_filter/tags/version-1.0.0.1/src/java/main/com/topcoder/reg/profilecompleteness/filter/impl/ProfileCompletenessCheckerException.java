/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessorException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is thrown, when there's any problem with checking profile completeness.
 * <p>
 * <b>Thread-safety</b>
 * <p>
 * The class inherits non thread-safety from the base class, but will be used in a thread-safe
 * manner.
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public class ProfileCompletenessCheckerException extends CheckProfileCompletenessProcessorException {

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = -8038221414486092347L;

    /**
     * The constructor with message parameter.
     *
     * @param message
     *            the exception message
     */
    public ProfileCompletenessCheckerException(String message) {
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
    public ProfileCompletenessCheckerException(String message, Throwable cause) {
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
    public ProfileCompletenessCheckerException(String message, ExceptionData data) {
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
    public ProfileCompletenessCheckerException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
