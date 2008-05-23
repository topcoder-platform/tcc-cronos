/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This class represents an exception that occurred during distance generation. It will be created and thrown by the
 * DefaultDistanceGenerator when it encounters an exception.
 * </p>
 * <p>
 * Thread-safety: this class is not thread-safe since its parent is not thread-safe.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class DistanceGeneratorException extends BaseRuntimeException {
    /**
     * Constructs the exception from the message and data.
     *
     * @param message
     *            A possibly null, possibly empty (trim'd) string representing the message.
     * @param data
     *            A possibly null exception data.
     */
    public DistanceGeneratorException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructs the exception from the message, cause and data.
     *
     * @param message
     *            A possibly null, possibly empty (trim'd) string representing the message.
     * @param cause
     *            A possibly null causing exception.
     * @param data
     *            A possibly null exception data.
     */
    public DistanceGeneratorException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
