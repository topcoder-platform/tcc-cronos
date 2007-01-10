/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.report.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AccuracyTestAbstractReport.suite());
        suite.addTest(AccuracyTestBasicColumnDecorator.suite());
        suite.addTest(AccuracyTestColumn.suite());
        suite.addTest(AccuracyTestColumnType.suite());
        suite.addTest(AccuracyTestDBHandlerFactory.suite());
        suite.addTest(AccuracyTestEqualityFilter.suite());
        suite.addTest(AccuracyTestExpenseReport.suite());
        suite.addTest(AccuracyTestFilterCategory.suite());
        suite.addTest(AccuracyTestFilterType.suite());
        suite.addTest(AccuracyTestInformixDBHandler.suite());
        suite.addTest(AccuracyTestReportType.suite());
        suite.addTest(AccuracyTestRangeFilter.suite());
        suite.addTest(AccuracyTestReportCategory.suite());
        suite.addTest(AccuracyTestReportConfiguration.suite());
        suite.addTest(AccuracyTestReportDisplayTag.suite());
        suite.addTest(AccuracyTestReportFactory.suite());
        suite.addTest(AccuracyTestStyleConstant.suite());
        suite.addTest(AccuracyTestTimeReport.suite());
        suite.addTest(AccuracyTestTimeExpenseReport.suite());
        suite.addTestSuite(RenderingTest.class);

        return suite;
    }
}
