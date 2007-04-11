/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author enefem21
 * @version 3.1
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ExpenseEntriesReportBuilderTest.suite());
        suite.addTest(FixedBillingEntriesReportBuilderTest.suite());
        suite.addTest(InformixFilterTest.suite());
        suite.addTest(InformixReportDAOTest.suite());
        suite.addTest(InformixReportSearchBuilderTest.suite());
        suite.addTest(ReportDAOFactoryTest.suite());
        suite.addTest(TimeEntriesReportBuilderTest.suite());

        return suite;
    }

}
