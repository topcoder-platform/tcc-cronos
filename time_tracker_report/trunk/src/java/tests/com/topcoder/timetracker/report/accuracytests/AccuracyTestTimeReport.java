/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.Column;
import com.cronos.timetracker.report.EqualityFilter;
import com.cronos.timetracker.report.FilterCategory;
import com.cronos.timetracker.report.RangeFilter;
import com.cronos.timetracker.report.ReportCategory;
import com.cronos.timetracker.report.ReportType;
import com.cronos.timetracker.report.htmlreport.TimeReport;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;


/**
 * Accuracy test for <code>TimeReport</code>.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTestTimeReport extends AccuracyBaseTest {
    /** The value of the project filter used for test. */
    private static final String PROJECT_FILTER_VALUE = "Project Name 1";

    /** The value of the employee filter used for test. */
    private static final String EMPLOYEE_FILTER_VALUE = "admin";

    /** The value of the client filter used for test. */
    private static final String CLIENT_FILTER_VALUE = "Client 1";

    /** The lower value of the date filter used for test. */
    private static final String DATE_FILTER_LOWER_VALUE = "01-01-2006";

    /** The upper value of the date filter used for test. */
    private static final String DATE_FILTER_UPPER_VALUE = "02-01-2006";

    /** The value of the namespace used for test. */
    private static final String NAMESPACE = "com.cronos.timetracker.report.CustomConfiguration";

    /** The instance of the <code>TimeReport</code> used for test. */
    private TimeReport report = null;

    /** The filters list used for test. */
    private List filters;

    /** The date filter used for test. */
    private RangeFilter dateFilter;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestTimeReport.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        super.setUp();
        fillTables();

        dateFilter = new RangeFilter(Column.DATE, FilterCategory.DATE);
        dateFilter.addFilterRange(DATE_FILTER_LOWER_VALUE, DATE_FILTER_UPPER_VALUE);

        filters = new ArrayList();
        filters.add(dateFilter);

        report = new TimeReport();
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
     * Tests Creating <code>TimeReport</code> instance.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertTrue("The format of the report is not valid.", report.getFormat().equals("HTML"));
        assertTrue("The category of the report is not valid.", report.getCategory().equals(ReportCategory.TIME));
    }

    /**
     * Tests executeReport with the report of the client type.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testExecuteReport1() throws Exception {
        EqualityFilter clientFilter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);

        clientFilter.addFilterValue(CLIENT_FILTER_VALUE);

        filters.add(clientFilter);

        System.out.println("The output message from AccuracyTestTimeReport#testExecuteReport1():");
        System.out.println(report.execute(NAMESPACE, ReportType.CLIENT, filters, null));
    }

    /**
     * Tests executeReport with the report of the employee type.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testExecuteReport2() throws Exception {
        EqualityFilter employeeFilter = new EqualityFilter(Column.EMPLOYEE, FilterCategory.EMPLOYEE);

        employeeFilter.addFilterValue(EMPLOYEE_FILTER_VALUE);

        filters.add(employeeFilter);

        System.out.println("The output message from AccuracyTestTimeReport#testExecuteReport2():");
        System.out.println(report.execute(NAMESPACE, ReportType.EMPLOYEE, filters, null));
    }

    /**
     * Tests executeReport with the report of the project type.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testExecuteReport3() throws Exception {
        EqualityFilter projectFilter = new EqualityFilter(Column.PROJECT, FilterCategory.PROJECT);

        projectFilter.addFilterValue(PROJECT_FILTER_VALUE);

        filters.add(projectFilter);

        System.out.println("The output message from AccuracyTestTimeReport#testExecuteReport3():");
        System.out.println(report.execute(NAMESPACE, ReportType.PROJECT, filters, null));
    }
}
