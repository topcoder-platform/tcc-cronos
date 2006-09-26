/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This is a data class that represents the result of screening.
 * </p>
 * <p>
 * It indicates whether the task satisfied the requirements of the rule
 * (successful if this happens), and an optional message providing more details
 * as to how the task satisfied (or failed) the rule requirements. It may also
 * contain a throwable in the case that the screening rule ended in an error.
 * </p>
 * <p>
 * Thread Safety: This class is immutable, and therefore thread-safe.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class RuleResult {

    /**
     * <p>
     * Indicates the success status of the rule. If successful (true), it means
     * that the submission satisfied the conditions of the rule.
     * </p>
     */
    private final boolean success;

    /**
     * <p>
     * Indicates the message of the rule. It is used to convey additional
     * information as to the results of executing the rule.
     * </p>
     */
    private final String message;

    /**
     * <p>
     * This is the error that resulted when applying the screening rule.
     * </p>
     */
    private final Throwable error;

    /**
     * <p>
     * Constructor that allows a RuleResult with an error to be produced.
     * </p>
     * @param error
     *            a RuleResult with an error to be produced.
     * @throws IllegalArgumentException
     *             if the error is null.
     */
    public RuleResult(Throwable error) {
        if (error == null) {
            throw new IllegalArgumentException("error should not be null.");
        }

        this.success = false;
        this.message = null;
        this.error = error;
    }

    /**
     * <p>
     * Creates a RuleResult.
     * </p>
     * @param success
     *            The success status of the rule.
     * @param message
     *            The message to be provided with the rule.
     * @throws IllegalArgumentException
     *             if message is null or an empty String.
     */
    public RuleResult(boolean success, String message) {
        if (message == null) {
            throw new IllegalArgumentException("message should not be null.");
        }
        if (message.trim().length() == 0) {
            throw new IllegalArgumentException("message should not be empty (trimmed).");
        }

        this.success = success;
        this.message = message;
        this.error = null;
    }

    /**
     * <p>
     * Indicates the success status of the rule. If successful (true), it means
     * that the submission satisfied the conditions of the rule.
     * </p>
     * @return the success status of the rule. If successful (true), it means
     *         that the submission satisfied the conditions of the rule.
     */
    public boolean isSuccessful() {
        return success;
    }

    /**
     * <p>
     * Retrieves the message of the rule. It is used to convey additional
     * information as to the results of executing the rule. This returns null if
     * an error was encountered. (Use the message for the error instead).
     * </p>
     * @return the message of the rule.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * Retrieves the error that resulted when applying the screening rule (or
     * null if no error was encountered).
     * </p>
     * @return the error that resulted when applying the screening rule.
     */
    public Throwable getError() {
        return error;
    }
}
