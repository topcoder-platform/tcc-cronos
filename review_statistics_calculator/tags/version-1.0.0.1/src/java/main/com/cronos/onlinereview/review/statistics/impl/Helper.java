/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.text.MessageFormat;
import java.util.Date;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.topcoder.management.review.data.Review;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Helper class for the component. It provides useful common constants and methods for all the classes in this
 * component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
    /**
     * <p>
     * Represents the entrance message.
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering the method [{0}].";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting the method [{0}], time spent in the method: {1} milliseconds.";

    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in the method [{0}], Details: {1}";

    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalArgumentException if the value of the variable is <code>null</code>.
     */
    static final void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' should not be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalArgumentException if the value of the variable is <code>null</code>.
     */
    static final void checkNull(Log logger, String signature, Object value, String name) {
        if (value == null) {
            throw logException(logger, signature, new IllegalArgumentException("'" + name + "' should not be null."),
                "'" + name + "' should not be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalStateException if the value of the variable is <code>null</code>.
     */
    static void checkState(Log logger, String signature, Object value, String name) {
        if (value == null) {
            throw logException(logger, signature, new IllegalStateException("'" + name + "' should not be null."),
                "'" + name + "' should not be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string. The value can not be an empty string.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws StatisticsCalculatorConfigurationException if the given string is an empty string.
     */
    static final void checkEmptyLogger(String value, String name) {
        if ((value != null) && (value.trim().length() == 0)) {
            throw new StatisticsCalculatorConfigurationException("'" + name + "' should not be an empty string.");
        }
    }

    /**
     * <p>
     * Validates the value of a string. The value can not be <code>null</code> or an empty string.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws StatisticsCalculatorConfigurationException if the given string is an empty string or null.
     */
    static final void checkNullOrEmpty(Log logger, String signature, String value, String name) {
        checkNull(logger, signature, value, name);

        if (value.trim().length() == 0) {
            throw logException(logger, signature, new StatisticsCalculatorConfigurationException("'" + name
                + "' should not be empty."), "'" + name + "' should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether the given value is negative. The value should not be negative.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalArgumentException if the given value of the variable is negative.
     */
    static final void checkPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException("'" + name + "' should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether the given value is negative. The value should not be negative.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalArgumentException if the given value of the variable is negative.
     */
    static final void checkNegative(double value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException("'" + name + "' should not be negative.");
        }
    }

    /**
     * Check if Review array is valid.
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged
     * @param reviews the reviews with evaluation details (outer array - reviews for each reviewer, inner array -
     *            reviews of one reviewer for all submissions)
     *
     * @throws IllegalArgumentException if reviews is null or contains null, lengths of all inner arrays are not
     *             equal, inner array contains null
     */
    static final void checkReview(Log logger, String signature, Review[][] reviews) {
        Helper.checkNull(logger, signature, reviews, "reviews");

        for (int i = 0; i < reviews.length; i++) {
            Helper.checkArray(logger, signature, reviews[i], "reviews[" + i + "]", true);

            if (reviews[i].length != reviews[0].length) {
                throw logException(logger, signature, new IllegalArgumentException(
                    "lengths of all inner arrays are not equal."), "lengths of all inner arrays are not equal.");
            }
        }
    }

    /**
     * <p>
     * Validates the value of a list. The value can not be <code>null</code> or contains <code>null</code>, or
     * elements are not of given type.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param array the array to be validated.
     * @param name the name of the list to be validated.
     * @param canEmpty whether the list can be empty.
     *
     * @throws IllegalArgumentException if the given list is <code>null</code> or contains <code>null</code> element,
     *             or elements are not of given type, or there is not element if the list can not be empty.
     */
    static final void checkArray(Log logger, String signature, Object[] array, String name, boolean canEmpty) {
        Helper.checkNull(logger, signature, array, name);

        int size = array.length;
        if (size == 0) {
            if (!canEmpty) {
                throw new IllegalArgumentException("'" + name + "' should not be empty.");
            }
            // Empty array
            return;
        }

        for (int i = 0; i < size; i++) {
            Object element = array[i];

            if (element instanceof Long) {
                Helper.checkNull(logger, signature, (Long) element, "element of " + name);
                Helper.checkPositive(logger, signature, (Long) element, "element of " + name);
            } else {
                Helper.checkNull(logger, signature, element, "element of " + name);
            }
        }
    }

    /**
     * <p>
     * Checks whether the given value is not positive.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalArgumentException if the given value of the variable is negative.
     */
    static final void checkPositive(Log logger, String signature, long value, String name) {
        if (value <= 0) {
            throw logException(logger, signature, new IllegalArgumentException("'" + name + "' should be positive."),
                "'" + name + "' should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether the given value is negative. The value should not be negative.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     *
     * @throws IllegalArgumentException if the given value of the variable is negative.
     */
    static final void checkNegative(Log logger, String signature, double value, String name) {
        if (value < 0) {
            throw logException(logger, signature, new IllegalArgumentException("'" + name
                + "' should not be negative."), "'" + name + "' should not be negative.");
        }
    }

    /**
     * <p>
     * Logs for entrance into public methods at <code>DEBUG</code> level, parameters at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param paramNames The names of parameters to log.
     * @param params The values of parameters to log.
     */
    static void logEntrance(Log logger, String signature, String[] paramNames, Object[] params) {
        if (logger == null) {
            return;
        }

        String entranceMessage = MessageFormat.format(MESSAGE_ENTRANCE, new Object[] {signature});
        logger.log(Level.DEBUG, entranceMessage);

        if ((paramNames != null) && (params != null)) {
            logParameters(logger, paramNames, params);
        }
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level, return value at <code>DEBUG</code> level.
     * </p>
     *
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param value The return value to log (not <code>null</code>).
     * @param timestamp The timestamp while entering the method.
     */
    static void logExit(Log logger, String signature, Object[] value, Date timestamp) {
        if (logger == null) {
            return;
        }

        if (value != null) {
            logReturnValue(logger, value);
        }

        Date now = new Date();
        String exitMessage = MessageFormat.format(MESSAGE_EXIT, new Object[] {signature,
            new Long(now.getTime() - timestamp.getTime())});
        logger.log(Level.DEBUG, exitMessage);
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * @param logger The logger object.
     * @param signature The signature of the method to be logged.
     * @param exception The exception to log.
     * @param message The message to log.
     * @param <T> the type of the Exception.
     *
     * @return The passed in exception.
     */
    static <T extends Throwable> T logException(Log logger, String signature, T exception, String message) {
        if (logger == null) {
            return exception;
        }

        String errorMessage = MessageFormat.format(MESSAGE_ERROR, new Object[] {signature, message});
        logger.log(Level.ERROR, errorMessage);
        logger.log(Level.ERROR, exception);

        return exception;
    }

    /**
     * <p>
     * Logs the parameters at <code>DEBUG</code> level.
     * </p>
     *
     *
     * @param logger The logger object (not <code>null</code>).
     * @param paramNames The names of parameters to log (not <code>null</code>).
     * @param params The values of parameters to log (not <code>null</code>).
     */
    static void logParameters(Log logger, String[] paramNames, Object[] params) {
        if (logger == null) {
            return;
        }

        StringBuffer sb = new StringBuffer("Input parameters: {");

        for (int i = 0; i < params.length; i++) {

            if (i > 0) {
                // Append a comma
                sb.append(", ");
            }

            sb.append(paramNames[i] + " : " + String.valueOf(params[i]));
        }
        sb.append("}.");

        logger.log(Level.DEBUG, sb.toString());
    }

    /**
     * <p>
     * Logs the return value at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger The logger object (not <code>null</code>).
     * @param value The return value to log (not <code>null</code>).
     */
    static void logReturnValue(Log logger, Object[] value) {
        StringBuffer sb = new StringBuffer("Return value: {");

        for (int i = 0; i < value.length; i++) {

            if (i > 0) {
                // Append a comma
                sb.append(", ");
            }

            sb.append(String.valueOf(value[i]));
        }
        sb.append("}.");

        logger.log(Level.DEBUG, sb.toString());
    }
}
