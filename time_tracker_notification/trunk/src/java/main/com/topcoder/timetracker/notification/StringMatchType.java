/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is an enum that is used by the FilterFactories for those methods when a String criterion is specified. This
 * gives the user a convenient way of specifying the method of matching the Strings.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is an enum, and therefor thread-safe.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 1.0
 */
public class StringMatchType extends Enum {

    /**
      * Automatically generated unique ID for use with serialization.
      */
    private static final long serialVersionUID = 964874786410105161L;

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
     *
     * <p>
     * Initial Value: null
     * </p>
     *
     * <p>
     * Accessed In: getFilterPrefix
     * </p>
     *
     * <p>
     * Modified In: Not Modified
     * </p>
     *
     * <p>
     * Utilized In: toString
     * </p>
     *
     * <p>
     * Valid Values: Null (during initialization) or Not null and Not empty String objects (after setting)
     * </p>
     */
    private final String filterPrefix;

    /**
     * <p>
     * Private constructor.
     * </p>
     *
     * @param prefix The String prefix to use when constructing the LikeFilter.
     */
    private StringMatchType(String prefix) {
        this.filterPrefix = prefix;
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
