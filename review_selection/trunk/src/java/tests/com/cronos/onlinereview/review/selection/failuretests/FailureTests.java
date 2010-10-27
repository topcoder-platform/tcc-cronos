/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.review.selection.failuretests;

import com.cronos.onlinereview.review.selection.failuretests.impl.DefaultReviewApplicationProcessorFailureTests;
import com.cronos.onlinereview.review.selection.failuretests.impl.DefaultReviewBoardApplicationListenerFailureTests;
import com.cronos.onlinereview.review.selection.failuretests.impl.RatingBasedSelectionAlgorithmFailureTests;
import com.cronos.onlinereview.review.selection.failuretests.impl.ReviewerCoefficientFailureTests;
import com.cronos.onlinereview.review.selection.failuretests.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculatorFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DefaultWorkloadFactorCalculatorFailureTests.class);
        suite.addTestSuite(ReviewSelectionResultFailureTests.class);
        suite.addTestSuite(ReviewerCoefficientFailureTests.class);
        suite.addTestSuite(DefaultReviewBoardApplicationListenerFailureTests.class);
        suite.addTestSuite(DefaultReviewApplicationProcessorFailureTests.class);
        suite.addTestSuite(RatingBasedSelectionAlgorithmFailureTests.class);
        return suite;
    }
}