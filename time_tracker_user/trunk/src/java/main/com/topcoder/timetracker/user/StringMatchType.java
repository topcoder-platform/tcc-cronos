/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is an enum that is used by the filter factories for those methods when a <code>String</code> criterion is
 * specified.
 * </p>
 *
 * <p>
 * This gives the user a convenient way of specifying the method of matching the Strings.
 * </p>
 *
 * <p>
 * Thread Safety: This class is an enum, and therefor thread-safe.
 * </p>
 *
 * @author ShindouHikaru, biotrail
 * @version 3.2
 */
public class StringMatchType extends Enum {

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
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -2500639364006680362L;

    /**
     * <p>
     * This is the filter prefix that the Filter Factory should provide to the <code>LikeFilter</code> that it
     * produces in order to achieve the desired functionality.
     * </p>
     *
     * <p>
     * Note, if it is <code>EXACT_MATCH</code>, then <code>EqualToFilter</code> will be used so this prefix is
     * not needed.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     */
    private final String filterPrefix;

    /**
     * <p>
     * Private constructor that produces a <code>StringMatchType</code> with given prefix.
     * </p>
     *
     * @param filterPrefix
     *            The prefix that the Filter Factory should provide to the <code>LikeFilter</code>, if it is
     *            <code>EXACT_MATCH</code>, then this prefix string is not used.
     */
    private StringMatchType(String filterPrefix) {
        this.filterPrefix = filterPrefix;
    }

    /**
     * <p>
     * Retrieves the filter prefix of this match type.
     * </p>
     *
     *
     * @return the filter prefix of this match type.
     */
    public String getFilterPrefix() {
        return this.filterPrefix;
    }

    /**
     * <p>
     * Retrieves the String representation of this match type, which is equivalent to the filter prefix.
     * </p>
     *
     * @return the filter prefix of this match type.
     */
    public String toString() {
        return this.filterPrefix;
    }
}