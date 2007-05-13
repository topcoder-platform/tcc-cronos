/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * This is factory that may be used to build filters for searching Time Tracker Notification entities.
 * </p>
 *
 * <p>
 * It maintains a mapping of filter names that are necessary for the filter criterion that it supports, and builds
 * filters according to the specified column names.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This is thread-safe, since it is not modified after construction.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationFilterFactory implements Serializable {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -1273007500236823519L;

	/**
     * <p>
     * This is the map key to use to specify the filter name for the Project Id.
     * </p>
     */
    public static final String PROJECT_ID_NAME = "PROJECT_ID_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the Company Id.
     * </p>
     */
    public static final String COMPANY_ID_NAME = "COMPANY_ID_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the Client Id.
     * </p>
     */
    public static final String CLIENT_ID_NAME = "CLIENT_ID_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the Resource Id.
     * </p>
     */
    public static final String RESOURCE_ID_NAME = "RESOURCE_ID_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the active flag.
     * </p>
     */
    public static final String ACTIVE_NAME = "ACTIVE_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the Last Sent date.
     * </p>
     */
    public static final String LAST_SENT_NAME = "LAST_SENT_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the Next Send date.
     * </p>
     */
    public static final String NEXT_SEND_NAME = "NEXT_SEND_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the "From" line.
     * </p>
     */
    public static final String FROM_LINE_NAME = "FROM_LINE_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the message of the notification.
     * </p>
     */
    public static final String MESSAGE_NAME = "MESSAGE_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the subject of the notification.
     * </p>
     */
    public static final String SUBJECT_NAME = "SUBJECT_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the creation user of the notification.
     * </p>
     */
    public static final String CREATION_USER_NAME = "CREATION_USER_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the modification user of the notification.
     * </p>
     */
    public static final String MODIFICATION_USER_NAME = "MODIFICATION_USER_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the creation date of the notification.
     * </p>
     */
    public static final String CREATION_DATE_NAME = "CREATION_DATE_NAME";

    /**
     * <p>
     * This is the map key to use to specify the filter name for the modification date of the notification.
     * </p>
     */
    public static final String MODIFICATION_DATE_NAME = "MODIFICATION_DATE_NAME";

    /**
     * <p>
     * This is a mapping of column names to use.  The FilterFactory will use these column names when determining the
     * column name to use when providing a Filter.  The Map may not be empty.
     * </p>
     *
     * <p>
     * Initial Value: Null
     * </p>
     *
     * <p>
     * Accessed In: not accessed
     * </p>
     *
     * <p>
     * Modified In: Not Modified
     * </p>
     *
     * <p>
     * Utilized In: All methods
     * </p>
     *
     * <p>
     * Valid Keys: Not null and not empty Strings. The keys represent a String describing the criterion which the
     * column name is for.
     * </p>
     *
     * <p>
     * Valid Values: Not null and not empty Strings.  The values represent the column name in the persistent store for
     * the given criterion.
     * </p>
     */
    private final Map filterNames;

    /**
     * <p>
     * Creates a MappedNotificationFilterFactory with the specified column definitions.
     * </p>
     *
     * @param filterNames The filter name definitions to use.
     *
     * @throws IllegalArgumentException if filterNames contains null or empty String keys or values, or if it is
     *         missing a Map Entry for the static constants defined in this class.
     */
    public NotificationFilterFactory(Map filterNames) {
        // check if the map contain null or empty String keys or values
        Helper.checkMap(filterNames, "filterNames");

        // check if the map contain all the static constants defined
        Helper.checkMapContainKeys(filterNames,
            new String[] {
                ACTIVE_NAME, CLIENT_ID_NAME, COMPANY_ID_NAME, CREATION_DATE_NAME, CREATION_USER_NAME, FROM_LINE_NAME,
                LAST_SENT_NAME, MESSAGE_NAME, MODIFICATION_DATE_NAME, MODIFICATION_USER_NAME, NEXT_SEND_NAME,
                PROJECT_ID_NAME, RESOURCE_ID_NAME, SUBJECT_NAME
            }, "filterNames");

        this.filterNames = Collections.unmodifiableMap(filterNames);
    }

    /**
     * <p>
     * Creates a filter according to the project id of the notification.
     * </p>
     *
     * @param projectId The id to search for
     *
     * @return A filter with the specified criteria
     *
     * @throws IllegalArgumentException if the id is not positive
     */
    public Filter createProjectIdFilter(long projectId) {
        Helper.checkPositive(projectId, "projectId");

        return new EqualToFilter((String) filterNames.get(PROJECT_ID_NAME), new Long(projectId));
    }

    /**
     * <p>
     * Creates a filter according to the company id of the notification.
     * </p>
     *
     * @param companyId The id to search for.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if the id is not positive
     */
    public Filter createCompanyIdFilter(long companyId) {
        Helper.checkPositive(companyId, "companyId");

        return new EqualToFilter((String) filterNames.get(COMPANY_ID_NAME), new Long(companyId));
    }

    /**
     * <p>
     * Creates a filter according to the client id of the notification.
     * </p>
     *
     * @param clientId The id to search for.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if the id is not positive.
     */
    public Filter createClientIdFilter(long clientId) {
        Helper.checkPositive(clientId, "clientId");

        return new EqualToFilter((String) filterNames.get(CLIENT_ID_NAME), new Long(clientId));
    }

    /**
     * <p>
     * Creates a filter according to the resource id of the notification.
     * </p>
     *
     * @param resourceId The id to search for.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if the id is positive.
     */
    public Filter createResourceIdFilter(long resourceId) {
        Helper.checkPositive(resourceId, "resourceId");

        return new EqualToFilter((String) filterNames.get(RESOURCE_ID_NAME), new Long(resourceId));
    }

    /**
     * <p>
     * Creates a filter according to the active flag of the notification.
     * </p>
     *
     * @param active Whether status is active or not.
     *
     * @return A filter with the specified criteria.
     */
    public Filter createActiveFilter(boolean active) {
        return new EqualToFilter((String) filterNames.get(ACTIVE_NAME), new Integer(active ? 1 : 0));
    }

    /**
     * <p>
     * Creates a filter according to the last sent date of the notification.
     * </p>
     *
     * @param rangeStart the lower bound (inclusive) of the range to search.  If it is null, then it means that it is
     *        unbounded at this end.
     * @param rangeEnd the upper bound (inclusive) of the range to search. If it is null, then it means that it is
     *        unbounded on this end.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if both bounds are null, or if rangeStart is greater than rangeEnd.
     */
    public Filter createLastSentFilter(Date rangeStart, Date rangeEnd) {
        return buildRangeFilter(rangeStart, rangeEnd, (String) filterNames.get(LAST_SENT_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the next send date of the notification.
     * </p>
     *
     * @param rangeStart the lower bound (inclusive) of the range to search.  If it is null, then it means that it is
     *        unbounded at this end.
     * @param rangeEnd the upper bound (inclusive) of the range to search. If it is null, then it means that it is
     *        unbounded on this end.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if both bounds are null, or if rangeStart is greater than rangeEnd.
     */
    public Filter createNextSendFilter(Date rangeStart, Date rangeEnd) {
        return buildRangeFilter(rangeStart, rangeEnd, (String) filterNames.get(NEXT_SEND_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the From line of the notification.
     * </p>
     *
     * @param searchString the String to search for.
     * @param matchType the type of matching you wish to perform during the search.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or String argument is null or empty String.
     */
    public Filter createFromLineFilter(String searchString, StringMatchType matchType) {
        return buildStringFilter(searchString, matchType, (String) filterNames.get(FROM_LINE_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the message of the notification.
     * </p>
     *
     * @param searchString the String to search for.
     * @param matchType the type of matching you wish to perform during the search.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or String argument is null or empty String.
     */
    public Filter createMessageFilter(String searchString, StringMatchType matchType) {
        return buildStringFilter(searchString, matchType, (String) filterNames.get(MESSAGE_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the subject  of the notification.
     * </p>
     *
     * @param searchString the String to search for.
     * @param matchType the type of matching you wish to perform during the search.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or String argument is null or empty String.
     */
    public Filter createSubjectFilter(String searchString, StringMatchType matchType) {
        return buildStringFilter(searchString, matchType, (String) filterNames.get(SUBJECT_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the creation user of the notification.
     * </p>
     *
     * @param searchString the user to search for.
     * @param matchType the type of matching you wish to perform during the search.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or String argument is null or empty String.
     */
    public Filter createCreationUserFilter(String searchString, StringMatchType matchType) {
        return buildStringFilter(searchString, matchType, (String) filterNames.get(CREATION_USER_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the modification user of the notification.
     * </p>
     *
     * @param searchString the user to search for.
     * @param matchType the type of matching you wish to perform during the search.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or String argument is null or empty String.
     */
    public Filter createModificationUserFilter(String searchString, StringMatchType matchType) {
        return buildStringFilter(searchString, matchType, (String) filterNames.get(MODIFICATION_USER_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the creation date of the notification.
     * </p>
     *
     * @param rangeStart the lower bound (inclusive) of the range to search.  If it is null, then it means that it is
     *        unbounded at this end.
     * @param rangeEnd the upper bound (inclusive) of the range to search. If it is null, then it means that it is
     *        unbounded on this end.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if both bounds are null, or if rangeStart is greater than rangeEnd.
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        return buildRangeFilter(rangeStart, rangeEnd, (String) filterNames.get(CREATION_DATE_NAME));
    }

    /**
     * <p>
     * Creates a filter according to the modification creation date of the notification.
     * </p>
     *
     * @param rangeStart the lower bound (inclusive) of the range to search.  If it is null, then it means that it is
     *        unbounded at this end.
     * @param rangeEnd the upper bound (inclusive) of the range to search. If it is null, then it means that it is
     *        unbounded on this end.
     *
     * @return A filter with the specified criteria.
     *
     * @throws IllegalArgumentException if both bounds are null, or if rangeStart is greater than rangeEnd.
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
        return buildRangeFilter(rangeStart, rangeEnd, (String) filterNames.get(MODIFICATION_DATE_NAME));
    }

    /**
     * <p>
     * Create an AndFilter for two date, use the begin date as the lower bound and the end date as the up bound. If one
     * of the date is null, it means it's unbound. it both is null throw IllegalArgumentException
     * </p>
     *
     * @param rangeStart the lower bound (inclusive) of the range to search.  If it is null, then it means that it is
     *        unbounded at this end.
     * @param rangeEnd the upper bound (inclusive) of the range to search. If it is null, then it means that it is
     *        unbounded on this end.
     * @param name the prefix name for the criteria
     *
     * @return A filter with the specified criteria
     * @throws IllegalArgumentException if error build the filter
     */
    private Filter buildRangeFilter(Date rangeStart, Date rangeEnd, String name) {
        if ((rangeStart != null) && (rangeEnd != null)) {
            if (rangeStart.after(rangeEnd)) {
                throw new IllegalArgumentException("Begin date is after end date.");
            }

            return new BetweenFilter(name, rangeStart, rangeEnd);
        } else if ((rangeStart == null) && (rangeEnd == null)) {
            throw new IllegalArgumentException("There should be at most one argument to be null.");
        } else if (rangeStart == null) {
            return new LessThanOrEqualToFilter(name, rangeEnd);
        } else {
            return new GreaterThanOrEqualToFilter(name, rangeStart);
        }
    }

    /**
     * <p>
     * Create the Filter for string search, if the matchType is MATCH_EXACT, an EqualToFilter will be build, otherwise,
     * a LikeFilter will build.
     * </p>
     *
     * @param searchString the search string
     * @param matchType the match type for the search
     * @param name the name of the column
     *
     * @return a filter for search
     * @throws IllegalArgumentException if error build the filter
     */
    private Filter buildStringFilter(String searchString, StringMatchType matchType, String name) {
        // matchType will not be null in our case.
        Helper.checkNull(matchType, "matchType");
        Helper.checkString(searchString, "searchString");

        if (matchType != StringMatchType.EXACT_MATCH) {
            return new LikeFilter(name, matchType.getFilterPrefix() + searchString);
        } else {
            return new EqualToFilter(name, searchString);
        }
    }
}
