/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is the base custom exception of all checked custom exceptions defined in this component. It
 * is not thrown directly by any class.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsManagerException extends BaseCriticalException {
    /**
     * Creates a new instance with the given message.
     * @param message
     *            the message
     */
    public DigitalRunPointsManagerException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with the given message and cause.
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public DigitalRunPointsManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance with the given message and exception data.
     * @param data
     *            the exception data
     * @param message
     *            the message
     */
    public DigitalRunPointsManagerException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance with the given message,cause and exception data.
     * @param data
     *            the exception data
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public DigitalRunPointsManagerException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
