/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.dbhandler;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.report.BaseTimeTrackerReportTest;
import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.EqualityFilter;
import com.topcoder.timetracker.report.FilterCategory;
import com.topcoder.timetracker.report.RangeFilter;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.ReportType;
import junit.framework.AssertionFailedError;
import org.dbunit.Assertion;
import org.dbunit.database.CachedResultSetTable;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseTableMetaData;
import org.dbunit.database.ForwardOnlyResultSetTable;
import org.dbunit.database.IResultSetTable;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * This class contains the accuracy tests for {@link InformixDBHandler}. Additional test cases are contained in {@link
 * InformixDBHandlerTest}
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InformixDBHandlerAccuracyTest extends BaseTimeTrackerReportTest {
    /**
     * This is an empty {@link ArrayList} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ArrayList filters;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#EMPLOYEE} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter employeeFilter;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#CLIENT} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter clientFilter;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#BILLABLE} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter billableFilter;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#PROJECT} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter projectFilter;
    /**
     * This an {@link RangeFilter} instance for {@link Column#DATE} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private RangeFilter dateFilter;
    /**
     * This is the {@link InformixDBHandler} instance tested in the test cases. It is instantiated in {@link #setUp()}.
     */
    private InformixDBHandler informixDBHandler;

    /**
     * This method tests the correctness of {@link #checkExpectedDataset(ReportConfiguration, String)}.
     * <p/>
     * An assertion failure is expected when using a wrong result file as expected result.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testComparisonValid() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        try {
            checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE.xml");
            throw new Exception("Test setup invalid, unmatched test should throw AssertionFailedError.");
        } catch (AssertionFailedError expected) {
            //expected
        }
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy1() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy2() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_EMPLOYEE_ivern.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern" or "dok"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy3() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        employeeFilter.addFilterValue("dok");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_EMPLOYEE_ivern_and_dok.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy4() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy5() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "EMPLOYEE_TIME_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy6() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1","client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy7() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy8() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1","project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy9() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy10() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy11() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIME_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: None
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy12() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy13() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1","project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy14() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy15() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_EMPLOYEE_ivern.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern","dok"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy16() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        employeeFilter.addFilterValue("dok");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_EMPLOYEE_ivern_dok.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy17() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy18() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy19() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy20() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIME_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy21() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy22() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy23() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy24() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1","client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy25() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy26() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy27() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIME_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy28() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy29() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_EMPLOYEE_ivern.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern" or "dok"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy30() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        employeeFilter.addFilterValue("dok");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_EMPLOYEE_ivern_and_dok.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy31() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy32() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "EMPLOYEE_EXPENSE_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy33() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1","client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy34() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy35() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1","project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy36() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy37() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy38() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_EXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: None
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy39() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "PROJECT_EXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy40() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_EXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1","project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy41() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_EXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy42() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_EXPENSE_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy43() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "PROJECT_EXPENSE_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy44() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_EXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy45() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_EXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy46() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "CLIENT_EXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy47() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_EXPENSE_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy48() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "CLIENT_EXPENSE_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy49() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_EXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1","client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy50() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_EXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy51() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_EXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy52() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_EXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy53() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy54() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_EMPLOYEE_ivern.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "ivern" or "dok"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy55() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("ivern");
        employeeFilter.addFilterValue("dok");
        filters.add(employeeFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_EMPLOYEE_ivern_and_dok.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy56() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy57() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "EMPLOYEE_TIMEEXPENSE_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy58() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1","client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy59() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy60() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1","project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy61() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy62() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy63() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: None
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy64() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIMEEXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy65() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIMEEXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "project1","project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy66() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filters.add(projectFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIMEEXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy67() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIMEEXPENSE_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy68() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "PROJECT_TIMEEXPENSE_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy69() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy70() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE,
            ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "PROJECT_TIMEEXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy71() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIMEEXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy72() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIMEEXPENSE_DATE_01-01-2005_06-30-2005.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2005","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy73() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2005");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filters.add(dateFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration,
            "CLIENT_TIMEEXPENSE_DATE_01-01-2005_06-30-2005_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy74() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIMEEXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "client1","client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy75() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filters.add(clientFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIMEEXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy76() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataAccuracy77() throws Exception {
        ReportConfiguration reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("0");
        filters.add(billableFilter);
        reportConfiguration.setFilters(filters);
        checkExpectedDataset(reportConfiguration, "CLIENT_TIMEEXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method executes {@link InformixDBHandler#getReportData(ReportConfiguration)} with the given {@link
     * ReportCategory} and checks the result returned for equality with the expected result read from the XML file with
     * the given filename.
     * <p/>
     * In case the result returned is not equal to the expected result, an AssertionFailedError is thrown
     *
     * @param rc       the configuration to be used when calling {@link InformixDBHandler
     *                 #getReportData(ReportConfiguration)}
     * @param filename the name of the file that contains the expected dataset as DBUnit XML dataset
     *
     * @throws Exception            in case an unexpected Exception occurs
     * @throws AssertionFailedError in case the retrieved dataset is not equal to the expected one loaded from the XML
     *                              file
     */
    protected void checkExpectedDataset(final ReportConfiguration rc, final String filename) throws Exception {
        final DBConnectionFactoryImpl factory = new DBConnectionFactoryImpl(INFORMIX_DB_CONNECTION_FACTORY_NAMESPACE);
        final Connection jdbcConnection = factory.createConnection(INFORMIX_CONNECTION_PROPERTY_NAME);
        final DatabaseConnection databaseConnection = new DatabaseConnection(jdbcConnection);

        final IResultSetTable table;
        try {
            final ResultSet reportData = informixDBHandler.getReportData(rc);
            final ITableMetaData metaData = DatabaseTableMetaData.createMetaData("RESULT", reportData,
                databaseConnection);
            table = new ForwardOnlyResultSetTable(metaData, reportData);
        } finally {
            databaseConnection.close();
        }
        //
        // use CachedResultSetTable for having scrollable table to obtain row count from,
        // as the default resultset from JDBC driver is not scrollable and thus no
        // row count could be obtained during comparison
        //
        // Use SortedTable because otherwise we would rely
        // on the row order returned from the database, which could change over
        // versions and changed resultset row order would make the testcase fail
        //
        final DefaultDataSet actual = new DefaultDataSet(new SortedTable(new CachedResultSetTable(table)));
        final FlatXmlDataSet expected = new FlatXmlDataSet(getClass().getClassLoader().getResource(
            "expectedDatasets/" + filename));
        Assertion.assertEquals(expected, actual);
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        informixDBHandler = new InformixDBHandler();
        filters = new ArrayList();
        billableFilter = new EqualityFilter(Column.BILLABLE, FilterCategory.BILLABLE);
        clientFilter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);
        dateFilter = new RangeFilter(Column.DATE, FilterCategory.DATE);
        projectFilter = new EqualityFilter(Column.PROJECT, FilterCategory.PROJECT);
        employeeFilter = new EqualityFilter(Column.EMPLOYEE, FilterCategory.EMPLOYEE);
    }
}
