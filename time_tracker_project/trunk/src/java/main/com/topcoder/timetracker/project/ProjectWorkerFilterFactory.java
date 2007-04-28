/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.Date;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Project Workers.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use. The factory is capable of producing
 * filters that conform to a specific schema, and is associated with the <code>ProjectWorkerUtility</code>
 * that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>ProjectWorkerUtility</code>
 * implementation that produced this <code>ProjectWorkerFilterFactory</code> instance.
 * </p>
 *
 * <p>
 * This ensures that the Filters can be recognized by the utility.
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
public interface ProjectWorkerFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * Creates a filter that will select ProjectWorkers based on the id of the TIme Tracker project they
     * are working on.
     * </p>
     *
     * @param projectId the id of the TIme Tracker project.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createProjectIdFilter(long projectId);

    /**
     * <p>
     * Creates a filter that will select ProjectWorkers based on the id of the TIme Tracker user they
     * are working on.
     * </p>
     *
     * @param userId the id of the TIme Tracker user.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createUserIdFilter(long userId);

    /**
     * <p>
     * Creates a filter that will select ProjectWorkers based on the start date of the worker.
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
     * @throws IllegalArgumentException the range specified is invalid (e.g. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * Creates a filter that will select ProjectWorkers based on the end date of the worker.
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
     * @throws IllegalArgumentException the range specified is invalid (e.g. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * Creates a filter that will select ProjectWorkers based on the pay rate of the worker.
     * </p>
     *
     * @param rangeStart The lower bound of the int criterion.
     * @param rangeEnd The upper bound of the int criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the range is invalid (eg. rangeStart is &gt; rangeEnd)
     */
    public Filter createPayRateFilter(double rangeStart, double rangeEnd);
}