/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

/**
 * <p>
 * Package private helper class to simplify the argument checking and string parsing.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
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
     * @param obj
     *            the object to check.
     * @param paramName
     *            the paramName of the argument.
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
     * Check the given <code>String</code> for null and empty.
     * </p>
     * @param str
     *            string to check.
     * @param paramName
     *            paramName of the argument.
     * @throws IllegalArgumentException
     *             if str is null or empty string.
     */
    public static void checkString(String str, String paramName) {
        // check null
        checkNull(str, paramName);

        // check empty
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' can not be empty string!");
        }
    }

}