/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TaskType;

import com.topcoder.timetracker.project.ProjectWorker;

/**
 * <p>
 * This class provides tests for <code>TimeEntryReport</code> class. It tests:
 * <ol>
 * <li>TimeEntryReport() constructor</li>
 * <li>getter and setter of timeEntry</li>
 * <li>getter and setter of timeStatus</li>
 * <li>getter and setter of taskType</li>
 * <li>getter and setter of projectWorker</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestTimeEntryReport extends BaseTestCase {
    /**
     * <p>
     * Create a new <code>TimeEntry</code> object.
     * </p>
     *
     * @return a new created <code>TimeEntry</code> object.
     *
     * @throws Exception to JUnit
     */
    private TimeEntry createTimeEntry() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setId(1);
        entry.setDescription("entry desc");
        entry.setStatus(this.createTimeStatus());
        entry.setTaskType(this.createTaskType());
        return entry;
    }

    /**
     * <p>
     * Create a new <code>TimeStatus</code> object.
     * </p>
     *
     * @return a new created <code>TimeStatus</code> object.
     *
     * @throws Exception to JUnit
     */
    private TimeStatus createTimeStatus() throws Exception {
        TimeStatus status = new TimeStatus();
        status.setId(101);
        status.setDescription("status desc");
        return status;
    }

    /**
     * <p>
     * Create a new <code>TaskType</code> object.
     * </p>
     *
     * @return a new created <code>TaskType</code> object.
     *
     * @throws Exception to JUnit
     */
    private TaskType createTaskType() throws Exception {
        TaskType type = new TaskType();
        type.setId(1);
        type.setDescription("type desc");
        return type;
    }

    /**
     * <p>
     * Create a new <code>ProjectWorker</code> object.
     * </p>
     *
     * @return a new created <code>ProjectWorker</code> object.
     *
     * @throws Exception to JUnit
     */
    private ProjectWorker createProjectWorker() throws Exception {
        ProjectWorker worker = new ProjectWorker();
        worker.setId(1);
        worker.setPayRate(100.0);
        return worker;
    }

    /**
     * <p>
     * Assert a <code>TimeEntry</code> object to be same as the time it was created.
     * </p>
     *
     * @param entry the entry to test
     * @throws Exception to JUnit
     */
    private void assertExpectedTimeEntry(TimeEntry entry) throws Exception {
        assertNotNull("The entry should not be null.", entry);
        assertEquals("The entry.id should be 1.", 1, entry.getId());
        assertEquals("The entry.description should be 'entry desc'.", "entry desc", entry.getDescription());
        this.assertExpectedTimeStatus(entry.getStatus());
        this.assertExpectedTaskType(entry.getTaskType());
    }

    /**
     * <p>
     * Assert a <code>TimeStatus</code> object to be same as the time it was created.
     * </p>
     *
     * @param status the status to test
     * @throws Exception to JUnit
     */
    private void assertExpectedTimeStatus(TimeStatus status) throws Exception {
        assertNotNull("The status should not be null.", status);
        assertEquals("The status.id should be 101.", 101, status.getId());
        assertEquals(
                "The status.description should be 'status desc'.",
                "status desc",
                status.getDescription());
    }

    /**
     * <p>
     * Assert a <code>TaskType</code> object to be same as the time it was created.
     * </p>
     *
     * @param type the type to test
     * @throws Exception to JUnit
     */
    private void assertExpectedTaskType(TaskType type) throws Exception {
        assertNotNull("The type should not be null.", type);
        assertEquals("The type.id should be 1.", 1, type.getId());
        assertEquals("The type.description should be 'type desc'.", "type desc", type.getDescription());
    }

    /**
     * <p>
     * Assert a <code>ProjectWorker</code> object to be same as the time it was created.
     * </p>
     *
     * @param worker the worker to test
     * @throws Exception to JUnit
     */
    private void assertExpectedProjectWorker(ProjectWorker worker) throws Exception {
        assertNotNull("The worker should not be null.", worker);
        assertEquals("The worker.id should be 1.", 1, worker.getId());
        assertEquals("The worker.pay_rate should be " + 100.0, 100.0, worker.getPayRate(), 1e-5);
    }

    /**
     * <p>
     * Test of <code>TimeEntryReport()</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntryReport_Ctor() throws Exception {
        TimeEntryReport report = new TimeEntryReport();
        assertTrue("TimeEntryReport should extend ReportEntryBean", report instanceof ReportEntryBean);
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseEntry.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertTimeEntry_getter_setter() throws Exception {
        TimeEntryReport report = new TimeEntryReport();

        TimeEntry entry = this.createTimeEntry();
        report.setTimeEntry(entry);
        TimeEntry retrievedentry = report.getTimeEntry();

        assertEquals("The entry object should be stored properly.", entry, retrievedentry);
        this.assertExpectedTimeEntry(retrievedentry);
    }

    /**
     * <p>
     * Test of <code>getTimeEntry()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry() throws Exception {
        this.assertTimeEntry_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setTimeEntry()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetTimeEntry() throws Exception {
        this.assertTimeEntry_getter_setter();
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseStatus.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertTimeStatus_getter_setter() throws Exception {
        TimeEntryReport report = new TimeEntryReport();

        TimeStatus status = this.createTimeStatus();
        report.setTimeStatus(status);
        TimeStatus retrievedstatus = report.getTimeStatus();

        assertEquals("The entry object should be stored properly.", status, retrievedstatus);
        this.assertExpectedTimeStatus(retrievedstatus);
    }

    /**
     * <p>
     * Test of <code>getTimeStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus() throws Exception {
        this.assertTimeStatus_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setTimeStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetTimeStatus() throws Exception {
        this.assertTimeStatus_getter_setter();
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseType.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertTaskType_getter_setter() throws Exception {
        TimeEntryReport report = new TimeEntryReport();

        TaskType type = this.createTaskType();
        report.setTaskType(type);
        TaskType retrievedtype = report.getTaskType();

        assertEquals("The entry object should be stored properly.", type, retrievedtype);
        this.assertExpectedTaskType(retrievedtype);
    }

    /**
     * <p>
     * Test of <code>getTaskType()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType() throws Exception {
        this.assertTaskType_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setTaskType()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetTaskType() throws Exception {
        this.assertTaskType_getter_setter();
    }

    /**
     * <p>
     * Validate the getter/setter methods of expenseType.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertProjectWorker_getter_setter() throws Exception {
        TimeEntryReport report = new TimeEntryReport();

        ProjectWorker worker = this.createProjectWorker();
        report.setProjectWorker(worker);
        ProjectWorker retrievedworker = report.getProjectWorker();

        assertEquals("The entry object should be stored properly.", worker, retrievedworker);
        this.assertExpectedProjectWorker(retrievedworker);
    }

    /**
     * <p>
     * Test of <code>getProjectWorker()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectWorker() throws Exception {
        this.assertProjectWorker_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setTaskType()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetProjectWorker() throws Exception {
        this.assertProjectWorker_getter_setter();
    }
}
