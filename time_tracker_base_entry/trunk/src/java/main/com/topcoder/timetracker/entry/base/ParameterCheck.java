/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;
/**
 * Helper class to do parameters check.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class ParameterCheck {
    /**
     * Private ctor preventing this class from being instantiated.
     */
    private ParameterCheck() {
    }

    /**
     * Checks if the given parameter is not positive and throws IAE if so.
     *
     * @param paramName name of the parameter
     * @param parameter parameter value to be checked
     * @throws IllegalArgumentException if parameter is not positive
     */
    public static void checkNotPositive(String paramName, long parameter) {
        if (parameter <= 0) {
            throw new IllegalArgumentException(paramName + " is not positive");
        }
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is null.
     *
     * @param paramName name of the parameter
     * @param param value of the parameter to be checked
     *
     * @throws IllegalArgumentException if given parameter is null
     */
    public static void checkNull(String paramName, Object param) {
        if (param == null) {
            throw new IllegalArgumentException(paramName + " is null");
        }
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is null or empty string.
     *
     * @param paramName name of the parameter
     * @param string string to be checked
     *
     * @throws IllegalArgumentException if given parameter is empty string.
     */
    public static void checkNullEmpty(String paramName, String string) {
        checkNull(paramName, string);
        checkEmpty(paramName, string);
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is empty string(null is allowed).
     *
     * @param paramName name of the parameter
     * @param string string to be checked
     *
     * @throws IllegalArgumentException if given parameter is empty string
     */
    public static void checkEmpty(String paramName, String string) {
        if (string != null) {
            if (string.trim().length() == 0) {
                throw new IllegalArgumentException(paramName + " is empty string");
            }
        }
    }
}
