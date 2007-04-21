/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import java.lang.reflect.Array;

/**
 * Class utility for checking the value an argument.
 *
 * @author enefem21
 * @version 1.0
 */
public final class ArgumentCheckUtil {

    /**
     * Private constructor to avoid an instantiation of this utility class.
     */
    private ArgumentCheckUtil() {
        // nothing to do
    }

    /**
     * Checks if the object is null.
     *
     * @param object
     *            the object
     * @param objName
     *            the object name
     *
     * @throws IllegalArgumentException
     *             if the object is null
     */
    public static void checkNotNull(String objName, Object object) {
        if (isNull(object)) {
            throw new IllegalArgumentException(objName + " can't be null");
        }
    }

    /**
     * Checks not empty for <code>String</code>.
     *
     * @param object
     *            the object
     * @param objName
     *            the object name
     *
     * @throws IllegalArgumentException
     *             if the string is empty
     */
    static void checkNotEmpty(String objName, String object) {
        if (!isNull(object)) {
            if (isEmptyString(object)) {
                throw new IllegalArgumentException(objName + " can't be an empty string");
            }
        }
    }

    /**
     * Checks not null and not empty for <code>String</code>.
     *
     * @param object
     *            the object
     * @param objName
     *            the object name
     *
     * @throws IllegalArgumentException
     *             if the string is null or empty
     */
    public static void checkNotNullAndNotEmpty(String objName, String object) {
        checkNotNull(objName, object);
        checkNotEmpty(objName, object);
    }

    /**
     * Checks that the array is not contains null element.
     *
     * @param arrayName
     *            the name of the array
     * @param array
     *            the array itself
     */
    public static void checkNotContainsNull(String arrayName, Object array) {
        if (array.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(array); i++) {
                if (isNull(Array.get(array, i))) {
                    throw new IllegalArgumentException("The " + arrayName + " contains null element");
                }
            }
        }
    }

    /**
     * Checks if string is null or empty string.
     *
     * @param string
     *            the string
     *
     * @return true, if string is or and empty string
     */
    static boolean isNullOrEmptyString(String string) {
        return isNull(string) || isEmptyString(string);
    }

    /**
     * Checks if string is empty string.
     *
     * @param string
     *            the string
     *
     * @return true, if string is empty string
     */
    static boolean isEmptyString(String string) {
        if (!isNull(string)) {
            return string.trim().length() == 0;
        }
        return false;
    }

    /**
     * Checks if object is null.
     *
     * @param object
     *            the object
     *
     * @return true, if object is null
     */
    static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Checks if the number is zero or negative.
     *
     * @param numberName
     *            the name of the number
     * @param number
     *            the given number to check
     *
     * @throws IllegalArgumentException
     *             if the number is negative
     */
    public static void checkNotNegative(String numberName, long number) {
        if (number < 0) {
            throw new IllegalArgumentException(numberName + " can't be negative");
        }
    }

}
