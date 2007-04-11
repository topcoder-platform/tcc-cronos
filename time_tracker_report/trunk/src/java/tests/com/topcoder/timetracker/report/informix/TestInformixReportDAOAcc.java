/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;

import com.topcoder.timetracker.report.ReportDAO;
import com.topcoder.search.builder.filter.Filter;
import java.util.Date;

/**
 * <p>
 * This class provides accuracy tests for <code>InformixReportDAO</code> class. It tests:
 * <ol>
 * <li> InformixReportDAO() constructor</li>
 * <li> InformixReportDAO(String) constructor</li>
 * <li> getExpenseEntriesReport(Filter ,String[] ,boolean[]) method</li>
 * <li> getFixedBillingEntriesReport(Filter ,String[] ,boolean[]) method</li>
 * <li> getReport(String ,String[] ,boolean[]) method</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestInformixReportDAOAcc extends BaseTestCase {
    /**
     * <p>
     * This is an instance of InformixReportDAO which will be used in test cases.
     * </p>
     */
    private InformixReportDAO dao = null;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.setupDatabase(DATA_FILE_LOCATION);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.clearDatabase();
        super.tearDown();
    }

    /**
     * <p>
     * Retrieves a FixedBillingEntriesReportBuilder to use in test cases.
     * </p>
     *
     * @return the DAO to test
     * @throws Exception to JUnit
     */
    private InformixReportDAO getInformixReportDAO() throws Exception {
        return new InformixReportDAO();
    }

    /**
     * <p>
     * Accuracy test of <code>InformixReportDAO()</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_Ctor_1_Acc() throws Exception {
        dao = this.getInformixReportDAO();
        assertTrue("InformixReportDAO should implement ReportDAO", dao instanceof ReportDAO);
    }

    /**
     * <p>
     * Accuracy test of <code>InformixReportDAO(String)</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_Ctor_2_Acc() throws Exception {
        dao = new InformixReportDAO("com.topcoder.timetracker.report.InformixReportDAO");
        assertTrue("InformixReportDAO should implement ReportDAO", dao instanceof ReportDAO);
    }

    /**
     * <p>
     * Accuracy test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter = null</li>
     * <li> sortingColumns = null</li>
     * <li>ascendingOrders = null</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Acc_1_NullAll() throws Exception {
        dao = this.getInformixReportDAO();
        FixedBillingEntryReport[] reports = dao.getFixedBillingEntriesReport(null, null, null);
        this.assertArrayFixedBillingEntryReport(reports, 5);
    }

    /**
     * <p>
     * Accuracy test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with client_id = 1 only</li>
     * <li> sortingColumns = {"fix_bill_entry_fix_bill_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's fix_bill_entry_id sequence = 2,1,4,3 (sorted by client_name,
     * project_name first, them by fix_bill_entry_fix_bill_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Acc_2_ClientIdFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        FixedBillingEntryReport[] reports =
                dao.getFixedBillingEntriesReport(
                        filter,
                        new String[] {"fix_bill_entry_fix_bill_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayFixedBillingEntryReport(reports, 4);

        // assert that the order returned is : 2,1,4,3
        int[] sequence = new int[] {2, 1, 4, 3 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((FixedBillingEntryReport) reports[i]).getFixedBillingEntry().getId();
            assertEquals(
                    "The expected fix_bill_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with company IDs = 101,104 </li>
     * <li> sortingColumns = {"fix_bill_entry_fix_bill_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's fix_bill_entry_id sequence = 2,4 (sorted by client_name, project_name
     * first, them by fix_bill_entry_fix_bill_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Acc_3_CompanyIdFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();

        Filter filter = InformixFilter.getFilterCompanies(new long[] {101, 104 });
        FixedBillingEntryReport[] reports =
                dao.getFixedBillingEntriesReport(
                        filter,
                        new String[] {"fix_bill_entry_fix_bill_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayFixedBillingEntryReport(reports, 2);

        // assert that the order returned is : 1,4
        int[] sequence = new int[] {1, 4 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((FixedBillingEntryReport) reports[i]).getFixedBillingEntry().getId();
            assertEquals(
                    "The expected fix_bill_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with entry_date <= "2006-12-28 12:00:00"</li>
     * <li> sortingColumns = {"fix_bill_entry_fix_bill_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's fix_bill_entry_id sequence = 4,3,5 (sorted by client_name,
     * project_name first, them by fix_bill_entry_fix_bill_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Acc_4_EntryDateFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();

        Date upperBound = FORMATTER.parse("2006-12-28 12:00:00");
        Filter filter = InformixFilter.getFilterEntryDate(null, upperBound);

        FixedBillingEntryReport[] reports =
                dao.getFixedBillingEntriesReport(
                        filter,
                        new String[] {"fix_bill_entry_fix_bill_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayFixedBillingEntryReport(reports, 3);

        // assert that the order returned is : 4,3,5
        int[] sequence = new int[] {4, 3, 5 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((FixedBillingEntryReport) reports[i]).getFixedBillingEntry().getId();
            assertEquals(
                    "The expected fix_bill_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getExpenseEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter = null</li>
     * <li> sortingColumns = null</li>
     * <li>ascendingOrders = null</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getExpenseEntriesReport_Acc_1_NullAll() throws Exception {
        dao = this.getInformixReportDAO();
        ExpenseEntryReport[] reports = dao.getExpenseEntriesReport(null, null, null);
        this.assertArrayExpenseEntryReport(reports, 5);
    }

    /**
     * <p>
     * Accuracy test of <code>getExpenseEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with client_id = 1 only</li>
     * <li> sortingColumns = {"expense_entry_expense_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's expense_entry_id sequence = 2,1,4,3 (sorted by client_name,
     * project_name first, them by expense_entry_expense_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getExpenseEntriesReport_Acc_2_ClientIdFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        ExpenseEntryReport[] reports =
                dao.getExpenseEntriesReport(
                        filter,
                        new String[] {"expense_entry_expense_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayExpenseEntryReport(reports, 4);

        // assert that the order returned is : 2,1,4,3
        int[] sequence = new int[] {2, 1, 4, 3 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((ExpenseEntryReport) reports[i]).getExpenseEntry().getId();
            assertEquals(
                    "The expected expense_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getExpenseEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with company IDs = 101,104 </li>
     * <li> sortingColumns = {"expense_entry_expense_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's expense_entry_id sequence = 2,4 (sorted by client_name, project_name
     * first, them by expense_entry_expense_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getExpenseEntriesReport_Acc_3_CompanyIdFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();

        Filter filter = InformixFilter.getFilterCompanies(new long[] {101, 104 });
        ExpenseEntryReport[] reports =
                dao.getExpenseEntriesReport(
                        filter,
                        new String[] {"expense_entry_expense_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayExpenseEntryReport(reports, 2);

        // assert that the order returned is : 1,4
        int[] sequence = new int[] {1, 4 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((ExpenseEntryReport) reports[i]).getExpenseEntry().getId();
            assertEquals(
                    "The expected expense_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getExpenseEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with entry_date <= "2006-12-28 12:00:00"</li>
     * <li> sortingColumns = {"expense_entry_expense_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's expense_entry_id sequence = 4,3,5 (sorted by client_name, project_name
     * first, them by expense_entry_expense_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getExpenseEntriesReport_Acc_4_EntryDateFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();

        Date upperBound = FORMATTER.parse("2006-12-28 12:00:00");
        Filter filter = InformixFilter.getFilterEntryDate(null, upperBound);

        ExpenseEntryReport[] reports =
                dao.getExpenseEntriesReport(
                        filter,
                        new String[] {"expense_entry_expense_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayExpenseEntryReport(reports, 3);

        // assert that the order returned is : 4,3,5
        int[] sequence = new int[] {4, 3, 5 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((ExpenseEntryReport) reports[i]).getExpenseEntry().getId();
            assertEquals(
                    "The expected expense_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getTimeEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter = null</li>
     * <li> sortingColumns = null</li>
     * <li>ascendingOrders = null</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getTimeEntriesReport_Acc_1_NullAll() throws Exception {
        dao = this.getInformixReportDAO();
        TimeEntryReport[] reports = dao.getTimeEntriesReport(null, null, null);
        this.assertArrayTimeEntryReport(reports, 5);
    }

    /**
     * <p>
     * Accuracy test of <code>getTimeEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with client_id = 1 only</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's time_entry_id sequence = 2,1,4,3 (sorted by client_name, project_name
     * first, them by time_entry_time_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getTimeEntriesReport_Acc_2_ClientIdFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        TimeEntryReport[] reports =
                dao.getTimeEntriesReport(
                        filter,
                        new String[] {"time_entry_time_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 4);

        // assert that the order returned is : 2,1,4,3
        int[] sequence = new int[] {2, 1, 4, 3 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((TimeEntryReport) reports[i]).getTimeEntry().getId();
            assertEquals(
                    "The expected time_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getTimeEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with company IDs = 101,104 </li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's time_entry_id sequence = 2,4 (sorted by client_name, project_name
     * first, them by time_entry_time_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getTimeEntriesReport_Acc_3_CompanyIdFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();

        Filter filter = InformixFilter.getFilterCompanies(new long[] {101, 104 });
        TimeEntryReport[] reports =
                dao.getTimeEntriesReport(
                        filter,
                        new String[] {"time_entry_time_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 2);

        // assert that the order returned is : 1,4
        int[] sequence = new int[] {1, 4 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((TimeEntryReport) reports[i]).getTimeEntry().getId();
            assertEquals(
                    "The expected time_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getTimeEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with entry_date <= "2006-12-28 12:00:00"</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's time_entry_id sequence = 4,3,5 (sorted by client_name, project_name
     * first, them by time_entry_time_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getTimeEntriesReport_Acc_4_EntryDateFilterSortIdDescendingOrder()
        throws Exception {
        dao = this.getInformixReportDAO();

        Date upperBound = FORMATTER.parse("2006-12-28 12:00:00");
        Filter filter = InformixFilter.getFilterEntryDate(null, upperBound);

        TimeEntryReport[] reports =
                dao.getTimeEntriesReport(
                        filter,
                        new String[] {"time_entry_time_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 3);

        // assert that the order returned is : 4,3,5
        int[] sequence = new int[] {4, 3, 5 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((TimeEntryReport) reports[i]).getTimeEntry().getId();
            assertEquals(
                    "The expected time_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter = null</li>
     * <li> sortingColumns = null</li>
     * <li>ascendingOrders = null</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Acc_1_NullAll() throws Exception {
        dao = this.getInformixReportDAO();
        ReportEntryBean[] reports = dao.getReport(InformixReportDAO.FIXED_BILLING_ENTRIES, null, null, null);
        this.assertArrayFixedBillingEntryReport(reports, 5);
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with client_id = 1 only</li>
     * <li> sortingColumns = {"fix_bill_entry_fix_bill_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's fix_bill_entry_id sequence = 2,1,4,3 (sorted by client_name,
     * project_name first, them by fix_bill_entry_fix_bill_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Acc_2_ClientIdFilterSortIdDescendingOrder() throws Exception {
        dao = this.getInformixReportDAO();
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        ReportEntryBean[] reports =
                dao.getReport(
                        InformixReportDAO.FIXED_BILLING_ENTRIES,
                        filter,
                        new String[] {"fix_bill_entry_fix_bill_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayFixedBillingEntryReport(reports, 4);

        // assert that the order returned is : 2,1,4,3
        int[] sequence = new int[] {2, 1, 4, 3 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((FixedBillingEntryReport) reports[i]).getFixedBillingEntry().getId();
            assertEquals(
                    "The expected fix_bill_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with company IDs = 101,104 </li>
     * <li> sortingColumns = {"fix_bill_entry_fix_bill_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's fix_bill_entry_id sequence = 2,4 (sorted by client_name, project_name
     * first, them by fix_bill_entry_fix_bill_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Acc_3_CompanyIdFilterSortIdDescendingOrder() throws Exception {
        dao = this.getInformixReportDAO();

        Filter filter = InformixFilter.getFilterCompanies(new long[] {101, 104 });
        ReportEntryBean[] reports =
                dao.getReport(
                        InformixReportDAO.FIXED_BILLING_ENTRIES,
                        filter,
                        new String[] {"fix_bill_entry_fix_bill_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayFixedBillingEntryReport(reports, 2);

        // assert that the order returned is : 1,4
        int[] sequence = new int[] {1, 4 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((FixedBillingEntryReport) reports[i]).getFixedBillingEntry().getId();
            assertEquals(
                    "The expected fix_bill_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with entry_date <= "2006-12-28 12:00:00"</li>
     * <li> sortingColumns = {"fix_bill_entry_fix_bill_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's fix_bill_entry_id sequence = 4,3,5 (sorted by client_name,
     * project_name first, them by fix_bill_entry_fix_bill_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Acc_4_EntryDateFilterSortIdDescendingOrder() throws Exception {
        dao = this.getInformixReportDAO();

        Date upperBound = FORMATTER.parse("2006-12-28 12:00:00");
        Filter filter = InformixFilter.getFilterEntryDate(null, upperBound);

        ReportEntryBean[] reports =
                dao.getReport(
                        InformixReportDAO.FIXED_BILLING_ENTRIES,
                        filter,
                        new String[] {"fix_bill_entry_fix_bill_entry_id" },
                        new boolean[] {false });

        // there should be 3 records
        this.assertArrayFixedBillingEntryReport(reports, 3);

        // assert that the order returned is : 4,3,5
        int[] sequence = new int[] {4, 3, 5 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((FixedBillingEntryReport) reports[i]).getFixedBillingEntry().getId();
            assertEquals(
                    "The expected fix_bill_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * type = unknown. Empty result is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Acc_5_UnknownType() throws Exception {
        dao = this.getInformixReportDAO();
        ReportEntryBean[] reports = dao.getReport("unknown", null, null, null);

        assertNotNull("The result should not be null.", reports);
        assertEquals("There should be no report.", 0, reports.length);
    }
}
