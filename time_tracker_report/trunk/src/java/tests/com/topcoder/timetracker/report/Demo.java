/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.report.informix.InformixFilter;

import com.topcoder.search.builder.filter.Filter;
import java.util.Date;

/**
 * <p>
 * This class provides the demo of usage for Time Tracker Report component.
 *
 * @author rinoavd
 * @version 3.1
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.setupDatabase("/Time_Tracker_Report_v_3_1_demo.sql");
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
     * The Demo provided in Component Specification (part 1).
     * </p>
     *
     * <p>
     * All entries are to be retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo_1() throws Exception {
        // creates a ReportDAOFactory
        ReportDAOFactory factory = new ReportDAOFactory();

        // retrieves the correct DAO: in this case InformixReportDAO
        ReportDAO informixReportDAO = factory.getReportDAO(ReportDAOFactory.INFORMIX);

        // retrieves all reports of Expense entries
        ExpenseEntryReport[] expenseEntryReportsNotFiltered =
                informixReportDAO.getExpenseEntriesReport(null, null, null);

        // validate
        assertEquals("Number of reports should be 3.", 3, expenseEntryReportsNotFiltered.length);
        for (int i = 0; i < expenseEntryReportsNotFiltered.length; i++) {
            long companyId = expenseEntryReportsNotFiltered[i].getExpenseEntry().getCompanyId();
            long id = 0;
            if (companyId == 103) {
                id = 3;
            } else {
                id = (companyId == 7) ? 1 : 2;
            }
            this.assertExpenseEntry(expenseEntryReportsNotFiltered[i].getExpenseEntry(), id);
        }
    }

    /**
     * <p>
     * The Demo provided in Component Specification (part 2).
     * </p>
     *
     * <p>
     * Retrieves reports of Expense entries filtered by company IDs
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo_2() throws Exception {

        // retrieves DAO
        ReportDAOFactory factory = new ReportDAOFactory();
        ReportDAO informixReportDAO = factory.getReportDAO(ReportDAOFactory.INFORMIX);

        // creates the filter
        Filter filterByCompanies = InformixFilter.getFilterCompanies(new long[] {7, 42 });

        // retrieves the reports filtered by filterByCompanies
        ExpenseEntryReport[] expenseEntriesReportFilteredByCompanies =
                informixReportDAO.getExpenseEntriesReport(filterByCompanies, null, null);

        // validate
        assertEquals("Number of reports should be 2.", 2, expenseEntriesReportFilteredByCompanies.length);
        long companyId0 = expenseEntriesReportFilteredByCompanies[0].getExpenseEntry().getCompanyId();
        long companyId1 = expenseEntriesReportFilteredByCompanies[1].getExpenseEntry().getCompanyId();
        assertTrue("The company Ids should be 7 and 42.", (companyId0 == 7 && companyId1 == 42)
                || (companyId0 == 42 && companyId1 == 7));

        long expectedId0 = (companyId0 == 7) ? 1 : 2;
        long expectedId1 = (companyId1 == 7) ? 1 : 2;
        this.assertExpenseEntry(expenseEntriesReportFilteredByCompanies[0].getExpenseEntry(), expectedId0);
        this.assertExpenseEntry(expenseEntriesReportFilteredByCompanies[1].getExpenseEntry(), expectedId1);
    }

    /**
     * <p>
     * The Demo provided in Component Specification (part 3).
     * </p>
     *
     * <p>
     * Gets the reports filtered by company IDs, sorted by expense_entry.amount in ascending order
     * and expense_entry.mileage in descending order.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo_3() throws Exception {
        // retrieves DAO
        ReportDAOFactory factory = new ReportDAOFactory();
        ReportDAO informixReportDAO = factory.getReportDAO(ReportDAOFactory.INFORMIX);

        // creates filter
        Filter filterByCompanies = InformixFilter.getFilterCompanies(new long[] {7, 42 });

        // retrieves the reports
        ExpenseEntryReport[] expenseEntriesReportSortedByAmountAndMileage =
                informixReportDAO.getExpenseEntriesReport(filterByCompanies, new String[] {
                    "expense_entry_amount", "expense_entry_mileage" }, new boolean[] {true, false });
        // validate
        assertEquals("Number of reports should be 2.", 2, expenseEntriesReportSortedByAmountAndMileage.length);
        long id0 = expenseEntriesReportSortedByAmountAndMileage[0].getExpenseEntry().getCompanyId();
        long id1 = expenseEntriesReportSortedByAmountAndMileage[1].getExpenseEntry().getCompanyId();

        assertTrue("The company Ids should be 42 and 7 (in that order).", (id0 == 42 && id1 == 7));
        this.assertExpenseEntry(expenseEntriesReportSortedByAmountAndMileage[0].getExpenseEntry(), 2);
        this.assertExpenseEntry(expenseEntriesReportSortedByAmountAndMileage[1].getExpenseEntry(), 1);
    }

    /**
     * <p>
     * Validate the ExpenseEntry object using the provided <code>id</code>. From the
     * <code>id</code> we can find out how the entry should look like in the database (sample data
     * for demo:Time_Tracker_Report_v_3_1_demo.sql.)
     * </p>
     *
     * @param entry the ExpenseEntry object to be checked.
     * @param id expense_entry_id of the expected record in the database.
     *
     * @throws Exception to JUnit
     */
    protected void assertExpenseEntry(ExpenseEntry entry, long id) throws Exception {
        // id can be from 1 to 3 only.
        if (id < 1 || id > 3) {
            return;
        }

        int expectedCompanyId = 0;
        double expectedAmount = 0.0;

        if (id == 1) {
            expectedCompanyId = 7;
            expectedAmount = 5.0;
        }
        if (id == 2) {
            expectedCompanyId = 42;
            expectedAmount = 1.0;
        }
        if (id == 3) {
            expectedCompanyId = 103;
            expectedAmount = 3.0;
        }

        assertEquals("entry.id should be " + id, id, entry.getId());
        assertEquals("entry.companyId should be " + expectedCompanyId, expectedCompanyId, entry.getCompanyId());
        assertEquals("entry.amount should be " + expectedAmount, expectedAmount, entry.getAmount().doubleValue(),
                1e-5);

        assertEquals("entry.description should be 'expenseentryDesc" + id + "'.", "expenseentryDesc" + id, entry
                .getDescription());
        assertEquals("entry.creationUser should be 'creationUser1'.", "creationUser1", entry.getCreationUser());
        assertEquals("entry.modificationUser should be 'modificationUser1'.", "modificationUser1", entry
                .getModificationUser());

        assertDateDiff10Seconds("entry.entry_date is not correct.", new Date(FORMATTER
                .parse("2006-12-31 00:00:00").getTime()
                - id * ONEDAY), entry.getDate());
        Date setupDate = this.getDBSetupDate();
        assertDateDiff10Seconds("entry.creationDate is not correct.", new Date(setupDate.getTime() - id * ONEDAY),
                entry.getCreationDate());
        assertDateDiff10Seconds("entry.modificationDate is not correct.", new Date(setupDate.getTime()
                - id * ONEDAY), entry.getModificationDate());

        ExpenseStatus status = entry.getStatus();
        assertNotNull("entry.ExpenseStatus should not be null", status);
        assertEquals("entry.ExpenseStatus.id should be " + id, id, status.getId());
        this.assertExpenseStatus(status, id);

        ExpenseType type = entry.getExpenseType();
        assertNotNull("entry.ExpenseType should not be null", type);
        assertEquals("entry.ExpenseTpe.id should be " + id, id, type.getId());
        this.assertExpenseType(type, id);
    }
}
