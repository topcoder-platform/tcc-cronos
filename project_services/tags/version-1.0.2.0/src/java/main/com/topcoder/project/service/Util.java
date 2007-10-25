/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This is a helper class for the whole component.
 * </p>
 * <p>
 * Thread safety: This class is thread safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Util {

    /**
     * <p>
     * Private constructor preventing this class from being instantiated.
     * </p>
     */
    private Util() {
    }

    /**
     * <p>
     * Checks whether given object is null or not.
     * </p>
     * @param obj
     *            given object to be checked
     * @param name
     *            name of the specified object
     * @param logger
     *            the logger to log message
     * @throws IllegalArgumentException
     *             if specified object is null
     */
    public static void checkObjNotNull(Object obj, String name, Log logger) {
        if (obj == null) {
            if (logger != null) {
                logger.log(Level.ERROR, "IllegalArgumentException occurred.");
            }
            throw new IllegalArgumentException("[" + name + "] should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether given Object array has null elements.
     * </p>
     * @param array
     *            the Object array to be checked
     * @param name
     *            name of the array
     * @throws IllegalArgumentException
     *             if given Object array has null elements
     */
    public static void checkArrrayHasNull(Object[] array, String name) {
        for (int i = 0; i < array.length; i++) {
            checkObjNotNull(array[i], "Elements of " + name, null);

        }
    }

    /**
     * <p>
     * Checks whether given String array has null or empty elements.
     * </p>
     * @param array
     *            the array to be checked
     * @param name
     *            name of the array
     * @throws IllegalArgumentException
     *             if given array has null or empty elements
     */
    public static void checkStringArrayHasNullOrEmptyEle(String[] array, String name) {
        for (int i = 0; i < array.length; i++) {
            checkStrNotNullOrEmpty(array[i], "Elements of " + name);
        }

    }

    /**
     * <p>
     * Checks whether given string is null or empty.
     * </p>
     * @param str
     *            the string to be checked
     * @param name
     *            name of the string
     * @throws IllegalArgumentException
     *             if given string is null or empty
     */
    public static void checkStrNotNullOrEmpty(String str, String name) {
        checkObjNotNull(str, name, null);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("[" + name + "] should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether given ID is negative or not.
     * </p>
     * @param id
     *            the id to be checked
     * @param name
     *            name of the id
     * @param logger
     *            the logger to log message
     * @throws IllegalArgumentException
     *             if given ID is negative
     */
    public static void checkIDNotNegative(long id, String name, Log logger) {
        if (id < 0) {
            if (logger != null) {
                logger.log(Level.ERROR, "IllegalArgumentException occurred.");
            }
            throw new IllegalArgumentException("The parameter [" + name
                + "] should not be negative.");
        }
    }
}
