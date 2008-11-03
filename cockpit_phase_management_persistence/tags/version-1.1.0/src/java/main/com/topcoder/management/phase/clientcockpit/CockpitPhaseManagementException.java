/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.PhaseManagementException;


/**
 * <p>
 * This exception is thrown by various {@link CockpitPhaseManager} methods when an error occurs. If the error
 * was the result of an internal exception (such as a persistence problem), the
 * <code>CockpitPhaseManagementException</code> will have an associated wrapped exception. It is used to
 * wrap the exception thrown by the <code>ContestManager</code> and the exceptions thrown by the
 * <code>PhaseHandler</code>s.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is not thread safe since the base class
 * <code>PhaseManagementException</code> is not thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class CockpitPhaseManagementException extends PhaseManagementException {
    /**
     * <p>
     * This constructor initializes a new exception with the given message.
     * </p>
     *
     * @param message the message containing a description of why the exception was thrown. May be
     *        <code>null</code> or empty string (trimmed).
     */
    public CockpitPhaseManagementException(String message) {
        super(message);
    }

    /**
     * <p>
     * This constructor initializes a new exception with the given message and cause.
     * </p>
     *
     * @param message the message containing a description of why the exception was thrown. May be
     *        <code>null</code> or empty string(trimmed).
     * @param cause the initial <code>java.lang.Throwable</code> reason which triggered this exception to be
     *        thrown. It may be <code>null</code>.
     */
    public CockpitPhaseManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
