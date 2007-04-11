/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;
import com.topcoder.search.builder.filter.Filter;

import java.util.Date;

/**
 * <p>
 * This class provides accuracy tests for <code>TimeEntriesReportBuilder</code> class. It tests:
 * <ol>
 * <li>TimeEntriesReportBuilder() constructor</li>
 * <li>getReport() method</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestTimeEntriesReportBuilderAcc extends BaseTestCase {
    /**
     * <p>
     * This is an instance of TimeEntriesReportBuilder which will be used in test cases.
     * </p>
     */
    private TimeEntriesReportBuilder builder = null;

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
     * Retrieves a TimeEntriesReportBuilder to use in test cases.
     * </p>
     *
     * @throws Exception to JUnit
     * @return a builder to test
     */
    private TimeEntriesReportBuilder getTimeEntriesReportBuilder() throws Exception {
        return new TimeEntriesReportBuilder();
    }

    /**
     * <p>
     * Accuracy test of <code>TimeEntriesReportBuilder()</code> constructor.
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_Ctor() throws Exception {
        builder = new TimeEntriesReportBuilder();
        assertTrue(
                "TimeEntriesReportBuilder should extend InformixReportSearchBuilder.",
                builder instanceof InformixReportSearchBuilder);
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
    public void testTimeEntriesReportBuilder_getReport_Acc_1_NullAll() throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        ReportEntryBean[] reports = builder.getReport(null, null, null);
        this.assertArrayTimeEntryReport(reports, 5);
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with project_id = 1 only</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {true}</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_2_ProjectIdFilterSortIdAscendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        Filter filter = InformixFilter.getFilterProjects(new long[] {1 });
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {true });

        // there should be two records
        this.assertArrayTimeEntryReport(reports, 2);

        long id1 = ((TimeEntryReport) reports[0]).getTimeEntry().getId();
        long id2 = ((TimeEntryReport) reports[1]).getTimeEntry().getId();
        assertTrue("The first record should have time_entry_id = 1", id1 == 1);
        assertTrue("The second record should have time_entry_id = 2", id2 == 2);
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with project_id = 1 only</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_3_ProjectIdFilterSortIdDescendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        Filter filter = InformixFilter.getFilterProjects(new long[] {1 });
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

        // there should be two records
        this.assertArrayTimeEntryReport(reports, 2);

        long id1 = ((TimeEntryReport) reports[0]).getTimeEntry().getId();
        long id2 = ((TimeEntryReport) reports[1]).getTimeEntry().getId();
        assertTrue("The first record should have time_entry_id = 2", id1 == 2);
        assertTrue("The second record should have time_entry_id = 1", id2 == 1);
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with client_id = 1 only</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {true}</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_4_ClientIdFilterSortIdAscendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {true });

        // there should be 4 records
        this.assertArrayTimeEntryReport(reports, 4);

        // assert that the order returned is : 1,2,3,4
        for (int i = 0; i < reports.length; i++) {
            long id = ((TimeEntryReport) reports[i]).getTimeEntry().getId();
            assertEquals("The expected time_entry_id of reports[" + i + "] is " + (i + 1), i + 1, id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
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
    public void testTimeEntriesReportBuilder_getReport_Acc_5_ClientIdFilterSortIdDescendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

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
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with entry_date > "2006-12-27 12:00:00"</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {true}</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_6_EntryDateFilterSortIdAscendingOrder_LowerBound()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();

        // Date lowerBound = new Date(this.getDBSetupDate().getTime() - ONEDAY * 7 / 2);
        Date lowerBound = FORMATTER.parse("2006-12-27 12:00:00");
        Filter filter = InformixFilter.getFilterEntryDate(lowerBound, null);
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {true });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 3);

        // assert that the order returned is : 1,2,3
        for (int i = 0; i < reports.length; i++) {
            long id = ((TimeEntryReport) reports[i]).getTimeEntry().getId();
            assertEquals("The expected time_entry_id of reports[" + i + "] is " + (i + 1), i + 1, id);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with entry_date >= "2006-12-27 12:00:00"</li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's time_entry_id sequence = 2,1,3 (sorted by client_name, project_name
     * first, them by time_entry_time_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_7_EntryDateFilterSortIdDescendingOrder_LowerBound()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        // Date lowerBound = new Date(this.getDBSetupDate().getTime() - ONEDAY * 7 / 2);
        Date lowerBound = FORMATTER.parse("2006-12-27 12:00:00");
        Filter filter = InformixFilter.getFilterEntryDate(lowerBound, null);
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 3);

        // assert that the order returned is : 2,1,3
        int[] sequence = new int[] {2, 1, 3 };
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
    public void testTimeEntriesReportBuilder_getReport_Acc_8_EntryDateFilterSortIdAscendingOrder_UpperBound()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        // long dbSetupDate = this.getDBSetupDate().getTime();
        // Date upperBound = new Date(dbSetupDate - ONEDAY * 5 / 2);
        Date upperBound = FORMATTER.parse("2006-12-28 12:00:00");

        Filter filter = InformixFilter.getFilterEntryDate(null, upperBound);
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

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
     * <li> filter is to capture records with entry_date >= "2006-12-26 12:00:00" and <= "2006-12-29
     * 12:00:00" </li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's time_entry_id sequence = 2,4,3 (sorted by client_name, project_name
     * first, them by time_entry_time_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_9_EntryDateFilterSortIdDescendingOrder_UpperBound()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();
        // long dbSetupDate = this.getDBSetupDate().getTime();
        // Date lowerBound = new Date(dbSetupDate - (ONEDAY * 9) / 2);
        // Date upperBound = new Date(dbSetupDate - (ONEDAY * 3) / 2);
        Date lowerBound = FORMATTER.parse("2006-12-26 12:00:00");
        Date upperBound = FORMATTER.parse("2006-12-29 12:00:00");

        Filter filter = InformixFilter.getFilterEntryDate(lowerBound, upperBound);
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 3);

        // assert that the order returned is : 2,4,3
        int[] sequence = new int[] {2, 4, 3 };
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
     * <li> filter is to capture records with status = 5 </li>
     * <li> sortingColumns = {"time_entry_time_entry_id"}</li>
     * <li> ascendingOrders = {false}</li>
     * <li> expected record's time_entry_id sequence = 5 (sorted by client_name, project_name first,
     * them by time_entry_time_entry_id)</li>
     * </ul>
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntriesReportBuilder_getReport_Acc_10_StatusFilterSortIdDescendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();

        Filter filter = InformixFilter.getFilterStatus(5);
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 1);

        // assert that the order returned is : 5
        int[] sequence = new int[] {5 };
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
    public void testTimeEntriesReportBuilder_getReport_Acc_11_CompanyIdFilterSortIdDescendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();

        Filter filter = InformixFilter.getFilterCompanies(new long[] {101, 104 });
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

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
     * Accuracy test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * <ul>
     * <li> filter is to capture records with usernames = creationUser1, creationUser5 </li>
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
    public void testTimeEntriesReportBuilder_getReport_Acc_12_UsernameFilterSortIdDescendingOrder()
        throws Exception {
        builder = this.getTimeEntriesReportBuilder();

        Filter filter = InformixFilter.getFilterUsernames(new String[] {"creationUser1", "creationUser5" });
        ReportEntryBean[] reports =
                builder.getReport(filter, new String[] {"time_entry_time_entry_id" }, new boolean[] {false });

        // there should be 3 records
        this.assertArrayTimeEntryReport(reports, 2);

        // assert that the order returned is : 1,5
        int[] sequence = new int[] {1, 5 };
        for (int i = 0; i < reports.length; i++) {
            long id = ((TimeEntryReport) reports[i]).getTimeEntry().getId();
            assertEquals(
                    "The expected time_entry_id of reports[" + i + "] is " + sequence[i],
                    sequence[i],
                    id);
        }
    }
}
