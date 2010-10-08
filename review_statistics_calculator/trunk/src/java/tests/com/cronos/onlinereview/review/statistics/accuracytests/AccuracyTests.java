/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.review.statistics.accuracytests;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({AccuracyCalculatorImplAccTests.class,
    CoverageCalculatorImplAccTests.class,
    ReviewerStatisticsCalculatorAccTests.class,
    TimelineReliabilityCalculatorImplAccTests.class
})
public class AccuracyTests {
}
