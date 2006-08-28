/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Map;

/**
 * <p>
 * This class is used to encapsulate screening logic.
 * </p>
 * <p>
 * It separates itself from the actual screening rule in order to disassociate
 * the severity of a rule from the procedure that is needed to check it. This
 * allows for a more flexible configuration if the client decides to change the
 * severity of a rule (for example, a rule mandating presence of Sequence
 * Diagrams may cause failure in one situation, but may only cause a warning in
 * a different situation).
 * </p>
 * <p>
 * This class simply acts as an additional wrapper around the ScreeningRule
 * class. The ScreeningRule class provides the actual programming logic that is
 * required, and this class adds the information on what to do based on the
 * results of performing the screening.
 * </p>
 * <p>
 * Thread Safety: This class is not required to be thread safe, because we
 * expect only a single thread to be working on it.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ScreeningLogic {

    /**
     * <p>
     * This is the ScreeningRule instance that encapsulates the actual procedure
     * that will be performed on the submission to determine the
     * ResponseSeverity and ResponseCode to use.
     * </p>
     */
    private ScreeningRule screeningRule;

    /**
     * <p>
     * This is the configured level of the rule in the case that the Rule has
     * successfully executed and returns a 'True' result.
     * </p>
     */
    private final ResponseLevel successSeverity;

    /**
     * <p>
     * This is the configured level of the rule in the case that the Rule has
     * successfully executed and returns a 'False' result.
     * </p>
     */
    private final ResponseLevel failureSeverity;

    /**
     * <p>
     * This is the configured Level of the rule in the case that the Rule has
     * thrown an exception.
     * </p>
     */
    private final ResponseLevel errorSeverity;

    /**
     * <p>
     * This is the configured response code of the rule in the case that the
     * Rule has successfully executed and returns a 'True' result. It defaults
     * to Long.MIN_VALUE if not specified in constructor.
     * </p>
     */
    private final long successResponseCode;

    /**
     * <p>
     * This is the configured response code of the rule in the case that the
     * Rule has successfully executed and returns a 'false' result. It defaults
     * to Long.MIN_VALUE if not specified in constructor.
     * </p>
     */
    private final long failureResponseCode;

    /**
     * <p>
     * This is the configured response code of the rule in the case that the
     * Rule has thrown an exception. It defaults to Long.MIN_VALUE if not
     * specified in constructor.
     * </p>
     */
    private final long errorResponseCode;

    /**
     * <p>
     * Constructor that allows for the construction of a ScreeningRule and
     * specified severities. The response code is ignored.
     * </p>
     * @param screeningRule
     *            The rule to apply.
     * @param successSeverity
     *            The severity in case of success.
     * @param failureSeverity
     *            The severity in case of failure.
     * @param errorSeverity
     *            The severity in case of an error.
     * @throws IllegalArgumentException
     *             if any parameter is null.
     */
    public ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
        ResponseLevel failureSeverity, ResponseLevel errorSeverity) {
        this(screeningRule, successSeverity, failureSeverity, errorSeverity, Long.MIN_VALUE,
            Long.MIN_VALUE, Long.MIN_VALUE);
    }

    /**
     * <p>
     * Constructor that allows for the construction of a ScreeningRule and
     * specified severities and response codes.
     * </p>
     * @param screeningRule
     *            The rule to apply.
     * @param successSeverity
     *            The severity in case of success.
     * @param failureSeverity
     *            The severity in case of failure.
     * @param errorSeverity
     *            The severity in case of an error.
     * @param successCode
     *            The response code in case of success.
     * @param failureCode
     *            The response code in case of failure.
     * @param errorCode
     *            The response code in case of an error.
     * @throws IllegalArgumentException
     *             if any non-long parameter is null.
     */
    public ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
        ResponseLevel failureSeverity, ResponseLevel errorSeverity, long successCode,
        long failureCode, long errorCode) {
        // validate arguments
        if (screeningRule == null) {
            throw new IllegalArgumentException("screeningRule should not be null.");
        }
        if (successSeverity == null) {
            throw new IllegalArgumentException("successSeverity should not be null.");
        }
        if (failureSeverity == null) {
            throw new IllegalArgumentException("failureSeverity should not be null.");
        }
        if (errorSeverity == null) {
            throw new IllegalArgumentException("errorSeverity should not be null.");
        }

        this.screeningRule = screeningRule;
        this.successSeverity = successSeverity;
        this.failureSeverity = failureSeverity;
        this.errorSeverity = errorSeverity;

        this.successResponseCode = successCode;
        this.failureResponseCode = failureCode;
        this.errorResponseCode = errorCode;
    }

    /**
     * <p>
     * Retrieves the ScreeningRule instance that encapsulates the actual
     * procedure that will be performed on the submission to determine the
     * ResponseSeverity and ResponseCode to use.
     * </p>
     * @return the ScreeningRule instance that encapsulates the actual procedure
     *         that will be performed on the submission to determine the
     *         ResponseSeverity and ResponseCode to use.
     */
    public ScreeningRule getScreeningRule() {
        return screeningRule;
    }

    /**
     * <p>
     * Retrieves the configured Level of the rule in the case that the Rule has
     * successfully executed and returns a 'True' result.
     * </p>
     * @return the configured Level of the rule in the case that the Rule has
     *         successfully executed and returns a 'True' result.
     */
    public ResponseLevel getSuccessLevel() {
        return successSeverity;
    }

    /**
     * <p>
     * Retrieves the configured Level of the rule in the case that the Rule has
     * successfully executed and returns a 'False' result.
     * </p>
     * @return the configured Level of the rule in the case that the Rule has
     *         successfully executed and returns a 'False' result.
     */
    public ResponseLevel getFailureLevel() {
        return failureSeverity;
    }

    /**
     * <p>
     * Retrieves the configured Level of the rule in the case that the Rule has
     * thrown an exception.
     * </p>
     * @return the configured Level of the rule in the case that the Rule has
     *         thrown an exception.
     */
    public ResponseLevel getErrorLevel() {
        return errorSeverity;
    }

    /**
     * <p>
     * Retrieves the configured response code of the rule in the case that the
     * Rule has successfully executed and returns a 'True' result. It defaults
     * to Long.MIN_VALUE if not specified in constructor.
     * </p>
     * @return the configured response code of the rule in the case that the
     *         Rule has successfully executed and returns a 'True' result.
     */
    public long getSuccessResponseCode() {
        return successResponseCode;
    }

    /**
     * <p>
     * Retrieves the configured response code of the rule in the case that the
     * Rule has successfully executed and returns a 'false' result. It defaults
     * to Long.MIN_VALUE if not specified in constructor.
     * </p>
     * @return the configured response code of the rule in the case that the
     *         Rule has successfully executed and returns a 'false' result.
     */
    public long getFailureResponseCode() {
        return failureResponseCode;
    }

    /**
     * <p>
     * Retrieves the configured response code of the rule in the case that the
     * Rule has thrown an exception. It defaults to Long.MIN_VALUE if not
     * specified in constructor.
     * </p>
     * @return the configured response code of the rule in the case that the
     *         Rule has thrown an exception.
     */
    public long getErrorResponseCode() {
        return errorResponseCode;
    }

    /**
     * <p>
     * This is a convenience method that will screen the provided task and
     * context by delegating to the ScreeningRule.
     * </p>
     * @return The results of screening.
     * @param screeningTask
     *            The task to screen.
     * @param context
     *            A map containing the screening context. It may be modified
     *            throughout.
     * @throws IllegalArgumentException
     *             if the screeningTask or context is null.
     */
    public RuleResult[] screen(ScreeningTask screeningTask, Map context) {
        return screeningRule.screen(screeningTask, context);
    }

    /**
     * <p>
     * This is a convenience method that will cleanup the provided task and
     * context by delegating to the ScreeningRule.
     * </p>
     * @param screeningTask
     *            The screeningTask for which cleanup must be performed.
     * @param context
     *            The screening context which may contain some resources to be
     *            released.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public void cleanup(ScreeningTask screeningTask, Map context) {
        screeningRule.cleanup(screeningTask, context);
    }
}
