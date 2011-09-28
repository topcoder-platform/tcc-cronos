/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Helper class for this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public final class Helper {

    /** This is the private constructor. */
    private Helper() {

    }

    /**
     * Check whether the dependency was properly initialized.
     *
     * @param data The data will be checked.
     * @param name The name of the dependency to check.
     * @throws BillingCostActionConfigurationException If data is null.
     */
    static void checkConfig(Object data, String name) {
        if (data == null) {
            throw new BillingCostActionConfigurationException(name
                    + " should not be null.");
        }
    }


    /**
     * <p>
     * Logs the entrance operation of the specified method.
     * </p>
     *
     * @param methodName the method name
     * @param log the logging instance
     */
    public static void logEnterMethod(Log log, String methodName) {
        log.log(Level.DEBUG, "[Entering the method {" + methodName + "}]");
    }

    /**
     * <p>
     * Logs the exit operation of the specified method.
     * </p>
     *
     * @param methodName the method name
     * @param log the logging instance
     * @param returnObj the return object
     */
    public static void logExitMethod(Log log, String methodName,
            Object returnObj) {
        StringBuffer message = new StringBuffer("[Exiting method {" + methodName + "}");
        if (returnObj != null) {
            message.append(" Output parameter {").append(String.valueOf(returnObj)).append("}");
        }
        log.log(Level.DEBUG, message.append("]").toString());
    }

    /**
     * Log and throw the exception.
     *
     * @param <T> the exception
     * @param log the log instance
     * @param methodName method name
     * @param exception the exception to throw
     * @return the exception
     * @throws T the exception
     */
    public static <T extends Exception> T logAndThrow(Log log,
            String methodName, T exception) throws T {
        log.log(Level.ERROR, "[Error in method {" + methodName + "}: Details {"
                + exception.getMessage() + "}]", exception);
        throw exception;
    }
}
