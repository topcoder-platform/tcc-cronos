/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * The helper methods only used in this component. This class and its methods should not be used in
 * other components.
 *
 * @author nowind_lee
 * @version 1.0
 */
public final class Helper {

    /**
     * Private default constructor to prevent creating instance of this helper class.
     */
    private Helper() {
        // do nothing
    }

    /**
     * Check if a string field is null or not.
     *
     * @param field
     *            the field value to check
     * @param fieldName
     *            the field name used in the error message
     * @param log
     *            the logger, can be null
     * @param method
     *            the method signature used in logging
     * @throws CheckProfileCompletenessConfigurationException
     *             if the field value is null
     */
    public static void checkConfigNull(Object field, String fieldName, Log log, String method) {
        if (field == null) {
            throw logError(log, method, new CheckProfileCompletenessConfigurationException(
                fieldName + " should not be null after initializing"));
        }
    }

    /**
     * Check if the field value is null, empty or not.
     *
     * @param field
     *            the field value to check
     * @param fieldName
     *            the field name used in the error message
     * @param log
     *            the logger, can be null
     * @param method
     *            the method signature used in logging
     * @throws CheckProfileCompletenessConfigurationException
     *             if the field value if null or empty
     */
    public static void checkConfigNullEmpty(String field, String fieldName, Log log, String method) {
        checkConfigNull(field, fieldName, log, method);
        if (field.trim().isEmpty()) {
            throw new CheckProfileCompletenessConfigurationException(
                fieldName + " should not be empty after initializing");
        }
    }

    /**
     * Check if the argument is null. IllegalArgumentException will be thrown if the argument is
     * null.
     *
     * @param arg
     *            the argument value
     * @param argName
     *            the argument name, used in error message
     * @param log
     *            the logger, can be null
     * @param method
     *            the method signature used in logging
     * @throws IllegalArgumentException
     *             if argument is null
     */
    public static void notNull(Object arg, String argName, Log log, String method) {
        if (arg == null) {
            throw logError(log, method, new IllegalArgumentException(argName
                + " should not be null"));
        }
    }

    /**
     * Log the entry of a method in DEBUG level. This method is for methods without parameters.
     *
     * @param log
     *            the logger used to log messages, assume it not null
     * @param method
     *            the class and method name of the logging action happened
     */
    public static void logEntry(Log log, String method) {
        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, "[Entering method {" + method + "}]");
        }
    }

    /**
     * Log the entry of a method in DEBUG level. This method is for methods with parameters.
     *
     * @param log
     *            the logger used to log messages, assume it not null
     * @param method
     *            the class and method name of the logging action happened
     * @param paramNames
     *            the parameter names (may contain "%s" patterns)
     * @param params
     *            the parameter values to be logged
     */
    public static void logEntry(Log log, String method, String paramNames, Object... params) {
        logEntry(log, method);
        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, String.format("[Input parameters[{" + paramNames + "}]]", params));
        }
    }

    /**
     * Log the exit of a method in DEBUG level. This method is for methods without return value.
     *
     * @param log
     *            the logger used to log messages, assume it not null
     * @param method
     *            the class and method name of the logging action happened
     */
    public static void logExit(Log log, String method) {
        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, "[Exiting method {" + method + "}]");
        }
    }

    /**
     * Log the exit of a method in DEBUG level. This method is for methods with return value. It
     * will return the logged return value, to make the invoker log and return the return-value in
     * one line of code.
     *
     * @param <T>
     *            the type of logged return value
     * @param log
     *            the logger used to log messages, assume it not null
     * @param method
     *            the class and method name of the logging action happened
     * @param returnValue
     *            the return value to be logged and returned
     * @return the logged return value
     */
    public static <T> T logExit(Log log, String method, T returnValue) {
        logExit(log, method);
        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, String.format("[Output parameter {%s}]", returnValue));
        }
        return returnValue;
    }

    /**
     * Log error message with detail, at ERROR level.
     *
     * @param log
     *            the logger used to log messages, assume it not null
     * @param method
     *            the class and method name of the logging action happened
     * @param detail
     *            the error detail
     */
    public static void logError(Log log, String method, String detail) {
        if (log != null) {
            log.log(Level.ERROR, "[Error in method {" + method + "}: Details {" + detail + "}]");
        }
    }

    /**
     * Log error message with an exception, at ERROR level.
     *
     * @param <T>
     *            the type of excpetion
     * @param log
     *            the logger used to log messages, assume it not null
     * @param method
     *            the class and method name of the logging action happened
     * @param exception
     *            the exception to be logged
     * @return the logged exception
     */
    public static <T extends Exception> T logError(Log log, String method, T exception) {
        if (log != null) {
            log.log(Level.ERROR, exception,
                "[Error in method {" + method + "}: Details {" + exception.toString() + "}]");
        }
        return exception;
    }

}
