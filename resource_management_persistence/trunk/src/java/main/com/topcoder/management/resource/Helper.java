/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * Helper class to simplify the argument checking.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Check the given <code>obj</code> for null.
     * </p>
     *
     * @param obj
     *            the object to check.
     * @param paramName
     *            the paramName of the argument.
     *
     * @throws IllegalArgumentException
     *             if obj is null.
     */
    public static void checkNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' can not be null!");
        }
    }

    /**
     * <p>
     * Check the give Long instance for positive value if is not a null.
     * </p>
     *
     * @param value
     *            the Long instance to check
     * @param paramName
     *            the paramName of the argument.
     * @throws IllegalArgumentException
     *             if the value is not positive
     */
   public static void checkLongValue(Long value, String paramName) {
        if (value != null && value.longValue() <= 0) {
            throw new IllegalArgumentException("The parameter " + paramName + " should be positive value, but was "
                    + value.longValue());
        }
    }

    /**
     * <p>
     * Check if the long value is positive.
     * </p>
     *
     * @param value
     *            the long value to check
     * @param paramName
     *            the paramName of the argument.
     * @throws IllegalArgumentException
     *             if the value is not positive.
     */
   public static void checkPositiveValue(long value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException("The value for " + paramName + " should be positive, but was " + value);
        }
    }
}
