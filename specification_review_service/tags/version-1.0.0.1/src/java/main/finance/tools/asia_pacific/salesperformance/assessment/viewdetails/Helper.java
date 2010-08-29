/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import org.apache.log4j.Logger;

/**
 * <p>
 * Helper class containing argument checking and utility methods.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private Helper() {

    }

    /**
     * <p>
     * Helper method to check if the value with the specified parameter name is null.
     * </p>
     *
     * @param value
     *            the value to check
     * @param paramName
     *            the parameter name of the value
     * @param methodName
     *            the method name to be used for logging
     * @param log
     *            the logger instance to be used for logging
     *
     * @throws IllegalArgumentException
     *             if value is null
     */
    public static void checkNull(Object value, String paramName, String methodName, Logger log) {
        if (value == null) {
            throw (IllegalArgumentException) logError(methodName, new IllegalArgumentException(
                "[" + paramName + "] cannot be null"), log);
        }
    }

    /**
     * <p>
     * Helper method to check if the string value with the specified parameter name is an
     * empty string.
     * </p>
     *
     * @param value
     *            the string value to check
     * @param paramName
     *            the parameter name of the string value
     * @param methodName
     *            the method name to be used for logging
     * @param log
     *            the logger instance to be used for logging
     *
     * @throws IllegalArgumentException
     *             if value is an empty string
     */
    private static void checkEmpty(String value, String paramName, String methodName, Logger log) {
        if (value.trim().length() == 0) {
            throw (IllegalArgumentException) logError(methodName, new IllegalArgumentException(
                "[" + paramName + "] cannot be an empty string"), log);
        }
    }

    /**
     * <p>
     * Helper method to check if the string value with the specified parameter name is
     * null or an empty string.
     * </p>
     *
     * @param value
     *            the string value to check
     * @param paramName
     *            the parameter name of the string value
     * @param methodName
     *            the method name to be used for logging
     * @param log
     *            the logger instance to be used for logging
     *
     * @throws IllegalArgumentException
     *             if value is null or an empty string
     */
    public static void checkNullOrEmpty(String value, String paramName, String methodName, Logger log) {
        checkNull(value, paramName, methodName, log);
        checkEmpty(value, paramName, methodName, log);
    }

    /**
     * <p>
     * Helper method to check if the numeric value with the specified parameter name is
     * positive.
     * </p>
     *
     * @param value
     *            the numeric value to check
     * @param paramName
     *            the parameter name of the value
     * @param methodName
     *            the method name to be used for logging
     * @param log
     *            the logger instance to be used for logging
     *
     * @throws IllegalArgumentException
     *             if value is not positive (&lt;= 0)
     */
    public static void checkPositive(long value, String paramName, String methodName, Logger log) {
        if (value <= 0) {
            throw (IllegalArgumentException) logError(methodName, new IllegalArgumentException(
                "[" + paramName + "] cannot be non-positive"), log);
        }
    }

    /**
     * <p>
     * Helper method to check if an array of long values with the specified parameter name
     * is null or empty.
     * </p>
     *
     * @param array
     *            the array to check
     * @param paramName
     *            the parameter name of the array
     * @param methodName
     *            the method name to be used for logging
     * @param log
     *            the logger instance to be used for logging
     *
     * @throws IllegalArgumentException
     *             if value is null or an empty array
     */
    public static void checkArray(long[] array, String paramName, String methodName, Logger log) {
        checkNull(array, paramName, methodName, log);
        if (array.length == 0) {
            throw (IllegalArgumentException) logError(methodName, new IllegalArgumentException(
                "[" + paramName + "] cannot be empty"), log);
        }
    }

    /**
     * <p>
     * Helper method to check if an array of short values with the specified parameter
     * name is null or empty.
     * </p>
     *
     * @param array
     *            the array to check
     * @param paramName
     *            the parameter name of the array
     * @param methodName
     *            the method name to be used for logging
     * @param log
     *            the logger instance to be used for logging
     *
     * @throws IllegalArgumentException
     *             if value is null or an empty array
     */
    public static void checkArray(short[] array, String paramName, String methodName, Logger log) {
        checkNull(array, paramName, methodName, log);
        if (array.length == 0) {
            throw (IllegalArgumentException) logError(methodName, new IllegalArgumentException(
                "[" + paramName + "] cannot be empty"), log);
        }
    }

    /**
     * <p>
     * Helper method to log method entry with the method parameters, if any were
     * specified.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param paramNames
     *            the optional method parameter names
     * @param params
     *            the optional method parameters
     * @param log
     *            the Logger instance
     *
     * @return the start time of the method operation in milliseconds
     */
    public static long logEntry(String methodName, String[] paramNames, Object[] params, Logger log) {
        StringBuffer sb = new StringBuffer();
        sb.append("{Method Entry: ").append(methodName);
        if (params != null) {
            sb.append("; Parameters {");
            for (int i = 0; i < params.length; i++) {
                sb.append("[").append(paramNames[i]).append(": {").append(params[i]).append("}]");
                if (i != params.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("}");
        }
        sb.append("}");

        log.debug(sb.toString());
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * Helper method to log method exit with the return value if any.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param returnValue
     *            the optional return value of the method
     * @param startTime
     *            the time in milliseconds at the method entry
     * @param log
     *            the Logger instance
     */
    public static void logExit(String methodName, Object returnValue, long startTime, Logger log) {
        long execTime = System.currentTimeMillis() - startTime;
        String message = "{Method Exit: " + methodName;
        if (returnValue != null) {
            message += "; Return Value [" + returnValue.toString() + "]";
        }
        message += "; Took " + String.valueOf(execTime) + "ms}";

        log.debug(message);
    }

    /**
     * <p>
     * Helper method to log an exception at the ERROR level.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param e
     *            the exception that caused the error
     * @param log
     *            the Logger instance
     *
     * @return the logged exception
     */
    public static Throwable logError(String methodName, Throwable e, Logger log) {
        if (log != null) {
            String message = "{An error occurred in " + methodName + ": [" + e.getMessage() + "]}";
            log.error(message, e);
        }
        return e;
    }
}
