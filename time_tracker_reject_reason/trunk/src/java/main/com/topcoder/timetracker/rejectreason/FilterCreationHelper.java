/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;


/**
 * <p>
 * Helper class for Filter creation. This contains a helper method to create filter between a given range, the range is
 * allowed to be open-end.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
final class FilterCreationHelper {
    /**
     * The private constructor to avoid instantiation.
     */
    private FilterCreationHelper() {
        // Do nothing
    }

    /**
     * <p>
     * Creates a Filter based on the range. A range that may be open-ended can be specified.
     * </p>
     *
     * @param columnName the column name.
     * @param rangeStart the lower bound of the date criterion. May be null to specify that this has no lower bound.
     * @param rangeEnd the upper bound of the date criterion. May be null to specify that this has no upper bound.
     *
     * @return a filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the range specified is invalid (eg. rangeStart &gt rangeEnd), or if both
     *         arguments are null.
     */
    static Filter createRangeFilter(String columnName, Comparable rangeStart, Comparable rangeEnd) {
        if ((rangeStart == null) && (rangeEnd == null)) {
            throw new IllegalArgumentException("Both lower bound and upper bound is null.");
        }

        if (rangeStart == null) {
            return new LessThanOrEqualToFilter(columnName, rangeEnd);
        } else if (rangeEnd == null) {
            return new GreaterThanOrEqualToFilter(columnName, rangeStart);
        } else {
            if (rangeStart.compareTo(rangeEnd) > 0) {
                throw new IllegalArgumentException("The range specified is invalid.");
            }

            return new BetweenFilter(columnName, rangeEnd, rangeStart);
        }
    }
}
