/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * Helper class for the package com.topcoder.management.deliverable.
 * @author TCSDEVELOPER
 * @version 1.0
 */
class Helper {
    /**
     * Private constructor to prevent this class be instantiated.
     */
    private Helper() {
    }

    /**
     * Check if the given object is null.
     * @param obj
     *            the given object to check
     * @param name
     *            the name to identify the object.
     * @throws IllegalArgumentException
     *             if the given object is null
     */
    static void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Check if the given long value is positive.
     * @param val
     *            the given long value to check.
     * @param name
     *            the name to identify the long value.
     * @throws IllegalArgumentException
     *             if the given long value is negatvie or zero.
     */
    static void assertLongPositive(long val, String name) {
        if (val <= 0) {
            throw new IllegalArgumentException(name + " [" + val + "] should be positive.");
        }
    }
}
