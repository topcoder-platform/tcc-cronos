/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import org.apache.log4j.Logger;

/**
 * <p>
 * This is a helper class use in packages of hr.leads.services.ejb and hr.leads.services.jobs.
 * </p>
 *
 * <p>
 * It mainly provides the methods to check the arguments and logging.
 * </p>
 *
 * <p>
 * <b>Thread-safety:</b> No fields in this class so it is always thread-safe.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public final class ReportHelper {

    /**
     * <p>
     * Represents the private constructor to prevent creating an instance.
     * </p>
     */
    private ReportHelper() {
        // do nothing
    }

    /**
     * <p>
     * Checks if the string argument is valid. If it is null or empty after trim, IllegalArgumentException is thrown.
     * </p>
     *
     * @param logger the logger to log the error.
     * @param methodName the name of the method to check its parameters.
     *
     * @param argument the value of the argument.
     * @param argumentName the name of the argument.
     *
     * @throws IllegalArgumentException if the string is null or empty.
     */
    public static void checkNullOrEmpty(Logger logger, String methodName, String argument, String argumentName) {
        if (argument == null) {
            // log the error
            logError(logger, methodName, "The argument " + argumentName + " cannot be null.");
            throw new IllegalArgumentException("The argument " + argumentName + " cannot be null.");
        }
        if (argument.trim().length() == 0) {
            logError(logger, methodName, "The argument " + argumentName + " cannot be empty.");
            throw new IllegalArgumentException("The argument " + argumentName + " cannot be empty.");
        }
    }

    /**
     * <p>
     * Checks if the argument is valid. If it is null, IllegalArgumentException is thrown.
     * </p>
     *
     * @param logger the logger to log the error.
     * @param methodName the name of the method to check its parameters.
     *
     * @param argument the value of the argument.
     * @param argumentName the name of the argument.
     *
     * @throws IllegalArgumentException if the argument is null.
     */
    public static void checkNull(Logger logger, String methodName, Object argument, String argumentName) {
        if (argument == null) {
            logError(logger, methodName, "The argument " + argumentName + " cannot be null.");
            throw new IllegalArgumentException("The argument " + argumentName + " cannot be null.");
        }
    }

    /**
     * <p>
     * Checks if the string array is valid. If it contains string which is null
     * or empty after trim, IllegalArgumentException is thrown.
     * </p>
     *
     * @param logger the logger to log the error.
     * @param methodName the name of the method to check its parameters.
     *
     * @param argument
     *            the value of the argument.
     * @param argumentName
     *            the name of the argument.
     * @throws IllegalArgumentException
     *             if contains string which is null or empty after trim.
     */
    static void checkContainsNullOrEmpty(Logger logger, String methodName, String[] argument, String argumentName) {
        if (argument == null) {
            logError(logger, methodName, "The argument " + argumentName + " cannot be null.");
            throw new IllegalArgumentException("The argument " + argumentName + " cannot be null.");
        }

        if (argument.length == 0) {
            logError(logger, methodName, "The argument " + argumentName + " cannot be empty.");
            throw new IllegalArgumentException("The argument " + argumentName + " cannot be empty.");
        }

        for (String string : argument) {
            checkNullOrEmpty(logger, methodName, string, "element in " + argumentName);
        }
    }

    /**
     * <p>
     * Logs the method entrance on debug level.
     * </p>
     *
     * @param logger the logger.
     * @param methodName the name of the methods.
     */
    public static void logEntrance(Logger logger, String methodName) {
        if (logger != null) {
            logger.debug("[Entering method: {" + methodName + "}]");
        }
    }

    /**
     * <p>
     * Logs the request parameters on debug level.
     * </p>
     *
     * @param logger the logger.
     * @param params the value of the parameters.
     * @param names the name of the parameters.
     */
    public static void logParameters(Logger logger, Object[] params, String[] names) {
        if (logger != null) {
            StringBuffer messsageBuffer = new StringBuffer();
            messsageBuffer.append("[Input parameters[");
            for (int i = 0; i < params.length; i++) {
                messsageBuffer.append("{");
                messsageBuffer.append(names[i]);
                messsageBuffer.append(":");
                messsageBuffer.append(printObjectValue(params[i]));
                messsageBuffer.append("}");
            }
            messsageBuffer.append("]]");
            logger.debug(messsageBuffer.toString());
        }
    }

    /**
     * <p>
     * Logs the exceptions in error level.
     * </p>
     *
     * @param logger the logger.
     * @param methodName the name of the method.
     * @param message the message of the error.
     */
    public static void logError(Logger logger, String methodName, String message) {
        if (logger != null) {
            logger.error("[Error in method {" + methodName + "}:Details{" + message + "}]");
        }
    }

    /**
     * <p>
     * Logs the output in debug level.
     * </p>
     * @param logger the logger.
     * @param value the value of the output.
     */
    public static void logOutput(Logger logger, Object value) {
        if (logger != null) {
            String message = "[Output parameter[" + "{" + printObjectValue(value) + "}]";
            logger.debug(message);
        }
    }

    /**
     * <p>
     * Gets the string form of the value.
     * </p>
     * @param object the value of the object.
     * @return a string form of the value.
     */
    private static String printObjectValue(Object object) {
        if (object == null) {
            return "null";
        }
        return object.toString();
    }

    /**
     * <p>
     * Logs the method exit in debug level.
     * </p>
     *
     * @param logger the logger.
     * @param methodName the name of the method.
     */
    public static void logExit(Logger logger, String methodName) {
        if (logger != null) {
            logger.debug("[Exiting method: {" + methodName + "}]");
        }
    }
}
