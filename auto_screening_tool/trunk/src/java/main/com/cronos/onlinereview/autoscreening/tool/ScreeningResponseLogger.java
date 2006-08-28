/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This is an interface that is used to describe the contract for logging the
 * screening responses. Implementations are expected to record the result of
 * performing the screening rule, as defined by the screening logic rules.
 * </p>
 * <p>
 * The logging target is implementation dependent. The initial implementation
 * will log into the Screening Database Schema.
 * </p>
 * <p>
 * Thread Safety: - Implementations are not required to be thread safe, as they
 * will be accessed by a single thread.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public interface ScreeningResponseLogger {
    /**
     * <p>
     * Logs the result of executing the provided screening logic against the
     * provided ScreeningTask.
     * </p>
     * @param task
     *            The task to screen.
     * @param screeningLogic
     *            The screeningLogic that was executing against the task.
     * @param ruleResult
     *            The result of executing the screening logic.
     * @throws IllegalArgumentException
     *             if any parameter is null, or ruleResult contains null
     *             elements.
     */
    public void logResponse(ScreeningTask task, ScreeningLogic screeningLogic,
        RuleResult[] ruleResult);

    /**
     * <p>
     * This method is called to provide the logger with information as to the
     * screener which is performing the screening and using this logger.
     * </p>
     * <p>
     * It is expected to be called only once prior to placing the logger into
     * service.
     * </p>
     * @param operator
     *            The id of the screener that is performing the logging.
     * @throws IllegalArgumentException
     *             if the operator is null or an empty String.
     */
    public void setOperator(String operator);
}
