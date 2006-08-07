/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;
import com.topcoder.util.errorhandling.BaseException;

/**
 * PhaseManagementException is thrown if there is errors in phase management.
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PhaseManagementException extends BaseException {

    /**
     * <p>
     * Constructs a new instance of PhaseManagementException with the given error message.
     * </p>
     * @param message the error message
     */
    public PhaseManagementException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new instance of ConfigurationException with the given error message and inner
     * cause.
     * </p>
     * @param message the error message
     * @param cause the inner cause
     */
    public PhaseManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
