/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;

/**
 * <p>
 * This interface represents a review payment calculator. It provides a method for calculation payments of primary and
 * secondary reviewers for specific project based on base primary payment amount, secondary payment pool and reviewer
 * statistics for this contest. Additionally it provides configure() method to support consistent configuration
 * mechanism with use Configuration API component among all the implementations.
 * </p>
 *
 * <p>
 * <em>Sample Code: </em>
 * <pre>
 * // Create an instance of reviewPaymentCalculator
 * ReviewPaymentCalculator reviewPaymentCalculator = new DefaultReviewPaymentCalculator();
 *
 * // Get configuration for DefaultReviewPaymentCalculator
 * ConfigurationObject config = TestsHelper.getConfig();
 *
 * // Configure review payment calculator
 * reviewPaymentCalculator.configure(config);
 *
 * // Prepare review statistics data
 * ReviewerStatistics[] statistics = new ReviewerStatistics[3];
 * ReviewerStatistics primaryReviewerStats =
 *     TestsHelper.getReviewerStatistics(1, 1001, 0.85, -1.0, -1.0, -1.0, StatisticsType.HISTORY);
 * statistics[0] = primaryReviewerStats;
 * ReviewerStatistics firstSecondaryReviewerStats =
 *     TestsHelper.getReviewerStatistics(1, 1002, 1, 0.61, 0.85, -1.0, StatisticsType.HISTORY);
 * statistics[1] = firstSecondaryReviewerStats;
 * ReviewerStatistics secondSecondaryReviewerStats =
 *     TestsHelper.getReviewerStatistics(1, 1003, 0.74, 0.39, 1, -1.0, StatisticsType.HISTORY);
 * statistics[2] = secondSecondaryReviewerStats;
 *
 * // Compute review payment for project with ID=1
 * // Use $120 as a base primary payment
 * // Use $200 as a secondary payment pool
 * ReviewerPayment[] reviewerPayments = reviewPaymentCalculator.computeReviewPayment(1, 120, 200, statistics);
 * // reviewerPayments.length must be 3
 * // reviewerPayments[0].getProjectId() must be 1
 * // reviewerPayments[0].getReviewerId() must be 1001
 * // reviewerPayments[0].getPaymentAmount() must be 102
 * // reviewerPayments[1].getProjectId() must be 1
 * // reviewerPayments[1].getReviewerId() must be 1002
 * // reviewerPayments[1].getPaymentAmount() must be 142.78775...
 * // reviewerPayments[2].getProjectId() must be 1
 * // reviewerPayments[2].getReviewerId() must be 1003
 * // reviewerPayments[2].getPaymentAmount() must be 57.21224...
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface must be thread safe. It's assumed that
 * configure() method will be called just once right after instantiation, before calling computeReviewPayment().
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface ReviewPaymentCalculator {
    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if config is <code>null</code>.
     * @throws ReviewPaymentCalculatorConfigurationException
     *             if some error occurred when initializing an instance using the given configuration.
     */
    public void configure(ConfigurationObject config);

    /**
     * <p>
     * Computes the review payments for specific project using the given calculated reviewer statistics.
     * </p>
     *
     * @param primaryPayment
     *            the primary reviewer's payment amount.
     * @param projectId
     *            the ID of the project.
     * @param secondaryPaymentPool
     *            the payment pool for secondary reviewers.
     * @param statistics
     *            the reviewer statistics for all reviewers.
     *
     * @return the calculated reviewer payments (not <code>null</code>, doesn't contain <code>null</code>;
     *         number of elements is equal to statistics.length).
     *
     * @throws IllegalArgumentException
     *             if projectId, primaryPayment or secondaryPaymentPool &lt;= <code>0</code>; if statistics is
     *             <code>null</code>, contains <code>null</code> or statistics.length &lt;= <code>2</code>.
     * @throws IllegalStateException
     *             if calculator was not configured properly (thrown by implementation that require configuration
     *             only).
     * @throws InvalidReviewersStatisticsException
     *             if the provided reviewer statistics is invalid (e.g. number of statistics for primary reviewer is
     *             not equal to <code>1</code>, coefficient values are out of the expected range).
     * @throws ReviewPaymentCalculatorException
     *             if some other error occurred.
     */
    public ReviewerPayment[] computeReviewPayment(long projectId, double primaryPayment, double secondaryPaymentPool,
        ReviewerStatistics[] statistics) throws ReviewPaymentCalculatorException;
}
