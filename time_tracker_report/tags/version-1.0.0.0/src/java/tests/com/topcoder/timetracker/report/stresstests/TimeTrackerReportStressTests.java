/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.report.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.EqualityFilter;
import com.topcoder.timetracker.report.FilterCategory;
import com.topcoder.timetracker.report.Report;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportFactory;
import com.topcoder.timetracker.report.ReportType;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


/**
 * Stress testing for Time Tracker Report Component.
 *
 * @author brain_cn
 * @version 1.0
 */
public class TimeTrackerReportStressTests extends TestCase {
    private static final String CONFIG_FILE = "stresstests/Time_Tracker_Report_stresstests.xml";

    /** Represents the custom namespace. */
    private static final String CUSTOM_NAMESPACE = "com.topcoder.timetracker.report.StressTest.CustomConfiruation";

    /** Represents the data namespace. */
    private static final String DATA_NAMESPACE = "com.topcoder.timetracker.stresstests.data";

    /** Represents the db namespace. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.report.Informix";

    /** Represents the all namespaces. */
    private static final String[] NAMESPACES =
        new String[] {
            "com.topcoder.timetracker.report.DefaultConfiguration", CUSTOM_NAMESPACE,
            "com.topcoder.timetracker.report.QueriesConfiguration",
            "com.topcoder.timetracker.report.FiltersConfiguration",
            "com.topcoder.timetracker.report.ColumnsConfiguration", "com.topcoder.timetracker.report.ColumnTypes",
            "com.topcoder.timetracker.report.Reports", "com.topcoder.timetracker.report.DBHandlers", DB_NAMESPACE,
            DATA_NAMESPACE
        };

    /** The test count. */
    private static final int NUMBER = 200;

    /** Current time. */
    private static long current = -1;

    /** The ReportFactory instance to test against. */
    private ReportFactory factory = null;

    /**
     * Loads the namespaces under the default configuration file.
     *
     * @throws Exception to JUnit
     */
    public static void loadNamespaces() throws Exception {
        releaseNamespaces();

        ConfigManager config = ConfigManager.getInstance();

        config.add(CONFIG_FILE);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception to JUnit
     */
    public static void releaseNamespaces() throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        for (int i = 0; i < NAMESPACES.length; i++) {
            if (config.existsNamespace(NAMESPACES[i])) {
                config.removeNamespace(NAMESPACES[i]);
            }
        }
    }

    /**
     * Clear the test data.
     *
     * @throws Exception to JUnit
     */
    private void clearData() throws Exception {
        DBConnectionFactoryImpl dbf = new DBConnectionFactoryImpl(DB_NAMESPACE);
        Connection conn = dbf.createConnection();
        Statement stmt = conn.createStatement();
        String[] deleteSqls = getValues("cleardata");

        for (int i = 0; i < deleteSqls.length; i++) {
            stmt.execute(deleteSqls[i]);
        }

        stmt.close();
        conn.close();
    }

    /**
     * Return the filter for client.
     *
     * @return the client filters
     *
     * @throws Exception to JUnit
     */
    private List getClientFilters() throws Exception {
        EqualityFilter filter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);
        List filters = new ArrayList();

        filter.addFilterValue(getValues("client")[0]);
        filters.add(filter);

