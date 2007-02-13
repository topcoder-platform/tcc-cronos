/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.htmlreport.ExpenseReport;
import com.topcoder.timetracker.report.htmlreport.TimeExpenseReport;
import com.topcoder.timetracker.report.htmlreport.TimeReport;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Accuracy test for <code>AbstractReport</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestAbstractReport extends AccuracyBaseTest {
    /** The value of the format used for test. */
    private static final String FORMAT = "HTML";

    /** The instance of the TimeReport used for test. */
    private TimeReport timeReport = null;

    /** The instance of the TimeReport used for test. */
    private ExpenseReport expenseReport = null;

    /** The instance of the TimeReport used for test. */
    private TimeExpenseReport timeExpenseReport = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestAbstractReport.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        super.setUp();

        timeReport = new TimeReport();
        expenseReport = new ExpenseReport();
        timeExpenseReport = new TimeExpenseReport();
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
     * Tests constructor.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertTrue("The format of timeReport is not valid.", timeReport.getFormat().equals(FORMAT));
        assertTrue("The format of expenseReport is not valid.", expenseReport.getFormat().equals(FORMAT));
        assertTrue("The format of timeExpenseReport is not valid.", timeExpenseReport.getFormat().equals(FORMAT));

        assertTrue("The category of timeReport is not valid.", timeReport.getCategory().equals(ReportCategory.TIME));
        assertTrue("The category of expenseReport is not valid.",
            expenseReport.getCategory().equals(ReportCategory.EXPENSE));
        assertTrue("The category of timeExpenseReport is not valid.",
            timeExpenseReport.getCategory().equals(ReportCategory.TIMEEXPENSE));
    }

    /**
     * Tests execute().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testExecute() throws Exception {
        // The execute() method will be tested in the test cases of the subclasses.
    }
}
