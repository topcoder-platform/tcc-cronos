/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;

/**
 * <p>
 * This class provides tests for <code>ExpenseEntryReport</code> class. It tests:
 * <ol>
 * <li>ExpenseEntryReport() constructor</li>
 * <li>getter and setter of expenseEntry</li>
 * <li>getter and setter of expenseStatus</li>
 * <li>getter and setter of expenseType</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestExpenseEntryReport extends BaseTestCase {
    /**
     * <p>
     * Create a new <code>ExpenseEntry</code> object.
     * </p>
     *
     * @return a new created <code>ExpenseEntry</code> object.
     *
     * @throws Exception to JUnit
     */
    private ExpenseEntry createExpenseEntry() throws Exception {
        ExpenseEntry entry = new ExpenseEntry();
        entry.setId(1);
        entry.setDescription("entry desc");
        entry.setStatus(this.createExpenseStatus());
        entry.setExpenseType(this.createExpenseType());
        return entry;
    }

    /**
     * <p>
     * Create a new <code>ExpenseStatus</code> object.
     * </p>
     *
     * @return a new created <code>ExpenseStatus</code> object.
     *
     * @throws Exception to JUnit
     */
    private ExpenseStatus createExpenseStatus() throws Exception {
        ExpenseStatus status = new ExpenseStatus();
        status.setId(101);
        status.setDescription("status desc");
        return status;
    }

    /**
     * <p>
     * Create a new <code>ExpenseType</code> object.
     * </p>
     *
     * @return a new created <code>ExpenseType</code> object.
     *
     * @throws Exception to JUnit
     */
    private ExpenseType createExpenseType() throws Exception {
        ExpenseType type = new ExpenseType();
        type.setId(1);
        type.setDescription("type desc");
        return type;
    }

    /**
     * <p>
     * Assert a <code>ExpenseEntry</code> object to be same as the time it was created.
     * </p>
     *
     * @param entry the entry to test
     * @throws Exception to JUnit
     */
    private void assertExpectedExpenseEntry(ExpenseEntry entry) throws Exception {
        assertNotNull("The entry should not be null.", entry);
        assertEquals("The entry.id should be 1.", 1, entry.getId());
        assertEquals("The entry.description should be 'entry desc'.", "entry desc", entry.getDescription());
        this.assertExpectedExpenseStatus(entry.getStatus());
        this.assertExpectedExpenseType(entry.getExpenseType());
    }

    /**
     * <p>
     * Assert a <code>ExpenseStatus</code> object to be same as the time it was created.
     * </p>
     *
     * @param status the status to test
     * @throws Exception to JUnit
     */
    private void assertExpectedExpenseStatus(ExpenseStatus status) throws Exception {
        assertNotNull("The status should not be null.", status);
        assertEquals("The status.id should be 101.", 101, status.getId());
        assertEquals(
                "The status.description should be 'status desc'.",
                "status desc",
                status.getDescription());
    }

    /**
     * <p>
     * Assert a <code>ExpenseType</code> object to be same as the time it was created.
     * </p>
     *
     * @param type the type to test
     * @throws Exception to JUnit
     */
    private void assertExpectedExpenseType(ExpenseType type) throws Exception {
        assertNotNull("The type should not be null.", type);
        assertEquals("The type.id should be 101.", 1, type.getId());
        assertEquals("The type.description should be 'type desc'.", "type desc", type.getDescription());
    }

    /**
     * <p>
     * Test of <code>ExpenseEntryReport()</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExpenseEntryReport_Ctor() throws Exception {
        ExpenseEntryReport report = new ExpenseEntryReport();
        assertTrue("ExpenseEntryReport should extend ReportEntryBean", report instanceof ReportEntryBean);
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseEntry.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertExpenseEntry_getter_setter() throws Exception {
        ExpenseEntryReport report = new ExpenseEntryReport();

        ExpenseEntry entry = this.createExpenseEntry();
        report.setExpenseEntry(entry);
        ExpenseEntry retrievedentry = report.getExpenseEntry();

        assertEquals("The entry object should be stored properly.", entry, retrievedentry);
        this.assertExpectedExpenseEntry(retrievedentry);
    }

    /**
     * <p>
     * Test of <code>getExpenseEntry()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetExpenseEntry() throws Exception {
        this.assertExpenseEntry_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setExpenseEntry()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetExpenseEntry() throws Exception {
        this.assertExpenseEntry_getter_setter();
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseStatus.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertExpenseStatus_getter_setter() throws Exception {
        ExpenseEntryReport report = new ExpenseEntryReport();

        ExpenseStatus status = this.createExpenseStatus();
        report.setExpenseStatus(status);
        ExpenseStatus retrievedstatus = report.getExpenseStatus();

        assertEquals("The entry object should be stored properly.", status, retrievedstatus);
        this.assertExpectedExpenseStatus(retrievedstatus);
    }

    /**
     * <p>
     * Test of <code>getExpenseStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetExpenseStatus() throws Exception {
        this.assertExpenseStatus_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setExpenseStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetExpenseStatus() throws Exception {
        this.assertExpenseStatus_getter_setter();
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseType.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertExpenseType_getter_setter() throws Exception {
        ExpenseEntryReport report = new ExpenseEntryReport();

        ExpenseType type = this.createExpenseType();
        report.setExpenseType(type);
        ExpenseType retrievedtype = report.getExpenseType();

        assertEquals("The entry object should be stored properly.", type, retrievedtype);
        this.assertExpectedExpenseType(retrievedtype);
    }

    /**
     * <p>
     * Test of <code>getExpenseType()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetExpenseType() throws Exception {
        this.assertExpenseType_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setExpenseType()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetExpenseType() throws Exception {
        this.assertExpenseType_getter_setter();
    }
}
