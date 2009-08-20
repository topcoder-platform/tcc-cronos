/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This runtime exception extends the <code>BaseRuntimeException</code>, and it is thrown
 * if the configured value is invalid, it is also used to wrap the underlying exceptions
 * when loading the configured values.
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
public class ContestConfigurationException extends BaseRuntimeException {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = 5798845940396482100L;

    /**
     * <p>Creates exception with the given error message.</p>
     *
     * @param message the error message
     */
    public ContestConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>Creates exception with the given error message and cause exception.</p>
     *
     * @param message the error message
     * @param cause the cause exception
     */
    public ContestConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Creates exception with the given error message and exception data.</p>
     *
     * @param message the error message
     * @param data the exception data
     */
    public ContestConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>Creates exception with the given error message, cause exception and exception data.</p>
     *
     * @param message the error message
     * @param cause the cause exception
     * @param data the exception data
     */
    public ContestConfigurationException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
