/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Map;


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
     * Check if the given stringArray is null or contains empty string as element.
     *
     * @param paramName name of the string array
     * @param stringArray string array to be checked
     *
     * @throws IllegalArgumentException if the given stringArray is null or contains empty string as element
     */
    public static void checkEmptyArray(String paramName, String[] stringArray) {
        checkNull(paramName, stringArray);

        for (int i = 0; i < stringArray.length; i++) {
            if ((stringArray[i] == null) || (stringArray[i].trim().length() == 0)) {
                throw new IllegalArgumentException("element " + i + " of array " + paramName + " empty string");
            }
        }
    }

    /**
     * Checks if the given map is null or contains null key or null value.
     *
     * @param paramName name of the parameter
     * @param map map to be checked
     * @throws IllegalArgumentException if the given map is null or contains null key or null value
     */
    public static void checkEmptyMap(String paramName, Map map) {
        checkNull(paramName, map);

        if (map.keySet().contains(null) || map.values().contains(null)) {
            throw new IllegalArgumentException("map :" + paramName + " contains null key or value");
        }
    }

    /**
     * Checks if the given parameter is not positive and throws IAE if so.
     *
     * @param paramName name of the parameter
     * @param parameter parameter value to be checked
     */
    public static void checkNotPositive(String paramName, int parameter) {
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

        if (string.trim().length() == 0) {
            throw new IllegalArgumentException(paramName + " is empty string");
        }
    }
}
