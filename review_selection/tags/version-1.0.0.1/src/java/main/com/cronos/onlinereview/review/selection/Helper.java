/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Helper class for the component. It provides useful common constants and methods for all the classes in this
 * component.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author xianwenchen
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Represents comma.
     * </p>
     */
    public static final String COMMA = ",";

    /**
     * <p>
     * Represents &quot;loggerName&quot; property key in configuration.
     * </p>
     */
    public static final String LOGGER_NAME_KEY = "loggerName";

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
     * Represents the return value message.
     * </p>
     */
    private static final String MESSAGE_RETURN = "Return value: {0}.";

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
     * Validates the value of a variable. The value can not be null.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     * @throws IllegalArgumentException if the value of the variable is null.
     */
    public static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("The " + name + " is null.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be null.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     * @param log The Log object.
     * @param signature The signature of the method to be logged.
     * @throws IllegalArgumentException if the value of the variable is null.
     */
    public static void checkNull(Object value, String name, Log log, String signature) {
        if (value == null) {
            throw logException(log, signature, new IllegalArgumentException("The " + name + " is null."));
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be null.
     * </p>
     *
     * @param value the value of the variable to be validated.
     * @param name the name of the variable to be validated.
     * @param log The Log object.
     * @param signature The signature of the method to be logged.
     * @throws IllegalStateException if the value of the variable is null.
     */
    public static void checkState(Object value, String name, Log log, String signature) {
        if (value == null) {
            throw logException(log, signature, new IllegalStateException("The " + name + " should be set."));
        }
    }

    /**
     * <p>
     * Logs for entrance into public methods and parameters at DEBUG level.
     * </p>
     *
     * @param log The logger object.
     * @param signature The signature of the method to be logged.
     * @param paramNames The names of parameters to log.
     * @param params The values of parameters to log.
     */
    public static void logEntrance(Log log, String signature, String[] paramNames, Object[] params) {
        if (log == null) {
            return;
        }

        String entranceMessage = MessageFormat.format(MESSAGE_ENTRANCE, new Object[] {signature});
        log.log(Level.DEBUG, entranceMessage);
    }

    /**
     * <p>
     * Logs for exit from public methods and return value at DEBUG level.
     * </p>
     *
     * @param log The logger object.
     * @param signature The signature of the method to be logged.
     * @param value The return value to log (not null).
     * @param timestamp The timestamp while entering the method.
     */
    public static void logExit(Log log, String signature, Object value, long timestamp) {
        if (log == null) {
            return;
        }

        if (value != null) {
            log.log(Level.DEBUG, MessageFormat.format(MESSAGE_RETURN, String.valueOf(value)));
        }

        String exitMessage = MessageFormat.format(MESSAGE_EXIT, new Object[] {signature,
            System.currentTimeMillis() - timestamp});
        log.log(Level.DEBUG, exitMessage);
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * @param <T> the generic type of exception.
     * @param log The logger object.
     * @param signature The signature of the method to be logged.
     * @param exception The exception to log.
     * @return The passed in exception.
     */
    public static <T extends Throwable> T logException(Log log, String signature, T exception) {
        if (log == null) {
            return exception;
        }

        String errorMessage = MessageFormat.format(MESSAGE_ERROR, new Object[] {signature, exception.getMessage()});

        // error message
        log.log(Level.ERROR, errorMessage);

        // convert the exception stack trace into string
        // note: closing the stringWriter has no effect
        StringWriter buffer = new StringWriter();
        exception.printStackTrace(new PrintWriter(buffer));
        log.log(Level.ERROR, buffer.toString());

        return exception;
    }
}
