/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import java.util.Map;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.user.StringMatchType;

/**
 * <p>
 * Helper class for this component.
 * </p>
 *
 * <p>
 * Thread Safety : This class is immutable and so thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public final class Util {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Util() {
        // empty
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg the argument to check
     * @param name the name of the argument to check
     * @return the original argument
     *
     * @throws IllegalArgumentException if the given Object is null
     */
    public static Object checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }

        return arg;
    }

    /**
     * <p>
     * Checks whether the given String is null or empty.
     * </p>
     *
     * @param arg the String to check
     * @param name the name of the String argument to check
     * @return the original argument
     *
     * @throws IllegalArgumentException if the given string is null or empty
     */
    public static String checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        return arg;
    }

    /**
     * <p>
     * Checks whether the given <code>Map</code> instance contains each string in the given string
     * array as a key.
     * </p>
     *
     * @param columnNames the <code>Map</code> instance to check
     * @param keys each key in the string array is expected to be contained in the given <code>Map</code>
     * instance as a key
     *
     * @throws IllegalArgumentException if the given map instance doesn't contains any key in the given
     * string array
     */
    static void checkMapForKeys(Map columnNames, String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (!columnNames.containsKey(keys[i])) {
                throw new IllegalArgumentException("The given column names mapping doesn't contains key [" + keys[i]
                    + "}.");
            }
        }
    }

    /**
     * <p>
     * This method creates a <code>Filter</code> instance based on the given arguments.
     * </p>
     *
     * <p>
     * For {@link StringMatchType#EXACT_MATCH}, this method will create a <code>EqualToFilter</code>
     * that is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, this method will create a <code>LikeFilter</code>
     * that is used to search for string that starts with the provided value.
     * For {@link StringMatchType#ENDS_WITH}, this method will create a <code>LikeFilter</code>
     * that is used to search for string that ends with the provided value.
     * For {@link StringMatchType#SUBSTRING}, this method will create a <code>LikeFilter</code>
     * that is used to search for strings that contains the provided value.
     * </p>
     *
     * @param matchType <code>StringMatchType</code> to determine which search type to be created
     * @param name the name of the column to search
     * @param value the value of the column to match based on the search type
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the given matchType is null
     */
    static Filter createFilter(StringMatchType matchType, String name, String value) {
        checkNull(matchType, "matchType");

        if (matchType.equals(StringMatchType.EXACT_MATCH)) {
            return new EqualToFilter(name, value);
        } else {
            return new LikeFilter(name, matchType.getFilterPrefix() + value);
        }
    }
}