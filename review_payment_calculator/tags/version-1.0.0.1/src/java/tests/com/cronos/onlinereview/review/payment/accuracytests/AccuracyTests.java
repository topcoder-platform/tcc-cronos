/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.accuracytests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 * @author vilain
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Returns the test suite.
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new JUnit4TestAdapter(InvalidReviewersStatisticsExceptionAccuracyTests.class));
        suite.addTest(new JUnit4TestAdapter(ReviewPaymentCalculatorExceptionAccuracyTests.class));
        suite
            .addTest(new JUnit4TestAdapter(ReviewPaymentCalculatorConfigurationExceptionAccuracyTests.class));
        suite.addTest(new JUnit4TestAdapter(ReviewerPaymentAccuracyTests.class));
        suite.addTest(new JUnit4TestAdapter(DefaultReviewPaymentCalculatorAccuracyTests.class));
        return suite;
    }
}