/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;

import java.util.Date;


/**
 * <p>
 * This is a base <code>FilterFactory</code> interface that provides filter creation methods that may be used for
 * filters of any Time Tracker Bean. It encapsulates filters for the common functionality - namely, the creation and
 * modification date and user.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface BaseFilterFactory {
    /**
     * <p>
     * Creates a Filter based on the Creation Date of the entity. A date range that may be open-ended can be specified.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion. May be null to specify that this has no lower bound.
     * @param rangeEnd The upper bound of the date criterion. May be null to specify that this has no upper bound.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid, or if both arguments are null.
     */
    Filter createCreationDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * Creates a Filter based on the Modification Date of the entity. A date range that may be open-ended can be
     * specified.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion. May be null to specify that this has no lower bound.
     * @param rangeEnd The upper bound of the date criterion. May be null to specify that this has no upper bound.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid, or if both arguments are null.
     */
    Filter createModificationDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * Creates a Filter based on the Creation User of the entity.
     * </p>
     *
     * @param username The user name used for searching.
     * @param matchType The String match type that is desired on the search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or if matchType is null.
     */
    Filter createCreationUserFilter(String username, StringMatchType matchType);

    /**
     * <p>
     * Creates a Filter based on the Modification User of the entity.
     * </p>
     *
     * @param username The user name used for searching.
     * @param matchType The String match type that is desired on the search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or if matchType is null.
     */
    Filter createModificationUserFilter(String username, StringMatchType matchType);
}
