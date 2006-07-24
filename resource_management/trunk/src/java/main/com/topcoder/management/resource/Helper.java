/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * A helper to avoid code redundancy.
 * This class mainly two helper methods:
 * 1. checkLongPostivie(), to check if a given long value is positive.
 * 2. checkNull(), to check if a given object is null.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {

    /**
     * The private constructor used to avoid creating instances.
     */
    private Helper() {
        // do nothing
    }

    /**
     * Checks if the value of long is positive.
     *
     * @param value the long value
     * @param name the name of the parameter
     *
     * @throws IllegalArgumentException if the value is less than or equal to 0
     */
    public static void checkLongPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException("The parameter [" + name + "] must be positive.");
        }
    }

    /**
     * Checks if the value of an object is null.
     *
     * @param obj the parameter object
     * @param name the name of the parameter
     *
     * @throws IllegalArgumentException if obj is null
     */
    public static void checkNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("The parameter [" + name + "] cannot be null.");
        }
    }
}
