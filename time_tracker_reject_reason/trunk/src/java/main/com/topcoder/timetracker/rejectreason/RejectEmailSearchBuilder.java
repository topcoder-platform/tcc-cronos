/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import java.util.Date;

import java.util.Arrays;


/**
 * <p>
 * This is a convenience class that may be used to build filters for performing searches in the RejectEmailDAO. It
 * provide the methods to build fundamental search filter as well as methods to compose filters with AND, OR and NOT
 * logic.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This is an immutable class and is thread safe.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailSearchBuilder {
    /**
     * <p>
     * Creates a new RejectEmailSearchBuilder instance.
     * </p>
     */
    public RejectEmailSearchBuilder() {
        // Empty.
    }

    /**
     * <p>
     * Returns a Filter built on input Filters with logic AND relation.
     * </p>
     *
     * @param filterA original search filter.
     * @param filterB original search filter.
     *
     * @return a search filter that may be used in the RejectEmailDAO directly or composed a new filter.
     *
     * @throws IllegalArgumentException if any of the input filter is null.
     */
    public Filter and(Filter filterA, Filter filterB) {
        return new AndFilter(filterA, filterB);
    }

    /**
     * <p>
     * Returns a Filter built on input Filters with logic AND relation.
     * </p>
     *
     * @param filters an array of filters to be composed.
     *
     * @return a search filter that may be used in the RejectEmailDAO directly or composed a new filter.
     *
     * @throws IllegalArgumentException if any of the input array is null or empty.
     */
    public Filter and(Filter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The filters is null.");
        }

        return new AndFilter(Arrays.asList(filters));
    }

    /**
     * <p>
     * Returns a Filter built on input Filters with logic OR relation.
     * </p>
     *
     * @param filterA original search filter.
     * @param filterB original search filter.
     *
     * @return a search filter that may be used in the RejectEmailDAO directly or composed a new filter.
     *
     * @throws IllegalArgumentException if any of the input filter is null.
     */
    public Filter or(Filter filterA, Filter filterB) {
        return new OrFilter(filterA, filterB);
    }

    /**
     * <p>
     * Returns a Filter built on input Filters with logic OR relation.
     * </p>
     *
     * @param filters an array of filters to be composed.
     *
     * @return a search filter that may be used in the RejectEmailDAO directly or composed a new filter.
     *
     * @throws IllegalArgumentException if any of the input array is null or empty.
     */
    public Filter or(Filter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The filters is null.");
        }

        return new OrFilter(Arrays.asList(filters));
    }

    /**
     * <p>
     * Returns a Filter built on the input Filter with logic NOT relation.
     * </p>
     *
     * @param filter original search filter.
     *
     * @return a search filter that may be used in the RejectEmailDAO directly or composed a new filter.
     *
     * @throws IllegalArgumentException the input filter is null.
     */
    public Filter not(Filter filter) {
        return new NotFilter(filter);
    }

    /**
     * <p>
     * Returns an EqualToFilter with RejectEmailDAO.SEARCH_COMPANY_ID as the field name and the provided value as the
     * search value.
     * </p>
     *
     * @param companyId the company id to search.
     *
     * @return an EqualToFilter with RejectEmailDAO.SEARCH_COMPANY_ID as the field name and the provided value as the
     *         search value.
     */
    public Filter hasCompanyIdFilter(long companyId) {
        if (companyId <= 0) {
            throw new IllegalArgumentException("The companyId must be a positive integer.");
        }

        return new EqualToFilter(RejectEmailDAO.SEARCH_COMPANY_ID, new Long(companyId));
    }

    /**
     * <p>
     * Returns an LikeFilter with RejectEmailDAO.SEARCH_BODY as the field name and the provided value as the search
     * value. The prefix used for LikeFilter is LikeFilter.WITH_CONTENT.
     * </p>
     *
     * @param body the body to search.
     *
     * @return an LikeFilter with RejectEmailDAO.SEARCH_BODY as the field name and the provided value as the search
     *         value.
     *
     * @throws IllegalArgumentException if the body is null or an empty String.
     */
    public Filter hasBodyFilter(String body) {
        if (body == null) {
            throw new IllegalArgumentException("The body is null.");
        }

        if (body.trim().length() == 0) {
            throw new IllegalArgumentException("The body is empty.");
        }

        return new LikeFilter(RejectEmailDAO.SEARCH_BODY, LikeFilter.WITH_CONTENT + body);
    }

    /**
     * <p>
     * Returns a range filter of creation date of RejectEmail.
     * </p>
     *
     * @param startDate the starting date to search.
     * @param endDate the end date to search.
     *
     * @return a search filter that specifies the range of creation date of RejectEmail.
     *
     * @throws IllegalArgumentException if both parameters are null, or if endDate is less than startDate.
     */
    public Filter createdWithinDateRangeFilter(Date startDate, Date endDate) {
        return FilterCreationHelper.createRangeFilter(RejectEmailDAO.SEARCH_CREATED_DATE, startDate, endDate);
    }

    /**
     * <p>
     * Return an EqualToFilter with RejectEmailDAO.SEARCH_CREATED_USER as the field name and the provided value as the
     * search value.
     * </p>
     *
     * @param username the username to search.
     *
     * @return an EqualToFilter with RejectEmailDAO.SEARCH_CREATED_USER as the field name and the provided value as the
     *         search value.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public Filter createdByUserFilter(String username) {
        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        return new EqualToFilter(RejectEmailDAO.SEARCH_CREATED_USER, username);
    }

    /**
     * <p>
     * Returns a range filter of modification date of RejectEmail.
     * </p>
     *
     * @param startDate the starting date to search.
     * @param endDate the end date to search.
     *
     * @return a search filter that specifies the range of creation date of RejectEmail.
     *
     * @throws IllegalArgumentException if both parameters are null, or if endDate is less than startDate.
     */
    public Filter modifiedWithinDateRangeFilter(Date startDate, Date endDate) {
        return FilterCreationHelper.createRangeFilter(RejectEmailDAO.SEARCH_MODIFICATION_DATE, startDate, endDate);
    }

    /**
     * <p>
     * Return an EqualToFilter with RejectEmailDAO.SEARCH_MODIFICATION_USER as the field name and the provided value as
     * the search value.
     * </p>
     *
     * @param username the username to search.
     *
     * @return an EqualToFilter with RejectEmailDAO.SEARCH_MODIFICATION_USER as the field name and the provided value
     *         as the search value.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public Filter modifiedByUserFilter(String username) {
        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        return new EqualToFilter(RejectEmailDAO.SEARCH_MODIFICATION_USER, username);
    }
}
