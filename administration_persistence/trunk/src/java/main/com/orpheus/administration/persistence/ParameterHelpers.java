/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * <p>Class containing helper methods for validating parameters. Note that this class is meant to be used only
 * internally by this component, but it has <code>public</code> access to enable it to be used by the various
 * sub-packages that make up this component.
 *
 * <p><strong>Thread Safety</strong>: This class has no state and is therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class ParameterHelpers {
    /**
     * Private constructor to prevent instantiation.
     */
    private ParameterHelpers() {
    }

    /**
     * Helper method to validate array arguments. This method throws an exception if the argument is invalid,
     * otherwise does nothing.
     *
     * @param arr the array to validate
     * @param description a description of the array type for use in formatting the exception
     * @throws IllegalArgumentException if <code>arr</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>arr</code> is an empty array
     * @throws IllegalArgumentException if <code>arr</code> contains any <code>null</code> elements
     */
    public static final void checkArray(Object[] arr, String description) {
        if (arr == null) {
            throw new IllegalArgumentException(description + " array must not be null");
        }

        if (arr.length == 0) {
            throw new IllegalArgumentException(description + " array must not be empty");
        }

        for (int idx = 0; idx < arr.length; ++idx) {
            if (arr[idx] == null) {
                throw new IllegalArgumentException(description + " array must not contain null elements");
            }
        }
    }

    /**
     * Helper method to validate string arguments. This method throws an exception if the argument is invalid,
     * otherwise does nothing.
     *
     * @param str the string to validate
     * @param description a description of the string for use in formatting the exception
     * @return <code>str</code>
     * @throws IllegalArgumentException if <code>str</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>str</code> is an empty string
     */
    public static final String checkString(String str, String description) {
        if (str == null) {
            throw new IllegalArgumentException(description + " must not be null");
        }

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(description + " must not be empty strings");
        }

        return str;
    }
}
