/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.review.statistics;

import com.cronos.onlinereview.review.statistics.impl.AccuracyCalculatorImplUnitTests;
import com.cronos.onlinereview.review.statistics.impl.BaseCalculatorUnitTests;
import com.cronos.onlinereview.review.statistics.impl.CoverageCalculatorImplUnitTests;
import com.cronos.onlinereview.review.statistics.impl.Demo;
import com.cronos.onlinereview.review.statistics.impl.TimelineReliabilityCalculatorImplUnitTests;
import com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculatorUnitTests;

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

        // Exceptions
        suite.addTestSuite(AccuracyCalculationExceptionUnitTests.class);
        suite.addTestSuite(CoverageCalculationExceptionUnitTests.class);
        suite.addTestSuite(PersistenceExceptionUnitTests.class);
        suite.addTestSuite(ProjectNotFoundExceptionUnitTests.class);
        suite.addTestSuite(StatisticsCalculatorConfigurationExceptionUnitTests.class);
        suite.addTestSuite(TimelineReliabilityCalculationExceptionUnitTests.class);

        // Classes
        suite.addTestSuite(AccuracyCalculatorImplUnitTests.class);
        suite.addTestSuite(BaseCalculatorUnitTests.class);
        suite.addTestSuite(CoverageCalculatorImplUnitTests.class);
        suite.addTestSuite(ReviewerStatisticsCalculatorUnitTests.class);
        suite.addTestSuite(TimelineReliabilityCalculatorImplUnitTests.class);

        suite.addTestSuite(Demo.class);
        
        return suite;
    }

}
