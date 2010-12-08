/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Creates suite that aggregates all Accuracy test cases for this component.
     * </p>
     * @return Test suite that aggregates all Accuracy test cases for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DefaultReviewApplicationProcessorAccuracyTest.suite());
        suite.addTest(DefaultReviewBoardApplicationListenerAccuracyTest.suite());
        suite.addTest(DefaultWorkloadFactorCalculatorAccuracyTest.suite());
        suite.addTest(ReviewerCoefficientAccuracyTest.suite());
        suite.addTest(ReviewSelectionResultAccuracyTest.suite());
        suite.addTest(RatingBasedSelectionAlgorithmAccuracyTest.suite());
        return suite;
    }
}
