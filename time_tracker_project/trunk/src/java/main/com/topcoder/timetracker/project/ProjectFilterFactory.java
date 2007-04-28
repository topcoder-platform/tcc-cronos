/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.Date;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Time Tracker Projects.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use.
 * The factory is capable of producing filters that conform to a specific schema, and is associated with
 * the <code>ProjectUtility</code> that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the ProjectUtility implementation
 * that produced this ProjectFilterFactory instance.  This ensures that the Filters can be recognized by
 * the utility.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this
 * factory with any of the Composite Filters in the Search Builder Component (<code>AndFilter</code>,
 * <code>OrFilter</code>, etc.)
 * </p>
 *
 * <p>
 * Note that all ranges specified are inclusive of the boundaries.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations of this interface are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the id of the
     * Time Tracker Company that owns the project.
     * </p>
     *
     * @param companyId the id of the Time Tracker Company that owns the project.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createCompanyIdFilter(long companyId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the id of the Time
     * Tracker Client for which work is being done.
     * </p>
     *
     * @param clientId the id of the Time Tracker Client for which work is being done.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createClientIdFilter(long clientId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the name of the project.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param matchType The enum to specify the method of matching the Strings
     * @param name the name of the project.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String.
     */
    public Filter createNameFilter(StringMatchType matchType, String name);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the start date of the Project.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with start
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with start
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with start date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the end date of the Project.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with end
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with end
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with end date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether a project manager
     * with the provided user id has been assigned to it.
     * </p>
     *
     * @param managerId The user id of the project manager.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createContainsProjectManagerFilter(long managerId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether a project worker
     * with the provided user id has been assigned to it.
     * </p>
     *
     * @param workerId The user id of the worker.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createContainsProjectWorkerFilter(long workerId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether an Entry
     * (<tt>Time</tt>/<tt>Expense</tt>/<tt>Fixed Billing</tt>) with the provided id has been
     * associated with it.
     * </p>
     *
     * @param entryId The id of the entry.
     * @param type The type of the entry.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the entryId &lt;= 0 or if the type is null.
     */
    public Filter createContainsEntryFilter(long entryId, EntryType type);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether it is active or not.
     * </p>
     *
     * @param isActive Whether the project is active or not.
     * @return A filter that will be based off the specified criteria.
     */
    public Filter createIsActiveFilter(boolean isActive);
}
