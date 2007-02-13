/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.timetracker.report.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

import com.topcoder.timetracker.report.failuretests.dbhandler.DBHandlerFactoryTest;
import com.topcoder.timetracker.report.failuretests.dbhandler.InformixDBHandlerTest;
import com.topcoder.timetracker.report.failuretests.htmlreport.ExpenseReportTest;
import com.topcoder.timetracker.report.failuretests.htmlreport.TimeExpenseReportTest;
import com.topcoder.timetracker.report.failuretests.htmlreport.TimeReportTest;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 2.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BasicColumnDecoratorTest.suite());
        suite.addTest(EqualityFilterTest.suite());
        suite.addTest(RangeFilterTest.suite());
        suite.addTest(ReportConfigurationTest.suite());
        suite.addTest(ReportDisplayTagTest.suite());
        suite.addTest(ReportFactoryTest.suite());

        suite.addTest(TimeReportTest.suite());
        suite.addTest(TimeExpenseReportTest.suite());
        suite.addTest(ExpenseReportTest.suite());

        suite.addTest(DBHandlerFactoryTest.suite());
        suite.addTest(InformixDBHandlerTest.suite());
        return suite;
    }

}
