/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * Helper class for TimeEntry.
 * </p>
 *
 * <p>
 * This class provides some useful methods, such as argument validation, etc.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class TimeEntryHelper {
    /**
     * <p>
     * Private constructor to prevent this class be instantiated.
     * </p>
     */
    private TimeEntryHelper() {
        // empty
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     *
     * @throws NullPointerException if the given Object is null
     */
    static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new NullPointerException(name + " can not be null.");
        }
    }

    /**
     * Checks whether the given String is empty or null.
     *
     * @param arg the String to check
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the given string is empty
     */
    static void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " can not be empty.");
        }
    }

    /**
     * truncates the given string if its length is longer than 64.
     *
     * @param oldString the string should be check to truncate
     *
     * @return the truncated string
     */
    static String truncatesString(String oldString) {
        if (oldString.length() <= 255) {
            return oldString;
        }

        return oldString.substring(0, 255);
    }
}
