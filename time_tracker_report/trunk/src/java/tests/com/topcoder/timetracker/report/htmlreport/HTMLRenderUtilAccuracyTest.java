/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.htmlreport;

import com.cronos.timetracker.report.BaseTimeTrackerReportTest;
import com.cronos.timetracker.report.BasicColumnDecorator;
import com.cronos.timetracker.report.Column;
import com.cronos.timetracker.report.EqualityFilter;
import com.cronos.timetracker.report.FilterCategory;
import com.cronos.timetracker.report.RangeFilter;
import com.cronos.timetracker.report.ReportCategory;
import com.cronos.timetracker.report.ReportConfiguration;
import com.cronos.timetracker.report.ReportConfigurationException;
import com.cronos.timetracker.report.ReportType;
import com.cronos.timetracker.report.StyleConstant;
import com.cronos.timetracker.report.dbhandler.DBHandlerFactory;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import junit.framework.AssertionFailedError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This class contains the accuracy tests for {@link HTMLRenderUtil}. additional test cases are contained in {@link
 * HTMLRenderUtilTest}.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class HTMLRenderUtilAccuracyTest extends BaseTimeTrackerReportTest {
    /**
     * This is an empty {@link HTMLRenderUtil.Aggregator} array instance used in the test cases. It is instantiated in
     * {@link #setUp()}.
     */
    private static final HTMLRenderUtil.Aggregator[] EMPTY_AGGREGATORS = new HTMLRenderUtil.Aggregator[0];
    /**
     * This is the {@link ReportConfiguration} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ReportConfiguration reportConfiguration;
    /**
     * This is an empty {@link ArrayList} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ArrayList list;
    /**
     * This is the {@link DBHandlerFactory} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private DBHandlerFactory dbHandlerFactory;
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
     * This method tests the correctness of {@link #checkRenderResult(ReportConfiguration, String)}.
     * <p/>
     * An assertion failure is expected when using a wrong result file as expected result.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testComparisonValid() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        try {
            checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE.xml");
            throw new Exception("Test setup invalid, unmatched test should throw AssertionFailedError.");
        } catch (AssertionFailedError expected) {
            //expected
        }
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy1() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy2() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_EMPLOYEE_admin.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin" or "user"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy3() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        employeeFilter.addFilterValue("user");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_EMPLOYEE_admin_and_user.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy4() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy5() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "EMPLOYEE_TIME_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy6() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1","Client 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy7() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        clientFilter.addFilterValue("Client 2");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy8() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1","Project Name 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy9() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        projectFilter.addFilterValue("Project Name 2");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy10() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy11() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: None
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy12() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "PROJECT_TIME.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy13() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1","Project Name 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy14() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        projectFilter.addFilterValue("Project Name 2");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy15() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_EMPLOYEE_admin.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin","user"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy16() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        employeeFilter.addFilterValue("user");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_EMPLOYEE_admin_user.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy17() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy18() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy19() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy20() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIME_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy21() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "CLIENT_TIME.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy22() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIME_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy23() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIME_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy24() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIME_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1","Client 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy25() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        clientFilter.addFilterValue("Client 2");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIME_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy26() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIME_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIME}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy27() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIME_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy28() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy29() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_EMPLOYEE_admin.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin" or "user"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy30() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        employeeFilter.addFilterValue("user");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_EMPLOYEE_admin_and_user.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy31() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy32() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "EMPLOYEE_EXPENSE_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy33() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1","Client 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy34() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        clientFilter.addFilterValue("Client 2");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy35() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1","Project Name 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy36() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        projectFilter.addFilterValue("Project Name 2");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy37() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy38() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_EXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: None
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy39() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "PROJECT_EXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy40() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_EXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1","Project Name 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy41() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        projectFilter.addFilterValue("Project Name 2");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_EXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy42() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_EXPENSE_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy43() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "PROJECT_EXPENSE_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy44() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_EXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy45() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_EXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy46() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "CLIENT_EXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy47() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_EXPENSE_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy48() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "CLIENT_EXPENSE_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy49() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_EXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1","Client 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy50() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        clientFilter.addFilterValue("Client 2");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_EXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy51() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_EXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#EXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy52() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_EXPENSE_BILLABLE_0.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy53() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy54() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_EMPLOYEE_admin.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE} values: "admin" or "user"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy55() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        employeeFilter.addFilterValue("admin");
        employeeFilter.addFilterValue("user");
        list.add(employeeFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_EMPLOYEE_admin_and_user.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy56() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy57() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "EMPLOYEE_TIMEEXPENSE_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy58() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1","Client 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy59() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        clientFilter.addFilterValue("Client 2");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy60() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1","Project Name 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy61() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        projectFilter.addFilterValue("Project Name 2");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy62() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy63() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: None
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy64() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "PROJECT_TIMEEXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy65() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIMEEXPENSE_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT} values: "Project Name 1","Project Name 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy66() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        projectFilter.addFilterValue("Project Name 1");
        projectFilter.addFilterValue("Project Name 2");
        list.add(projectFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIMEEXPENSE_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy67() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIMEEXPENSE_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy68() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "PROJECT_TIMEEXPENSE_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy69() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy70() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.PROJECT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "PROJECT_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: none
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy71() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        checkRenderResult(reportConfiguration, "CLIENT_TIMEEXPENSE.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy72() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIMEEXPENSE_DATE_01-01-2005_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#DATE} values: "01-01-2005"-"06-30-2006","01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy73() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        dateFilter.addFilterRange("01-01-2005", "06-30-2006");
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        list.add(dateFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration,
            "CLIENT_TIMEEXPENSE_DATE_01-01-2005_06-30-2006_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy74() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIMEEXPENSE_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT} values: "Client 1","Client 2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy75() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        clientFilter.addFilterValue("Client 1");
        clientFilter.addFilterValue("Client 2");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIMEEXPENSE_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy76() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * Tested Category: {@link ReportCategory#TIMEEXPENSE}
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#BILLABLE} values: "0"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy77() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        billableFilter.addFilterValue("1");
        list.add(billableFilter);
        reportConfiguration.setFilters(list);
        checkRenderResult(reportConfiguration, "CLIENT_TIMEEXPENSE_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Setup:</b> custom column list and custom column names
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy78() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        list.add(new BasicColumnDecorator(Column.DATE.getName(), "when?"));
        list.add(new BasicColumnDecorator(Column.CLIENT.getName(), "who?"));
        list.add(new BasicColumnDecorator(Column.PROJECT.getName(), "which?"));
        list.add(new BasicColumnDecorator(Column.HOURS.getName(), "how long?"));
        list.add(new BasicColumnDecorator(Column.PAY_RATE.getName(), "too much?"));
        checkRenderResult(reportConfiguration, list, "EMPLOYEE_TIME_custom_cols.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Setup:</b> custom prefixes and suffixes
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy79() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        BasicColumnDecorator decorator = new BasicColumnDecorator(Column.DATE.getName(), "when?");
        decorator.setPrefix("__[");
        decorator.setSuffix("]__");
        list.add(decorator);
        list.add(new BasicColumnDecorator(Column.CLIENT.getName(), "who?"));
        list.add(new BasicColumnDecorator(Column.PROJECT.getName(), "which?"));
        decorator = new BasicColumnDecorator(Column.PAY_RATE.getName(), "too much?");
        decorator.setPrefix("__[");
        decorator.setSuffix("]__");
        list.add(decorator);
        checkRenderResult(reportConfiguration, list, "EMPLOYEE_TIME_decorated_cols.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Setup:</b> custom styles
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy80() throws Exception {
        reportConfiguration = new ReportConfiguration(ReportCategory.TIME, ReportType.EMPLOYEE,
            DEFAULT_CONFIGURATION_NAMESPACE);
        final HashMap styles = new HashMap();
        styles .put(StyleConstant.TABLE_STYLE, "__styled&fancy__");
        styles .put(StyleConstant.TR_STYLE, "     ");
        styles .put(StyleConstant.TH_STYLE, "escaped?\"");
        styles .put(StyleConstant.TD_STYLE, "__<stylish>__");
        reportConfiguration.setStyles(styles);
        checkRenderResult(reportConfiguration, "EMPLOYEE_TIME_styled.xml");
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])} so that the aggregators are called.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy81() throws Exception {
        final HTMLRenderUtil.Aggregator aggregator = new HTMLRenderUtil.Aggregator(Column.AMOUNT);
        HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, new HTMLRenderUtil.Aggregator[]{aggregator});
        assertEquals(aggregator.getCurrentValue(), new BigDecimal("300.00"));
    }

    /**
     * This method tests the correctness of {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])} so that the aggregators are called.
     * <p/>
     * <b>Setup:</b> filer for {@link Column#CLIENT} with client = "Client 1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableAccuracy82() throws Exception {
        final HTMLRenderUtil.Aggregator aggregator = new HTMLRenderUtil.Aggregator(Column.AMOUNT);
        clientFilter.addFilterValue("Client 1");
        list.add(clientFilter);
        reportConfiguration.setFilters(list);
        HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, new HTMLRenderUtil.Aggregator[]{aggregator});
        assertEquals(aggregator.getCurrentValue(), new BigDecimal("100.00"));
    }

    /**
     * This method executes {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])} with the given {@link ReportConfiguration} and checks the result returned for
     * equality with the expected result read from the XML file with the given filename.
     * <p/>
     * This method adds the default columns for the category and type defined in the given {@link ReportConfiguration}
     * to the configuration.
     * <p/>
     * In case the result returned is not equal to the expected result, an AssertionFailedError is thrown
     *
     * @param rc       the configuration to be used when calling {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     *                 DBHandlerFactory, HTMLRenderUtil.Aggregator[])}
     * @param filename the name of the file that contains the expected render result
     *
     * @throws Exception            in case an unexpected Exception occurs
     * @throws AssertionFailedError in case the retrieved render result is not equal to the expected one loaded from the
     *                              XML file
     */
    private void checkRenderResult(final ReportConfiguration rc, final String filename) throws Exception {
        checkRenderResult(rc, lookupDefaultColumnsConfiguration(rc.getType(), rc.getCategory()), filename);
    }

    /**
     * This method executes {@link HTMLRenderUtil#renderTable(ReportConfiguration, DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])} with the given {@link ReportConfiguration} and checks the result returned for
     * equality with the expected result read from the XML file with the given filename.
     * <p/>
     * In case the result returned is not equal to the expected result, an AssertionFailedError is thrown
     *
     * @param rc       the configuration to be used when calling {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     *                 DBHandlerFactory, HTMLRenderUtil.Aggregator[])}
     * @param filename the name of the file that contains the expected render result
     * @param columns  the list of column decorators to be set as column configuration in the given {@link
     *                 ReportConfiguration}
     *
     * @throws Exception            in case an unexpected Exception occurs
     * @throws AssertionFailedError in case the retrieved render result is not equal to the expected one loaded from the
     *                              XML file
     */
    private void checkRenderResult(final ReportConfiguration rc, final List columns, final String filename)
        throws Exception {
        rc.setColumnDecorators(columns);
        final String rendered = HTMLRenderUtil.renderTable(rc, dbHandlerFactory, EMPTY_AGGREGATORS);
        checkContentsAreEqual("expectedRenderTables/" + filename, rendered);
    }

    /**
     * This method looks up the default column configuration for the given {@link ReportType}  and {@link
     * ReportCategory}from the given {@link ConfigManager} namespace.
     *
     * @param reportType the ReportType to lookup the default column configuration for
     * @param category   the ReportCategory to lookup the default column configuration for
     *
     * @return the list of default {@link com.cronos.timetracker.report.ColumnDecorator}s for the given {@link
     *         ReportType}  and  {@link ReportCategory}, looked up from {@link ConfigManager} namespace {@link
     *         #COLUMNS_CONFIGURATION_NAMESPACE}
     *
     * @throws ReportConfigurationException in case the default column configuration lookup from {@link ConfigManager}
     *                                      fails
     */
    private static List lookupDefaultColumnsConfiguration(final ReportType reportType, final ReportCategory category)
        throws
        ReportConfigurationException {
        final String key = reportType.getType() + "_" + category.getCategory() + "_COLUMNS";
        final String[] stringArray;
        try {
            stringArray = ConfigManager.getInstance().getStringArray(COLUMNS_CONFIGURATION_NAMESPACE, key);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("Unable to load the default columns configuration for report type ["
                + reportType + "] and report category [" + category + "] (looked up from config manager namespace ["
                + COLUMNS_CONFIGURATION_NAMESPACE + "] property [" + key + "]), as the namespace ["
                + COLUMNS_CONFIGURATION_NAMESPACE
                + "] was not found in ConfigManager configuration.", e);

        }
        if (stringArray == null || stringArray.length == 0) {
            throw new ReportConfigurationException("Unable to load the default columns configuration for report type ["
                + reportType + "] and report category [" + category + "] (looked up from config manager namespace ["
                + COLUMNS_CONFIGURATION_NAMESPACE + "] property [" + key + "]), as the ConfigManager property ["
                + key + "]  in namespace [" + COLUMNS_CONFIGURATION_NAMESPACE
                + "] was not defined or did not contain any values.");
        }
        final List ret = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            final String enumName = stringArray[i];
            ret.add(new BasicColumnDecorator(enumName, enumName));
        }
        return ret;
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        list = new ArrayList();
        dbHandlerFactory = new DBHandlerFactory();
        employeeFilter = new EqualityFilter(Column.EMPLOYEE, FilterCategory.EMPLOYEE);
        clientFilter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);
        billableFilter = new EqualityFilter(Column.BILLABLE, FilterCategory.BILLABLE);
        dateFilter = new RangeFilter(Column.DATE, FilterCategory.DATE);
        projectFilter = new EqualityFilter(Column.PROJECT, FilterCategory.PROJECT);
    }
}
