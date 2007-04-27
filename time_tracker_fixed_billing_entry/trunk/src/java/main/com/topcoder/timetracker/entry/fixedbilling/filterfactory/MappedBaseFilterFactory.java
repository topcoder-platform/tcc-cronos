/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.filterfactory;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.BaseFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.Helper;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;

import java.util.Date;


/**
 * <p>
 * This is an implementation of the <code>BaseFilterFactory</code> that may be used to build filters.
 * </p>
 *
 * <p>
 * Thread Safety: This is thread-safe, since it is not modified after construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class MappedBaseFilterFactory implements BaseFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Creation Date.
     * </p>
     */
    public static final String CREATION_DATE_COLUMN_NAME = "CREATION_DATE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Modification Date.
     * </p>
     */
    public static final String MODIFICATION_DATE_COLUMN_NAME = "MODIFICATION_DATE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Creation User.
     * </p>
     */
    public static final String CREATION_USER_COLUMN_NAME = "CREATION_USER_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Modification User.
     * </p>
     */
    public static final String MODIFICATION_USER_COLUMN_NAME = "MODIFICATION_USER_COLUMN_NAME";

    /**
     * <p>
     * Creates a MappedBaseFilterFactory.
     * </p>
     *
     */
    public MappedBaseFilterFactory() {
    }

    /**
     * <p>
     * Creates a Filter based on the Creation Date of the entity.  A date range that may be open-ended can be
     * specified.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid, or if both arguments are null.
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        return Helper.createDateRangeFilter(CREATION_DATE_COLUMN_NAME, rangeStart,
            rangeEnd);
    }

    /**
     * <p>
     * Creates a Filter based on the Modification Date of the entity. A date range that may be open-ended can be
     * specified.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid, or if both arguments are null.
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
        return Helper.createDateRangeFilter(MODIFICATION_DATE_COLUMN_NAME, rangeStart,
            rangeEnd);
    }

    /**
     * <p>
     * Creates a Filter based on the Creation User of the entity.
     * </p>
     *
     * @param username The user name used for searching.
     * @param matchType The String matching that is desired.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or if matchType is null.
     */
    public Filter createCreationUserFilter(String username, StringMatchType matchType) {
        return Helper.createFilterByMatchType(CREATION_USER_COLUMN_NAME, username,
            matchType);
    }

    /**
     * <p>
     * Creates a Filter based on the Modification User of the entity.
     * </p>
     *
     * @param username The user name used for searching.
     * @param matchType The String matching that is desired.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or if matchType is null.
     */
    public Filter createModificationUserFilter(String username, StringMatchType matchType) {
        return Helper.createFilterByMatchType(MODIFICATION_USER_COLUMN_NAME, username, matchType);
    }
}