        return filters;
    }

    /**
     * Return the filter for employee.
     *
     * @return the employee filters
     *
     * @throws Exception to JUnit
     */
    private List getEmployeeFilters() throws Exception {
        EqualityFilter filter = new EqualityFilter(Column.EMPLOYEE, FilterCategory.EMPLOYEE);
        List filters = new ArrayList();

        filter.addFilterValue(getValues("employee")[0]);
        filters.add(filter);

        return filters;
    }

    /**
     * Return the filter for project.
     *
     * @return the project filters
     *
     * @throws Exception to JUnit
     */
    private List getProjectFilters() throws Exception {
        EqualityFilter filter = new EqualityFilter(Column.PROJECT, FilterCategory.PROJECT);
        List filters = new ArrayList();

        filter.addFilterValue(getValues("project")[0]);
        filters.add(filter);

        return filters;
    }

    /**
     * Return the proeprty values for given name.
     *
     * @param name the property name
     *
     * @return the property value array
     *
     * @throws Exception to JUnit
     */
    private String[] getValues(String name) throws Exception {
        return ConfigManager.getInstance().getStringArray(DATA_NAMESPACE, name);
    }

    /**
     * Prepare test data for test.
     *
     * @throws Exception to JUnit
     */
    private void initData() throws Exception {
        DBConnectionFactoryImpl dbf = new DBConnectionFactoryImpl(DB_NAMESPACE);
        Connection conn = dbf.createConnection();
        Statement stmt = conn.createStatement();
        String[] deleteSqls = getValues("cleardata");

        for (int i = 0; i < deleteSqls.length; i++) {
            stmt.execute(deleteSqls[i]);
        }

        String[] initSqls = getValues("loaddata");

        for (int i = 0; i < initSqls.length; i++) {
            stmt.execute(initSqls[i]);
        }

        stmt.close();
        conn.close();
    }

    /**
     * Start to count time.
     */
    private static void start() {
        current = System.currentTimeMillis();
    }

    /**
     * Print test result.
     *
     * @param name the test name
     */
    private static void printResult(String name) {
        System.out.println("The test " + name + " running " + NUMBER + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }

    /** Represents the debug flag. */
    private static final boolean debug = true;
    
    /**
     * Output the content.
     * 
     * @param name the output name
     * @param output the output content
     */
    private static void log(String name, String output) {
        if (debug) {
            System.out.println(name + ": " + output);
        }
    }
    /**
     * Loads the configuration namespaces. Prepares a ReportFactory instance for testing.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    protected void setUp() throws Exception {
        loadNamespaces();
        initData();
        factory = new ReportFactory();
    }

    /**
     * Clears the configuration namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    protected void tearDown() throws Exception {
        clearData();
        releaseNamespaces();
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteExpenseReport_Project() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.EXPENSE);

        String projectOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            projectOutput = report.execute(CUSTOM_NAMESPACE, ReportType.PROJECT, getProjectFilters());
        }

        log("projectOutput", projectOutput);

        String expect = "Total Amount: 333.0".toLowerCase();
        assertTrue("failed to output the result", projectOutput.toLowerCase().indexOf(expect) >= 0);
        printResult("Execute expense report for project type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteExpenseReport_Client() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.EXPENSE);

        String clientOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            clientOutput = report.execute(CUSTOM_NAMESPACE, ReportType.CLIENT, getClientFilters());
        }

        log("clientOutput", clientOutput);

        String expect = "Total Amount: 777.0".toLowerCase();
        assertTrue("failed to output the result", clientOutput.toLowerCase().indexOf(expect) >= 0);
        printResult("Execute expense report for client type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteExpenseReport_Employee() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.EXPENSE);

        String employeeOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            employeeOutput = report.execute(CUSTOM_NAMESPACE, ReportType.EMPLOYEE, getEmployeeFilters());
        }

        log("employeeOutput", employeeOutput);
        String expect = "Total Amount: 333.0".toLowerCase();
        assertTrue("failed to output the result", employeeOutput.toLowerCase().indexOf(expect) >= 0);
        printResult("Execute expense report for employee type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteTimeReport_Project() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.TIME);

        String projectOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            projectOutput = report.execute(CUSTOM_NAMESPACE, ReportType.PROJECT, getProjectFilters());
        }

        log("projectOutput", projectOutput);

        String expect = "Total Hours: 10".toLowerCase();
        assertTrue("failed to output the result", projectOutput.toLowerCase().indexOf(expect) >= 0);
        printResult("Execute time report for project type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteTimeReport_Client() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.TIME);

        String clientOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            clientOutput = report.execute(CUSTOM_NAMESPACE, ReportType.CLIENT, getClientFilters());
        }

        log("clientOutput", clientOutput);

        String expect = "Total Hours: 30".toLowerCase();
        assertTrue("failed to output the result", clientOutput.toLowerCase().indexOf(expect) >= 0);
        printResult("Execute time report for client type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteTimeReport_Employee() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.TIME);

        String employeeOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            employeeOutput = report.execute(CUSTOM_NAMESPACE, ReportType.EMPLOYEE, getEmployeeFilters());
        }

        log("employeeOutput", employeeOutput);

        String expect = "Total Hours: 10".toLowerCase();
        assertTrue("failed to output the result", employeeOutput.toLowerCase().indexOf(expect) >= 0);
        printResult("Execute time report for employee type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteTimeExpenseReport_Project()
        throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.TIMEEXPENSE);

        String projectOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            projectOutput = report.execute(CUSTOM_NAMESPACE, ReportType.PROJECT, getProjectFilters());
        }

        log("projectOutput", projectOutput);

        String expect1 = "Total Hours: 10".toLowerCase();
        String expect2 = "Total Amount: 333.0".toLowerCase();
        assertTrue("failed to output the result",
            (projectOutput.toLowerCase().indexOf(expect1) >= 0) && (projectOutput.toLowerCase().indexOf(expect2) >= 0));
        printResult("Execute time_expense report for project type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteTimeExpenseReport_Client() throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.TIMEEXPENSE);

        String clientOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            clientOutput = report.execute(CUSTOM_NAMESPACE, ReportType.CLIENT, getClientFilters());
        }

        log("clientOutput", clientOutput);

        String expect1 = "Total Hours: 30".toLowerCase();
        String expect2 = "Total Amount: 777.0".toLowerCase();
        assertTrue("failed to output the result",
            (clientOutput.toLowerCase().indexOf(expect1) >= 0) && (clientOutput.toLowerCase().indexOf(expect2) >= 0));
        printResult("Execute time_expense report for client type");
    }

    /**
     * Test of execute expense report.
     *
     * @throws Exception to JUnit
     */
    public void testExecuteTimeExpenseReport_Employee()
        throws Exception {
        Report report = factory.getReport("HTML", ReportCategory.TIMEEXPENSE);

        String employeeOutput = null;
        start();

        for (int i = 0; i < NUMBER; i++) {
            employeeOutput = report.execute(CUSTOM_NAMESPACE, ReportType.EMPLOYEE, getEmployeeFilters());
        }

        log("employeeOutput", employeeOutput);

        String expect1 = "Total Hours: 10".toLowerCase();
        String expect2 = "Total Amount: 333.0".toLowerCase();
        assertTrue("failed to output the result",
            (employeeOutput.toLowerCase().indexOf(expect1) >= 0)
            && (employeeOutput.toLowerCase().indexOf(expect2) >= 0));
        printResult("Execute time_expense report for employee type");
    }
}
