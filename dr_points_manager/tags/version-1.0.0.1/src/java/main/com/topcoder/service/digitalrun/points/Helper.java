/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * This is a helper class.
 * <p>
 * Thread Safety: This class is immutable and thread-safe
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is null.
     * @param param
     *            value of the parameter to be checked
     * @param paramName
     *            name of the parameter
     * @throws IllegalArgumentException
     *             if given parameter is null
     */
    public static void checkNull(Object param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(paramName + " is null.");
        }
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is negative.
     * @param param
     *            value of the parameter to be checked
     * @param paramName
     *            name of the parameter
     * @throws IllegalArgumentException
     *             if the parameter is negative.
     */
    public static void checkNegative(long param, String paramName) {
        if (param < 0) {
            throw new IllegalArgumentException(paramName + " is negative.");
        }
    }

    // /**
    // * Checks and throws IllegalArgumentException if the parameter is positive.
    // * @param param
    // * value of the parameter to be checked
    // * @param paramName
    // * name of the parameter
    // * @throws IllegalArgumentException
    // * if the parameter is positive.
    // */
    // public static void checkPositive(long param, String paramName) {
    // if (param > 0) {
    // throw new IllegalArgumentException(paramName + " is positive.");
    // }
    // }

    /**
     * Checks and throws IllegalArgumentException if the string is null or empty.
     * @param param
     *            value of the parameter to be checked
     * @param paramName
     *            name of the parameter
     * @throws IllegalArgumentException
     *             if the string is null or empty.
     */
    public static void checkNullAndEmpty(String param, String paramName) {
        checkNull(param, paramName);
        if (param.trim().length() == 0) {
            throw new IllegalArgumentException(paramName + " is empty.");
        }
    }

    /**
     * <p>
     * Logs methods entrance info.
     * </p>
     * @param logger
     *            the logger to log method entrance.
     * @param methodName
     *            the method name.
     */
    public static void logEntranceInfo(Log logger, String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Entering method {" + methodName + "}");
        }
    }

    /**
     * <p>
     * Logs methods exit info.
     * </p>
     * @param logger
     *            the logger to log method exit.
     * @param methodName
     *            the method name.
     */
    public static void logExitInfo(Log logger, String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Exiting method {" + methodName + "}");
        }
    }

    /**
     * <p>
     * Logs the Exception message.
     * </p>
     * @param logger
     *            the logger to log method error.
     * @param e
     *            The Exception caught to be logged.
     * @param methodName
     *            the method name.
     */
    public static void logException(Log logger, Exception e, String methodName) {
        if (logger != null) {
            logger.log(Level.ERROR, e, "Error in method {" + methodName + "}: Details {" + e.getMessage()
                    + "}");
        }
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is null. This method will log the
     * exception.
     * @param param
     *            value of the parameter to be checked
     * @param paramName
     *            name of the parameter
     * @throws IllegalArgumentException
     *             if given parameter is null
     * @param logger
     *            the logger to log method error.
     * @param methodName
     *            the method name.
     */
    public static void checkNullWithLog(Object param, String paramName, Log logger, String methodName) {
        if (param == null) {
            IllegalArgumentException iae = new IllegalArgumentException(paramName + " is null");
            logException(logger, iae, methodName);
            throw iae;
        }
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is negative. This method will log
     * the exception.
     * @param param
     *            value of the parameter to be checked
     * @param paramName
     *            name of the parameter
     * @throws IllegalArgumentException
     *             if given parameter is negative
     * @param logger
     *            the logger to log method error.
     * @param methodName
     *            the method name.
     */
    public static void checkNegativeWithLog(long param, String paramName, Log logger, String methodName) {
        if (param < 0) {
            IllegalArgumentException iae = new IllegalArgumentException(paramName + " is negative");
            logException(logger, iae, methodName);
            throw iae;
        }
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is positive. This method will log
     * the exception.
     * @param param
     *            value of the parameter to be checked
     * @param paramName
     *            name of the parameter
     * @throws IllegalArgumentException
     *             if given parameter is positive
     * @param logger
     *            the logger to log method error.
     * @param methodName
     *            the method name.
     */
    public static void checkPositiveWithLog(long param, String paramName, Log logger, String methodName) {
        if (param > 0) {
            IllegalArgumentException iae = new IllegalArgumentException(paramName + " is positive");
            logException(logger, iae, methodName);
            throw iae;
        }
    }

    /**
     * Throw exception with log.
     * @param message
     *            the message
     * @param e
     *            the exception
     * @param logger
     *            the logger to log method error.
     * @param methodName
     *            the method name.
     * @throws DigitalRunPointsManagerPersistenceException
     *             the exception
     */
    public static void throwPersitenceExceptionWithLog(String message, Exception e, Log logger,
            String methodName) throws DigitalRunPointsManagerPersistenceException {
        DigitalRunPointsManagerPersistenceException ex;
        if (e != null) {
            ex = new DigitalRunPointsManagerPersistenceException(message, e);
        } else {
            ex = new DigitalRunPointsManagerPersistenceException(message);
        }
        Helper.logException(logger, ex, methodName);
        throw ex;
    }

    /**
     * Throw exception with log.
     * @param message
     *            the message
     * @param e
     *            the exception
     * @param logger
     *            the logger to log method error.
     * @param methodName
     *            the method name.
     * @throws EntityNotFoundException
     *             the exception
     */
    public static void throwEntityNotFoundExceptionWithLog(String message, Exception e, Log logger,
            String methodName) throws EntityNotFoundException {
        EntityNotFoundException ex;
        if (e != null) {
            ex = new EntityNotFoundException(message, e);
        } else {
            ex = new EntityNotFoundException(message);
        }

        Helper.logException(logger, ex, methodName);
        throw ex;
    }

}
