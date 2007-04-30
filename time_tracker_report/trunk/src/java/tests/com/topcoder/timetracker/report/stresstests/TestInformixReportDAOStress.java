/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.stresstests;

import java.io.File;
import java.sql.Connection;
import java.util.Iterator;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.report.informix.FixedBillingEntryReport;
import com.topcoder.timetracker.report.informix.InformixFilter;
import com.topcoder.timetracker.report.informix.InformixReportDAO;
import com.topcoder.timetracker.report.informix.TimeEntryReport;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixReportDAO </code>.
 *
 * @author Chenhong
 * @version 3.1
 */
public class TestInformixReportDAOStress extends TestCase {

    /**
     * Represents the InformixReportDAO instance for testing.
     */
    private InformixReportDAO dao = null;

    /**
     * Set up the environment.
     *
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        Connection connection = null;
        try {
            ConfigManager cm = ConfigManager.getInstance();
            for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
                cm.removeNamespace((String) iter.next());
            }

            cm.add(new File("test_files/stress/DBConnectionFactory.xml").getCanonicalPath());
            cm.add(new File("test_files/stress/config.xml").getCanonicalPath());

            dao = new InformixReportDAO();

            connection = DBUtil.getConnection();

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordIntoClient(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRerordInto_client_project(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_UserAccount(connection, i, 1, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_expense_type(connection, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_expense_status(connection, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.inserRecordIntoProject(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRerordIntoexpense_entry(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecord_project_expense(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_fix_bill_entry(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecord_fix_bill_type(connection, i);
            }
            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_fix_bill_status(connection, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_project_fix_bill(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordIntoTask_type(connection, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordIntotime_status(connection, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_time_entry(connection, i, 1);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_project_worker(connection, i, i);
            }

            for (int i = 1; i <= 20; i++) {
                DBUtil.insertRecordInto_project_time(connection, i, 1);
            }

            DBUtil.closeConnection(connection);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Tear down the environment.
     * <p>
     * Clear the records in all tables, and also clear the namespace contained in the config
     * manager.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        DBUtil.clearTables();
        DBUtil.clearNamespaces();
    }

    /**
     * Stress test case for method getExpenseEntriesReport.
     *
     * @throws Exception to junit.
     */
    public void testGetExpenseEntriesReport() throws Exception {
        Filter filter = InformixFilter.getFilterCompanies(new long[] {1 });

        long start = System.currentTimeMillis();
        ExpenseEntryReport[] reports = dao.getExpenseEntriesReport(filter, new String[] {}, new boolean[] {});

        long end = System.currentTimeMillis();

        System.out.println("search ExpenseEntryReport "
                + reports.length
                + " records cost "
                + (end - start)
                / 1000.0
                + " seconds.");
    }

    /**
     * Stress test case for method getFixedBillingEntriesReport.
     *
     * @throws Exception to junit.
     */
    public void testGetFixedBillingEntriesReport() throws Exception {
        Filter filter = InformixFilter.getFilterCompanies(new long[] {1 });

        long start = System.currentTimeMillis();
        FixedBillingEntryReport[] reports =
                dao.getFixedBillingEntriesReport(filter, new String[0], new boolean[0]);

        long end = System.currentTimeMillis();

        System.out.println("search FixedBillingEntryReport "
                + reports.length
                + " records cost "
                + (end - start)
                / 1000.0
                + " seconds.");
    }

    /**
     * Stress test case for method getTimeEntriesReport.
     *
     * @throws Exception to junit.
     */
    public void testGetTimeEntriesReport() throws Exception {
        // if the filter is null (not set), all records should be returned.
        long start = System.currentTimeMillis();
        TimeEntryReport[] reports = dao.getTimeEntriesReport(null, new String[0], new boolean[0]);

        long end = System.currentTimeMillis();

        System.out.println("search TimeEntryReport "
                + reports.length
                + " records cost "
                + (end - start)
                / 1000.0
                + " seconds.");
    }
}