/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ExpenseEntriesReportBuilderAccuracyTests.class);
        suite.addTestSuite(ExpenseEntryReportAccuracyTests.class);
        suite.addTestSuite(FixedBillingEntriesReportBuilderAccuracyTests.class);
        suite.addTestSuite(FixedBillingEntryReportAccuracyTests.class);
        suite.addTestSuite(InformixFilterAccuracyTests.class);
        suite.addTestSuite(InformixReportDAOAccuracyTests.class);
        suite.addTestSuite(InformixReportSearchBuilderAccuracyTests.class);
        suite.addTestSuite(ReportDAOFactoryAccuracyTests.class);
        suite.addTestSuite(ReportEntryBeanAccuracyTests.class);
        suite.addTestSuite(TimeEntriesReportBuilderAccuracyTests.class);
        suite.addTestSuite(TimeEntryReportAccuracyTests.class);
        return suite;
    }
}
