/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.review.selection;

import com.cronos.onlinereview.review.selection.impl.DefaultReviewApplicationProcessorTests;
import com.cronos.onlinereview.review.selection.impl.DefaultReviewBoardApplicationListenerTests;
import com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithmTests;
import com.cronos.onlinereview.review.selection.impl.ReviewerCoefficientUnitTests;
import com.cronos.onlinereview.review.selection.impl.WorkloadFactorCalculatorConfigurationExceptionUnitTests;
import com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculatorUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author xianwenchen
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ReviewApplicationProcessorConfigurationExceptionUnitTests.class);
        suite.addTestSuite(ReviewApplicationProcessorExceptionUnitTests.class);
        suite.addTestSuite(ReviewBoardApplicationListenerConfigurationExceptionUnitTests.class);
        suite.addTestSuite(ReviewBoardApplicationListenerExceptionUnitTests.class);
        suite.addTestSuite(ReviewSelectionConfigurationExceptionUnitTests.class);
        suite.addTestSuite(ReviewSelectionExceptionUnitTests.class);
        suite.addTestSuite(WorkloadFactorCalculatorConfigurationExceptionUnitTests.class);

        suite.addTestSuite(ReviewSelectionResultUnitTests.class);
        suite.addTestSuite(ReviewerCoefficientUnitTests.class);
        suite.addTestSuite(DefaultWorkloadFactorCalculatorUnitTests.class);

        suite.addTestSuite(DefaultReviewBoardApplicationListenerTests.class);
        suite.addTestSuite(DefaultReviewApplicationProcessorTests.class);
        suite.addTestSuite(RatingBasedSelectionAlgorithmTests.class);

        suite.addTestSuite(HelperTests.class);
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
