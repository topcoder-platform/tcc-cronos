/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception represents an exception that occurred during distance weighting.
 * </p>
 * <p>
 * This exception will be created and thrown by the DistanceWeighting when it encounters an exception.
 * </p>
 * <p>
 * <b>Note:</b> Current implementations do not throw this exception but it is in place for future implementations.
 * </p>
 * <p>
 * Thread-safety: this class is not thread-safe since its parent is not thread-safe.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class DistanceWeightingException extends DistanceGeneratorException {
    /**
     * Constructs the exception from the message and data. Simply calls super(message, data)
     *
     * @param message
     *            A possibly null, possibly empty (Trim'd) string representing the message
     * @param data
     *            A possibly null exception data
     */
    public DistanceWeightingException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructs the exception from the message, cause and data. Simply calls super(message, cause data)
     *
     * @param message
     *            A possibly null, possibly empty (Trim'd) string representing the message
     * @param cause
     *            A possibly null causing exception
     * @param data
     *            A possibly null exception data
     */
    public DistanceWeightingException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
