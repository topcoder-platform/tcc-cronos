/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import org.quartz.JobExecutionException;

/**
 * <p>
 * This exception is thrown by TogglePipelineIdentificationCycleJob if any error
 * occurs while updating pipeline cycle status.
 * </p>
 * <p>
 * It extends JobExecutionException.
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is not thread-safe because the base class
 * is not thread-safe.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJobExecutionException extends
        JobExecutionException {

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleJobExecutionException.
     *</p>
     */
    public TogglePipelineIdentificationCycleJobExecutionException() {
        // do nothing
    }

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleJobExecutionException with error
     * message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public TogglePipelineIdentificationCycleJobExecutionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleJobExecutionException with cause.
     * </p>
     *
     * @param cause
     *            the cause of the exception.
     */
    public TogglePipelineIdentificationCycleJobExecutionException(
            Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Creates a new instance of
     * TogglePipelineIdentificationCycleJobExecutionException with error message
     * and cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the cause of the exception.
     */
    public TogglePipelineIdentificationCycleJobExecutionException(
            String message, Throwable cause) {
        super(message, cause);
    }
}
