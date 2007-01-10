/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.ReportType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>ReportType</code>.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTestReportType extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestReportType.class);
    }

    /**
     * Tests getType().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetType() throws Exception {
        assertTrue("EMPLOYEE is not valid.", ReportType.EMPLOYEE.getType().equals("EMPLOYEE"));
        assertTrue("PROJECT is not valid.", ReportType.PROJECT.getType().equals("PROJECT"));
        assertTrue("CLIENT is not valid.", ReportType.CLIENT.getType().equals("CLIENT"));
    }

    /**
     * Tests toString().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testToString() throws Exception {
        assertTrue("EMPLOYEE is not valid.", ReportType.EMPLOYEE.toString().equals("EMPLOYEE"));
        assertTrue("PROJECT is not valid.", ReportType.PROJECT.toString().equals("PROJECT"));
        assertTrue("CLIENT is not valid.", ReportType.CLIENT.toString().equals("CLIENT"));
    }

    /**
     * Tests getReportTypes().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetReportTypes() throws Exception {
        List types = ReportType.getReportTypes();

        assertTrue("There should be 3 members.", types.size() == 3);

        assertTrue("EMPLOYEE is not valid.", types.contains(ReportType.EMPLOYEE));
        assertTrue("PROJECT is not valid.", types.contains(ReportType.PROJECT));
        assertTrue("CLIENT is not valid.", types.contains(ReportType.CLIENT));
    }
}
