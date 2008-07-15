/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This is a custom exception that will be thrown to indicate that configuration related errors occurred (missing
 * required property or errors that occur when getting the configuration from file).
 * </p>
 *
 * <p>
 * This class is not thread safe since the parent class is not thread safe.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public class DigitalRunTrackManagerBeanConfigurationException extends BaseRuntimeException {
    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3761334156527534506L;

    /**
     * Creates a new instance with the given message.
     *
     * @param message the message
     */
    public DigitalRunTrackManagerBeanConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with the given message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public DigitalRunTrackManagerBeanConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance with the given message and exception data.
     *
     * @param data the exception data
     * @param message the message
     */
    public DigitalRunTrackManagerBeanConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance with the given message,cause and exception data.
     *
     * @param data the exception data
     * @param message the message
     * @param cause the cause
     */
    public DigitalRunTrackManagerBeanConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
