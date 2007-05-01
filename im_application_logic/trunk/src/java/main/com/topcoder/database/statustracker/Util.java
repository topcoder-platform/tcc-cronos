/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker;

/**
 * <p>
 * Helper class for the Status Tracker Component.
 * </p>
 *
 * <p>
 * Note, since this is a multi-package component, this helper class is defined as public instead of package-private
 * to avoid defining such class in every package. This is allowed by the TopCoder rule.
 * All the static methods are shared between multiple packages are defined as public, otherwise will be defined as
 * package-private.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Util {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Util() {
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg the argument to check
     * @param name the name of the argument to check
     *
     * @throws IllegalArgumentException if the given Object is null
     */
    public static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the given String is null or empty.
     * Here empty means the length of the given string is zero after trimmed.
     * </p>
     *
     * @param arg the String to check
     * @param name the name of the String argument to check
     *
     * @throws IllegalArgumentException if the given string is null or empty
     */
    public static void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

}
