/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportFactory;
import com.topcoder.timetracker.report.htmlreport.ExpenseReport;
import com.topcoder.timetracker.report.htmlreport.TimeExpenseReport;
import com.topcoder.timetracker.report.htmlreport.TimeReport;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Accuracy test for <code>ReportFactory</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestReportFactory extends AccuracyBaseTest {
    /** The format of report factory used for test. */
    private static final String FORMAT = "HTML";

    /** The instance of ReportFactory used for test. */
    private ReportFactory factory = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestReportFactory.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        super.setUp();

        factory = new ReportFactory();
    }

    /**
     * See javadoc for junit.framework.TestCase#tearDown().
     *
     * @throws Exception exception that occurs during the tear down process.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests creating <code>ReportFactory</code> instance.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertNotNull("The instance of the ReportFactory is not valid.", factory);
    }

    /**
     * Tests getReport().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetReport() throws Exception {
        assertTrue("The instance of the ExpenseReport is not valid.",
            factory.getReport(FORMAT, ReportCategory.EXPENSE) instanceof ExpenseReport);
        assertTrue("The instance of the TimeReport is not valid.",
            factory.getReport(FORMAT, ReportCategory.TIME) instanceof TimeReport);
        assertTrue("The instance of the TimeExpenseReport is not valid.",
            factory.getReport(FORMAT, ReportCategory.TIMEEXPENSE) instanceof TimeExpenseReport);
    }
}
