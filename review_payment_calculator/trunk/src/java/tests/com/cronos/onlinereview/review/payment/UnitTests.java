/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment;

import com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculatorUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());
        suite.addTest(DefaultReviewPaymentCalculatorUnitTests.suite());
        suite.addTest(ReviewerPaymentUnitTests.suite());

        // Exceptions
        suite.addTest(ReviewPaymentCalculatorExceptionUnitTests.suite());
        suite.addTest(InvalidReviewersStatisticsExceptionUnitTests.suite());
        suite.addTest(ReviewPaymentCalculatorConfigurationExceptionUnitTests.suite());

        return suite;
    }

}
