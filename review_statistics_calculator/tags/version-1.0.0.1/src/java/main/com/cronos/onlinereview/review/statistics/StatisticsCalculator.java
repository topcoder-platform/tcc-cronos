/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.management.resource.ReviewerStatistics;

/**
 * <p>
 * This interface represents a reviewer statistics calculator that can calculate reviewer statistics for specified
 * software contests. Additionally it distributes eligibility points pool among all secondary reviewers. This
 * interface extends Configurable interface to support configuration via Configuration API component.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface must be thread safe. It's assumed that configure() method
 * will be called just once right after instantiation, before calling any business methods.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface StatisticsCalculator extends Configurable {
    /**
     * Calculates reviewer statistics for the project with the specified ID using the given eligibility points pool
     * size. Statistics data are calculated for the primary reviewer and all secondary reviewers.
     *
     * @param projectId the ID of the project
     * @param eligibilityPointsPool the eligibility points pool for this contest
     *
     * @throws IllegalArgumentException if projectId <= 0 or eligibilityPointsPool < 0
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     * @throws ProjectNotFoundException if project with the given ID doesn't exist in persistence
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     * @throws StatisticsCalculatorException if some other error occurred
     *
     * @return the calculated reviewer statistics (not null, doesn't contain null)
     */
    public ReviewerStatistics[] calculateStatistics(long projectId, double eligibilityPointsPool)
        throws StatisticsCalculatorException;
}
