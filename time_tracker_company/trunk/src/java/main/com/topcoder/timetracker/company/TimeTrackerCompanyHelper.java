/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * <p>
 * Defines utilities for this component. Since this class will be accessed via multi-packages, this class is declared
 * in public scope.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public final class TimeTrackerCompanyHelper {
    /**
     * Private constructor to prevent this class be instantiated.
     */
    private TimeTrackerCompanyHelper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>long</code>. The value should be positive.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is not positive.
     */
    public static void validatePositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code> or is empty string.
     */
    public static void validateString(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty string.");
        }
    }

    /**
     * <p>
     * Gets the content of stack trace of the exception.
     * </p>
     *
     * @param e the exception to get the content of statck trace.
     *
     * @return the content of stack trace of the exception.
     */
    public static String getExceptionStaceTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        String message = sw.toString();
        pw.close();

        return message;
    }
}
