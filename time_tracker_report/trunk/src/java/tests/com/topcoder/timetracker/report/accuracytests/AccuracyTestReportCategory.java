/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.ReportCategory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>ReportCategory</code>.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTestReportCategory extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestReportCategory.class);
    }

    /**
     * Tests getCategory().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetCategory() throws Exception {
        assertTrue("EXPENSE is not valid.", ReportCategory.EXPENSE.getCategory().equals("EXPENSE"));
        assertTrue("TIME is not valid.", ReportCategory.TIME.getCategory().equals("TIME"));
        assertTrue("TIMEEXPENSE is not valid.", ReportCategory.TIMEEXPENSE.getCategory().equals("TIMEEXPENSE"));
    }

    /**
     * Tests toString().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testToString() throws Exception {
        assertTrue("EXPENSE is not valid.", ReportCategory.EXPENSE.toString().equals("EXPENSE"));
        assertTrue("TIME is not valid.", ReportCategory.TIME.toString().equals("TIME"));
        assertTrue("TIMEEXPENSE is not valid.", ReportCategory.TIMEEXPENSE.toString().equals("TIMEEXPENSE"));
    }

    /**
     * Tests getReportCategories().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetReportCategories() throws Exception {
        List categories = ReportCategory.getReportCategories();

        assertTrue("There should be 3 members.", categories.size() == 3);

        assertTrue("EXPENSE is not valid.", categories.contains(ReportCategory.EXPENSE));
        assertTrue("TIME is not valid.", categories.contains(ReportCategory.TIME));
        assertTrue("TIMEEXPENSE is not valid.", categories.contains(ReportCategory.TIMEEXPENSE));
    }
}
