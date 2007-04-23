/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

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
    private static void checkNotEmpty(String objName, String object) {
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
     *
     * @throws IllegalArgumentException
     *             if the array contains null
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
     * @return true, if string is or an empty string
     */
    public static boolean isNullOrEmptyString(String string) {
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
    private static boolean isEmptyString(String string) {
        return string.trim().length() == 0;
    }

    /**
     * Checks if object is null.
     *
     * @param object
     *            the object
     *
     * @return true, if object is null
     */
    private static boolean isNull(Object object) {
        return object == null;
    }

}
