/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is an enum that is used by the FilterFactories for those methods when a String criterion is specified. This
 * gives the user a convenient way of specifying the method of matching the Strings.
 * </p>
 *
 * <p>
 * Thread Safety: This class is an enum, and there for thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class StringMatchType extends Enum {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = -3738176249130557574L;

	/**
     * <p>
     * This specifies that the criterion should be based on whether it begins with the specified String.
     * </p>
     */
    public static final StringMatchType STARTS_WITH = new StringMatchType("SW:");

    /**
     * <p>
     * This specifies that the criterion should be based on whether it ends with the specified String.
     * </p>
     */
    public static final StringMatchType ENDS_WITH = new StringMatchType("EW:");

    /**
     * <p>
     * This specifies that the criterion should be based on whether it contains the specified String.
     * </p>
     */
    public static final StringMatchType SUBSTRING = new StringMatchType("SS:");

    /**
     * <p>
     * This specifies that the criterion should be based on whether it exactly matches the specified String.
     * </p>
     */
    public static final StringMatchType EXACT_MATCH = new StringMatchType("");

    /**
     * <p>
     * This is the filter prefix that the Filter Factory should provide to the LikeFilter that it produces in order to
     * achieve the desired functionality.
     * </p>
     */
    private String filterPrefix;

    /**
     * <p>
     * Private constructor accepting a filterPrefix.
     * </p>
     *
     * @param filterPrefix The filter prefix of this match type.
     */
    private StringMatchType(String filterPrefix) {
        this.filterPrefix = filterPrefix;
    }

    /**
     * <p>
     * Retrieves the filter prefix of this match type.
     * </p>
     *
     * @return the filter prefix of this match type.
     */
    public String getFilterPrefix() {
        return filterPrefix;
    }

    /**
     * <p>
     * Retrieves the String representation of this match type, which is equivalent to the filter prefix.
     * </p>
     *
     * @return the filter prefix of this match type.
     */
    public String toString() {
        return filterPrefix;
    }
}
