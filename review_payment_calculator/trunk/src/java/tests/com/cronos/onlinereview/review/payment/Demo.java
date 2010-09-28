/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment;

import org.junit.Test;

import com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;

import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class Demo {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Demo API usage of <code>ReviewPaymentCalculator</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemo() throws Exception {
        // Create an instance of reviewPaymentCalculator
        ReviewPaymentCalculator reviewPaymentCalculator = new DefaultReviewPaymentCalculator();

        // Get configuration for DefaultReviewPaymentCalculator
        ConfigurationObject config = TestsHelper.getConfig();

        // Configure review payment calculator
        reviewPaymentCalculator.configure(config);

        // Prepare review statistics data
        ReviewerStatistics[] statistics = new ReviewerStatistics[3];
        ReviewerStatistics primaryReviewerStats =
            TestsHelper.getReviewerStatistics(1, 1001, 0.85, -1.0, -1.0, -1.0, StatisticsType.HISTORY);
        statistics[0] = primaryReviewerStats;
        ReviewerStatistics firstSecondaryReviewerStats =
            TestsHelper.getReviewerStatistics(1, 1002, 1, 0.61, 0.85, -1.0, StatisticsType.HISTORY);
        statistics[1] = firstSecondaryReviewerStats;
        ReviewerStatistics secondSecondaryReviewerStats =
            TestsHelper.getReviewerStatistics(1, 1003, 0.74, 0.39, 1, -1.0, StatisticsType.HISTORY);
        statistics[2] = secondSecondaryReviewerStats;

        // Compute review payment for project with ID=1
        // Use $120 as a base primary payment
        // Use $200 as a secondary payment pool
        ReviewerPayment[] reviewerPayments = reviewPaymentCalculator.computeReviewPayment(1, 120, 200, statistics);
        // reviewerPayments.length must be 3
        // reviewerPayments[0].getProjectId() must be 1
        // reviewerPayments[0].getReviewerId() must be 1001
        // reviewerPayments[0].getPaymentAmount() must be 102
        // reviewerPayments[1].getProjectId() must be 1
        // reviewerPayments[1].getReviewerId() must be 1002
        // reviewerPayments[1].getPaymentAmount() must be 142.78775...
        // reviewerPayments[2].getProjectId() must be 1
        // reviewerPayments[2].getReviewerId() must be 1003
        // reviewerPayments[2].getPaymentAmount() must be 57.21224...
    }
}
