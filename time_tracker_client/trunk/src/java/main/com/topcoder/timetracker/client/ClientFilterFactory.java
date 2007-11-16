/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.util.Date;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.client.db.ClientColumnName;


/**
 * <p>
 * This class is used to create pre-defined filters or AND/OR/NOT filter based on given filters.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientFilterFactory {
    /**
     * <p>
     * Represent the LikeFilter prefix used to create the LikeFilter.
     * </p>
     */
    private static final String LIKE_FILTER_PREFIX = "SS:";

    /**
     * <p>
     * Empty private constructor to ensure this class can't be instance.
     * </p>
     */
    private ClientFilterFactory() {
        // empty
    }

    /**
     * <p>
     * Create filter which will return all clients with a given company ID.
     * </p>
     *
     * @param companyId the company id
     *
     * @return the non null filter
     *
     * @throws IllegalArgumentException if the is not positive
     */
    public static Filter createCompanyIdFilter(long companyId) {
        Helper.checkPositive(companyId, "companyId");

        return new EqualToFilter(ClientColumnName.COMPANY_ID.getName(), new Long(companyId));
    }

    /**
     * <p>
     * Create filter which will return all clients with name that contains a given string.
     * </p>
     *
     * @param keyword non null, non empty(trim'd) string which is expected in the name
     *
     * @return non null filter
     *
     * @exception IllegalArgumentException if the keyword is null/empty(trim'd)
     */
    public static Filter createNameKeywordFilter(String keyword) {
        Helper.checkString(keyword, "keyword");

        return new LikeFilter(ClientColumnName.NAME.getName(), LIKE_FILTER_PREFIX + keyword);
    }

    /**
     * <p>
     * Create filter which will return all clients with greek name that contains a given string.
     * </p>
     *
     * @param keyword non null, non empty(trim'd) string which is expected in the name
     *
     * @return non null filter
     *
     * @exception IllegalArgumentException if the keyword is null/empty(trim'd)
     */
    public static Filter createGreekNameKeywordFilter(String keyword) {
        Helper.checkString(keyword, "keyword");

        return new LikeFilter(ClientColumnName.GREEK_NAME.getName(), LIKE_FILTER_PREFIX + keyword);
    }

    /**
     * <p>
     * Create filter which will return all clients created within a given inclusive date range (may be open-ended).
     * </p>
     *
     * @param from non null start date of the range
     * @param to possible null end date of the range
     *
     * @return non null filter
     *
     * @throws IllegalArgumentException if from is null
     */
    public static Filter createCreatedInFilter(Date from, Date to) {
        return buildRangeFilter(from, to, ClientColumnName.CREATION_DATE.getName());
    }

    /**
     * <p>
     * Create filter which will return all clients modified within a given inclusive date range (may be open-ended).
     * </p>
     *
     * @param from non null start date of the range
     * @param to possible null end date of the range
     *
     * @return non null filter
     *
     * @throws IllegalArgumentException if from is null
     */
    public static Filter createModifiedInFilter(Date from, Date to) {
        return buildRangeFilter(from, to, ClientColumnName.MODIFICATION_DATE.getName());
    }

    /**
     * <p>
     * Create filter which will return all clients created by a given username.
     * </p>
     *
     * @param userName non null, non empty(trim'd) name of the user
     *
     * @return non null filter
     *
     * @throws IllegalArgumentException if the name is null/empty(trim'd)
     */
    public static Filter createCreatedByFilter(String userName) {
        Helper.checkString(userName, "userName");

        return new EqualToFilter(ClientColumnName.CREATION_USER.getName(), userName);
    }

    /**
     * <p>
     * Create filter which will return all clients modified by a given username.
     * </p>
     *
     * @param userName non null, non empty(trim'd) name of the user
     *
     * @return non null filter
     *
     * @throws IllegalArgumentException if the name is null/empty(trim'd)
     */
    public static Filter createModifiedByFilter(String userName) {
        Helper.checkString(userName, "userName");

        return new EqualToFilter(ClientColumnName.MODIFICATION_USER.getName(), userName);
    }

    /**
     * <p>
     * Create AND filter of given filters.
     * </p>
     *
     * @param first non null first original filter
     * @param second non null second original filter
     *
     * @return non null and filter of given filters
     *
     * @throws IllegalArgumentException if any filter is null
     */
    public static Filter andFilter(Filter first, Filter second) {
        Helper.checkNull(first, "first");
        Helper.checkNull(second, "second");

        return new AndFilter(first, second);
    }

    /**
     * <p>
     * Create OR filter of given filters.
     * </p>
     *
     * @param first non null first original filter
     * @param second non null second original filter
     *
     * @return non null or filter of given filters
     *
     * @throws IllegalArgumentException if any filter is null
     */
    public static Filter orFilter(Filter first, Filter second) {
        Helper.checkNull(first, "first");
        Helper.checkNull(second, "second");

        return new OrFilter(first, second);
    }

    /**
     * <p>
     * Create NOT filter of given filter.
     * </p>
     *
     * @param filter non null original filter
     *
     * @return non null filter which is the not filter of the orginal one
     *
     * @throws IllegalArgumentException if the filter is null
     */
    public static Filter notFilter(Filter filter) {
        Helper.checkNull(filter, "filter");

        return new NotFilter(filter);
    }

    /**
     * <p>
     * Create filter which will return all clients with given active status.
     * </p>
     *
     * @param active the expected active status
     *
     * @return the non null filter
     */
    public static Filter createActiveFilter(boolean active) {
        return new EqualToFilter(ClientColumnName.ACTIVE.getName(), new Integer(active ? 1 : 0));
    }

    /**
     * <p>
     * Create an Between filter for two date, use the begin date as the lower bound and the end date as the up bound. If
     * any of the date is null, it means it's unbound.
     * </p>
     *
     * @param rangeStart the lower bound (inclusive) of the range to search.  If it is null, then it means that it is
     *        unbounded at this end.
     * @param rangeEnd the upper bound (inclusive) of the range to search. If it is null, then it means that it is
     *        unbounded on this end.
     * @param name the prefix name for the criteria
     *
     * @return A filter with the specified criteria
     *
     * @throws IllegalArgumentException if both range start and range end is null
     */
    private static Filter buildRangeFilter(Date rangeStart, Date rangeEnd, String name) {
        if ((rangeStart != null) && (rangeEnd != null)) {
            if (rangeStart.after(rangeEnd)) {
                throw new IllegalArgumentException("Begin date is after end date.");
            }

            return new BetweenFilter(name, rangeStart, rangeEnd);
        } else if (rangeStart == null && rangeEnd == null) {
            throw new IllegalArgumentException("Both end is null.");
        } else if (rangeStart != null) {
            return new GreaterThanOrEqualToFilter(name, rangeStart);
        } else {
            return new LessThanOrEqualToFilter(name, rangeEnd);
        }
    }
}
