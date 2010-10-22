/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

/**
 * <p>
 * This exception is thrown by TogglePipelineIdentificationCycleDaemon if any
 * error occurs while starting scheduler.
 * </p>
 * <p>
 * It extends Exception.
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is not thread-safe because the base class
 * is not thread-safe.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleDaemonException extends Exception {

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleDaemonException.
     * </p>
     * <p>
     * This is the default constructor.
     *</p>
     */
    public TogglePipelineIdentificationCycleDaemonException() {
        // do nothing
    }

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleDaemonException with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public TogglePipelineIdentificationCycleDaemonException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create a new instance of TogglePipelineIdentificationCycleDaemonException
     * with cause.
     * </p>
     *
     * @param cause
     *            the cause of the exception.
     */
    public TogglePipelineIdentificationCycleDaemonException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleDaemonException with error message and
     * cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the cause of the exception.
     */
    public TogglePipelineIdentificationCycleDaemonException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
