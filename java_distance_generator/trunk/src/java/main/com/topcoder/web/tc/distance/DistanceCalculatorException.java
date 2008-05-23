/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This class represents an exception that occurred during distance calculation.
 * </p>
 * <p>
 * This exception will be created and thrown by the DistanceCalculator implementations when it encounters an
 * exception.
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
public class DistanceCalculatorException extends DistanceGeneratorException {
    /**
     * <p>
     * Constructs the exception from the message and data.
     * </p>
     *
     * @param message
     *            A possibly null, possibly empty (trim'd) string representing the message.
     * @param data
     *            A possibly null exception data.
     */
    public DistanceCalculatorException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructs the exception from the message, cause and data.
     * </p>
     *
     * @param message
     *            A possibly null, possibly empty (trim'd) string representing the message.
     * @param cause
     *            A possibly null causing exception.
     * @param data
     *            A possibly null exception data.
     */
    public DistanceCalculatorException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
