/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.util.Date;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an implementation of the <code>ProjectWorkerFilterFactory</code> that may be used for
 * building searches in the database.
 * </p>
 *
 * <p>
 * It maintains a set of column names that are necessary for the filter criterion that it supports, and builds filters
 * according to the specified column names.
 * </p>
 *
 * <p>
 * It extends DbBaseFilterFactory to implement the base functionality
 * that is needed.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and so thread safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectWorkerFilterFactory extends DbBaseFilterFactory implements ProjectWorkerFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Project Worker Start Date.
     * </p>
     */
    public static final String WORKER_START_DATE_COLUMN_NAME = "WORKER_START_DATE";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Project Worker End Date.
     * </p>
     */
    public static final String WORKER_END_DATE_COLUMN_NAME = "WORKER_END_DATE";

    /**
     * <p>
     * This is the map key to use to specify the column name for Worker Project Id.
     * </p>
     */
    public static final String WORKER_PROJECT_ID_COLUMN_NAME = "WORKER_PROJECT_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for Worker User Id.
     * </p>
     */
    public static final String WORKER_USER_ID_COLUMN_NAME = "WORKER_USER_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for Worker Pay Rate.
     * </p>
     */
    public static final String PAY_RATE_COLUMN_NAME = "PAY_RATE";

    /**
     * <p>
     * Creates a <code>DbProjectWorkerFilterFactory</code> instance.
     */
    public DbProjectWorkerFilterFactory() {
        // empty
    }

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
    public Filter createProjectIdFilter(long projectId) {
        Util.checkIdValue(projectId, "project");

        return new EqualToFilter(WORKER_PROJECT_ID_COLUMN_NAME, new Long(projectId));
    }

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
    public Filter createUserIdFilter(long userId) {
        Util.checkIdValue(userId, "user");

        return new EqualToFilter(WORKER_USER_ID_COLUMN_NAME, new Long(userId));
    }

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
    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter(WORKER_START_DATE_COLUMN_NAME, rangeStart, rangeEnd);
    }

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
    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter(WORKER_END_DATE_COLUMN_NAME, rangeStart, rangeEnd);
    }

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
    public Filter createPayRateFilter(double rangeStart, double rangeEnd) {
        return Util.createRangeFilter(PAY_RATE_COLUMN_NAME, new Double(rangeStart), new Double(rangeEnd));
    }
}
