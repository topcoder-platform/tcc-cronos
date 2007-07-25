/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

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
final class Util {

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
     * @throws IllegalArgumentException
     *             if specified object is null
     */
    static void checkObjNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("The parameter [" + name + "] should not be null.");
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
     * @throws IllegalArgumentException
     *             if given ID is negative
     */
    static void checkIDNotNegative(long id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("The parameter [" + name
                + "] should not be negative.");
        }
    }

    /**
     * <p>
     * Checks whether error messages array is valid.
     * </p>
     * @param errors
     *            the error messages array
     * @throws IllegalArgumentException
     *             if given array contains null or empty elements
     */
    static void checkErrorsArray(String[] errors) {
        if (errors != null) {
            for (int i = 0; i < errors.length; i++) {
                if (errors[i] == null || errors[i].trim().length() == 0) {
                    throw new IllegalArgumentException(
                        "[errors] array should not contain null or empty elements.");
                }
            }
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
    static void checkStrNotNullOrEmpty(String str, String name) {
        checkObjNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter [" + name + "] should not be empty.");
        }
    }
}