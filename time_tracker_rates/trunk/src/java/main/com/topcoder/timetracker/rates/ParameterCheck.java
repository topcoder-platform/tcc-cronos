/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

/**
 * Helper class to do parameters check.
 * This class is thread-safe since it's immutable.
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
     * Checks and throws IllegalArgumentException if the parameter is null or contains null or is empty.
     *
     * @param paramName name of the parameter
     * @param array value of the parameter to be checked
     *
     * @throws IllegalArgumentException if given parameter is null or contains null or is empty
     */
    public static void checkArray(String paramName, Object[] array) {
        checkNull(paramName, array);

        if (array.length == 0) {
            throw new IllegalArgumentException("array:" + paramName + " is empty");
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException("element " + i + " of array:" + paramName + " is null");
            }
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

        if (string != null) {
            if (string.trim().length() == 0) {
                throw new IllegalArgumentException(paramName + " is empty string");
            }
        }
    }
}
