/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.util.Collection;

/**
 * <p>
 * Helper class used by this component. This contains the common methods to handle code redundancy.
 * </p>
 *
 * <p>
 * Thread Safety: This class does not maintain any state and is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {

    /**
     * No instances allowed.
     */
    private Helper() {
        // empty
    }

    /**
     * Checks the object is <code>null</code> or not.
     *
     * @param obj
     *            the object to check
     * @param paramName
     *            the parameter name
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>
     */
    public static void checkNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' cannot be null.");
        }
    }

    /**
     * Checks the string is <code>null</code> or empty.
     *
     * @param str
     *            the string to check
     * @param paramName
     *            the parameter name
     * @throws IllegalArgumentException
     *             if the string is <code>null</code> or empty
     */
    public static void checkString(String str, String paramName) {
        checkNull(str, paramName);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' cannot be empty.");
        }
    }

    /**
     * Checks the number is positive.
     *
     * @param num
     *            the number to check
     * @param paramName
     *            the parameter name
     * @throws IllegalArgumentException
     *             if the number is not positive
     */
    public static void checkPositive(long num, String paramName) {
        if (num <= 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should be positive number");
        }
    }

    /**
     * Checks the number is positive.
     *
     * @param num
     *            the number to check
     * @param paramName
     *            the parameter name
     * @throws IllegalArgumentException
     *             if the number is not positive
     */
    public static void checkPositive(double num, String paramName) {
        if (num <= 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should be positive number");
        }
    }

    /**
     * Checks the collection is valid.
     *
     * @param col
     *            the collection to check
     * @param paramName
     *            the parameter name
     * @throws IllegalArgumentException
     *             if the collection is <code>null</code>, or contains <code>null</code>
     */
    @SuppressWarnings("unchecked")
    public static void checkCollection(Collection col, String paramName) {
        checkNull(col, paramName);
        if (col.contains(null)) {
            throw new IllegalArgumentException("The collection '" + paramName + "' contains null elements.");
        }
    }
}
