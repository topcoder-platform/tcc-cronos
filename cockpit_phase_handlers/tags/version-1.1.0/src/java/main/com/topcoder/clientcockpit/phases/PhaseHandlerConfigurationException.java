/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.management.phase.PhaseHandlingException;


/**
 * <p>
 * This exception will be thrown from the constructors of the handlers if while retrieving the configured
 * properties or if a required property is missing.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   The class is mutable and not thread-safe. The application throwing the exception should handle it in a thread-safe
 *   manner.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PhaseHandlerConfigurationException extends PhaseHandlingException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -7099776242365422272L;

    /**
     * <p>
     * Creates a new instance with the given message.
     * </p>
     *
     * @param message the message
     */
    public PhaseHandlerConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance with the given message and cause.
     * </p>
     *
     * @param message the message
     * @param cause the cause
     */
    public PhaseHandlerConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
