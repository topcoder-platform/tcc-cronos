/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LikeFilter;

/**
 * <p>
 * This is a convenience class that may be used to build filters for performing searches in the RejectReasonDAO.
 * Users may call the different methods to set the criteria for the filter and finally retrieve the filter for use
 * via the buildFilter() method.
 * </p>
 * <p>
 * Thread Safety: - This is a stateful class, since it can be modified by the various method calls. It is not
 * thread safe, and multiple threads should work with their own instance of the search builder.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class RejectReasonSearchBuilder {

    /**
     * <p>
     * An AndFilter that is used to aggregate the different criteria that the user has defined from calling the
     * different methods of this class.
     * </p>
     * <p>
     * Initialized In: First method call to a method that isn't buildFilter/reset will cause this to be initialized
     * to a new AndFilter with a List containing the single filter that was built in the respective method call.
     * </p>
     * <p>
     * Accessed In: buildFilter (a clone of the Filter is returned)
     * </p>
     * <p>
     * Modified In: reset() method will make this uninitialized again
     * </p>
     * <p>
     * Utilized In: All methods
     * </p>
     *
     */
    private AndFilter andFilter;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public RejectReasonSearchBuilder() {

    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that are related to the company with provided id.
     * </p>
     * <p>
     * Implementation Notes:
     *  - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * RejectReasonDAO.SEARCH_COMPANY_ID as the field name and the provided value as the search value.
     *  - If the andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param id The related company id to search for.
     */
    public void hasCompanyId(long id) {
        addToFilter(new EqualToFilter(RejectReasonDAO.SEARCH_COMPANY_ID, new Long(id)));
    }

    /**
     * <p>
     * Adds given filter to <code>andFilter</code>. It <code>andFilter</code> is <code>null</code>, new
     * instance will be created.
     * </p>
     *
     * @param filter the filter to be added.
     */
    private void addToFilter(Filter filter) {
        if (andFilter == null) {
            List list = new ArrayList();
            list.add(filter);
            andFilter = new AndFilter(list);
        } else {
            andFilter.addFilter(filter);
        }

    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that contain the provided String as part of the description.
     * </p>
     * <p>
     * Implementation Notes:
     *  - Build the following filter and add it to the andFilter: -> LikeFilter with
     * RejectReasonDAO.SEARCH_DESCRIPTION as the field name and the provided value as the search value.
     *  - If the andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param description The description to search for.
     * @throws IllegalArgumentException if the description is null or an empty String.
     */
    public void hasDescription(String description) {
        Utils.checkString(description, "description", false);
        addToFilter(new LikeFilter(RejectReasonDAO.SEARCH_DESCRIPTION, description));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that contain the provided value as an active status.
     * </p>
     * <p>
     * Implementation Notes:
     *  - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * RejectReasonDAO.SEARCH_ACTIVE_STATUS as the field name and the provided value as the search value.
     *  - If the andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param activeStatus The status to search for. (Generally 1 for TRUE and 0 for FALSE)
     */
    public void hasActiveStatus(int activeStatus) {
        addToFilter(new EqualToFilter(RejectReasonDAO.SEARCH_ACTIVE_STATUS, new Integer(activeStatus)));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that were created within the given Date Range. Nulls may be provided to either the upper or lower bounds to
     * indicate that only one bound needs to be provided.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> The filter that is built
     * may either be a GreaterThanFilter, LessThanFilter or BetweenFilter depending on whether the startDate or
     * endDate or none of them are null. The field name to be used is RejectReasonDAO.SEARCH_CREATED_DATE. - If the
     * andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param startDate The starting date to search for.
     * @param endDate The end date to search for.
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    public void createdWithinDateRange(Date startDate, Date endDate) {
        addToFilter(Utils.createRangerFilter(RejectReasonDAO.SEARCH_CREATED_DATE, startDate, endDate));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that were created by the given username.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * RejectReasonDAO.SEARCH_CREATED_USER as the field name and the provided value as the search value. - If the
     * andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param username The username to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void createdByUser(String username) {
        Utils.checkString(username, "username", false);
        addToFilter(new EqualToFilter(RejectReasonDAO.SEARCH_CREATED_USER, username));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that was last modified within the given date range.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> The filter that is built
     * may either be a GreaterThanFilter, LessThanFilter or BetweenFilter depending on whether the startDate or
     * endDate or none of them are null. The field name to be used is RejectReasonDAO.SEARCH_MODIFICATION_DATE. -
     * If the andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param startDate The starting date to search for.
     * @param endDate The end date to search for.
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    public void modifiedWithinDateRange(Date startDate, Date endDate) {
        addToFilter(Utils.createRangerFilter(RejectReasonDAO.SEARCH_MODIFICATION_DATE, startDate, endDate));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Reject Reasons
     * that was last modified by the provided user.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * RejectReasonDAO.SEARCH_MODIFICATION_USER as the field name and the provided value as the search value. - If
     * the andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param username The username to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void modifiedByUser(String username) {
        Utils.checkString(username, "username", false);
        addToFilter(new EqualToFilter(RejectReasonDAO.SEARCH_MODIFICATION_USER, username));
    }

    /**
     * <p>
     * Retrieves the actual Search Builder filter built according to the previous method calls that defined the
     * search criterion to use. Note that this doesn't reset the state of the builder, and if a new set of criteria
     * needs to be used, then a call to reset() will be necessary.
     * </p>
     * <p>
     * Implementation Notes: - Return a clone of the andFilter.
     * </p>
     *
     *
     * @return A Search Filter that may be used in the RejectReasonDAO.
     * @throws IllegalStateException if no criteria has yet been set and the andFilter is null.
     */
    public Filter buildFilter() {
        if ((andFilter == null) || (andFilter.getFilters().size() == 0)) {
            throw new IllegalStateException("The filter has no criteries.");
        }

        return (Filter) andFilter.clone();
    }

    /**
     * <p>
     * Resets any criterion that were specified in the previous method calls. It will be as if the
     * CompanySearchBuilder was newly constructed.
     * </p>
     *
     */
    public void reset() {
        andFilter = null;
    }
}
