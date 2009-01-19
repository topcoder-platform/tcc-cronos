/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import java.util.List;

/**
 * <p>
 * This class is used by this component only. It provides some common utility methods.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> The class is thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // Empty constructor.
    }

    /**
     * <p>
     * Checks whether the given object is null. If null, <code>IllegalArgumentException</code> will be thrown.
     * </p>
     *
     * @param param
     *            the argument to be checked
     * @param paramName
     *            the name of the argument to be checked
     *
     * @throws IllegalArgumentException
     *             if param is null
     */
    public static void checkNotNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The argument '" + paramName + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the given string is null or empty. If it is, <code>IllegalArgumentException</code> will be thrown.
     * </p>
     *
     * @param param
     *            the argument to be checked
     * @param paramName
     *            the name of the argument to be checked
     *
     * @throws IllegalArgumentException
     *             if param is null or empty string
     */
    static void checkNotNullNotEmpty(final String param, final String paramName) {
        checkNotNull(param, paramName);
        if (param.trim().length() == 0) {
            throw new IllegalArgumentException("The argument '" + paramName + "' should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether the given list is null or empty. If it is, <code>IllegalArgumentException</code> will be thrown.
     * </p>
     *
     * @param <T>
     *            the type of list element
     * @param list
     *            the list to be checked
     * @param paramName
     *            the argument name of the list
     *
     * @throws IllegalArgumentException
     *             if list is null or empty
     */
    public static <T> void checkNotNullNotEmpty(final List<T> list, final String paramName) {
        checkNotNull(list, paramName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("The argument '" + paramName + "' should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether the param is positive, if it is not, <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @param param
     *            the value to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is not positive
     */
    public static void checkPositive(final double param, final String paramName) {
        if (param <= 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether the param is negative, if it is, <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @param param
     *            the double value to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is negative
     */
    public static void checkNotNegative(final double param, final String paramName) {
        if (param < 0.0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be negative.");
        }
    }

    /**
     * <p>
     * Checks whether the list is valid.
     * Note that the passed in list should not be null.
     * Valid list should not contain null element, and if it is string list, each string element should not be empty.
     * </p>
     *
     * @param <T>
     *            the type of list element
     * @param list
     *            the list to check
     * @param paramName
     *            the parameter name of the list
     * @throws IllegalArgumentException
     *             if the list contains null or empty string element
     */
    public static <T> void checkList(final List<T> list, final String paramName) {
        for (Object obj : list) {
            if (obj == null) {
                throw new IllegalArgumentException("The parameter '" + paramName + "' contains null element.");
            }
            // string element should not be empty
            if ((obj instanceof String) && ((String) obj).trim().length() == 0) {
                throw new IllegalArgumentException("The parameter '" + paramName
                    + "' contains empty string element.");
            }
        }
    }

    /**
     * <p>
     * Checks whether the array contains null element.
     * Note that the passed in array should not be null.
     * </p>
     *
     * @param <T>
     *            the type of list element
     * @param array
     *            the array to check
     * @param paramName
     *            the parameter name of the array
     * @throws IllegalArgumentException
     *             if the array contains null element
     */
    static <T> void checkArray(final T[] array, final String paramName) {
        for (Object obj : array) {
            if (obj == null) {
                throw new IllegalArgumentException("The parameter '" + paramName + "' contains null element.");
            }
        }
    }

}
