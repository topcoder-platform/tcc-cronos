/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.BaseEntity;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

import java.util.List;


/**
 * <p>
 * Helper class for this component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable and so thread safe.
 * </p>
 * @author waits
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Message format string for logging.
     * </p>
     */
    private static final String MESSAGE_FORMAT = "{0} : {1}";

    /**
     * <p>
     * Represents the entrance prefix.
     * </p>
     */
    private static final String ENTRANCE = "Enter method";

    /**
     * <p>
     * Represents the exit prefix.
     * </p>
     */
    private static final String EXIT = "Exit method";

    /**
     * <p>
     * Represents the exception caught message prefix.
     * </p>
     */
    private static final String EXCEPTION = "Exception caught";

    /**
     * <p>
     * Represents the exception caught message prefix.
     * </p>
     */
    private static final String ERROR = "Error Occurs";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Checks if the given BaseEntity is null or has positive id.
     * </p>
     *
     * @param entity the BaseEntity to check
     * @param name the name of the argument.
     * @param logger the log instance, can be null
     *
     * @throws IllegalArgumentException if the entity is is null or has positive id
     */
    public static void checkEntityPositive(BaseEntity entity, String name, Log logger) {
        checkNull(entity, name, logger);

        if (entity.getId() > 0) {
            String msg = name + " 's id is large than zero.";
            logError(msg, logger);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg the argument to check
     * @param name the name of the argument to check
     * @param logger the log instance, can be null
     *
     * @throws IllegalArgumentException if the given Object is null
     */
    public static void checkNull(Object arg, String name, Log logger) {
        if (arg == null) {
            String msg = name + " should not be null.";
            logError(msg, logger);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * <p>
     * Checks if the given list is null or empty or contains null values.
     * </p>
     *
     * @param <T> the class type of the instance in list
     * @param arg the array to check
     * @param name name of the argument.
     *
     * @throws IllegalArgumentException if the arg is null , empty or contains null values.
     */
    public static < T > void checkList(List < T > arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }

        if (arg.isEmpty()) {
            throw new IllegalArgumentException(name + " is empty.");
        }

        for (int i = 0; i < arg.size(); i++) {
            if (arg.get(i) == null) {
                throw new IllegalArgumentException(name + " cannot be contain null values.");
            }
        }
    }

    /**
     * <p>
     * Checks if the given list is null or empty or contains null/negative values.
     * </p>
     *
     * @param arg the array to check
     * @param name name of the argument.
     *
     * @throws IllegalArgumentException if the arg is null, empty or contains null/negative values.
     */
    public static void checkLongList(List < Long > arg, String name) {
        checkList(arg, name);

        for (int i = 0; i < arg.size(); i++) {
            if (arg.get(i).longValue() < 0) {
                throw new IllegalArgumentException(name + " cannot be contain negative values.");
            }
        }
    }

    /**
     * <p>
     * Checks if the given number is less than zero.
     * </p>
     *
     * @param arg the number to check
     * @param name the name of the argument.
     * @param logger the log instance, can be null
     *
     * @throws IllegalArgumentException if the arg is negative
     */
    public static void checkNegative(long arg, String name, Log logger) {
        if (arg < 0) {
            String msg = name + " should not be negative.";
            logError(msg, logger);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * <p>
     * Logs methods entrance info.
     * </p>
     *
     * @param method the method name.
     * @param logger the logger to log method entrance.
     */
    public static void logEntranceInfo(String method, Log logger) {
        if (logger != null) {
            logger.log(Level.DEBUG, MESSAGE_FORMAT, ENTRANCE, method);
        }
    }

    /**
     * <p>
     * Logs methods exit info.
     * </p>
     *
     * @param method the method name.
     * @param logger the logger to log method entrance.
     */
    public static void logExitInfo(String method, Log logger) {
        if (logger != null) {
            logger.log(Level.DEBUG, MESSAGE_FORMAT, EXIT, method);
        }
    }

    /**
     * <p>
     * Logs the Exception message.
     * </p>
     *
     * @param e The Exception caught to be logged.
     * @param logger the logger to log method entrance.
     */
    public static void logException(Exception e, Log logger) {
        if (logger != null) {
            logger.log(Level.ERROR, e, MESSAGE_FORMAT, EXCEPTION, e.getMessage());
        }
    }

    /**
     * <p>
     * Logs the error message.
     * </p>
     *
     * @param message The message caught to be logged.
     * @param logger the logger to log method entrance.
     */
    public static void logError(String message, Log logger) {
        if (logger != null) {
            logger.log(Level.ERROR, MESSAGE_FORMAT, ERROR, message);
        }
    }
}
