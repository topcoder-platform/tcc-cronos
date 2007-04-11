/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.report.informix.TimeEntryReport;

import junit.framework.TestCase;

/**
 * The test of TimeEntryReport.
 *
 * @author brain_cn
 * @version 1.0
 */
public class TimeEntryReportAccuracyTests extends TestCase {
    /** The tset TimeEntryReport for testing. */
    private TimeEntryReport instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        instance = new TimeEntryReport();
    }

    /**
     * Test method for 'TimeEntryReport()'
     */
    public void testTimeEntryReport() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getTimeEntry()'
     */
    public void testGetTimeEntry() {
        TimeEntry timeEntry = new TimeEntry();
        instance.setTimeEntry(timeEntry);
        assertEquals("incorrect timeEntry", timeEntry, instance.getTimeEntry());

    }

    /**
     * Test method for 'setTimeEntry(TimeEntry)'
     */
    public void testSetTimeEntry() {
        TimeEntry timeEntry = new TimeEntry();
        instance.setTimeEntry(timeEntry);
        assertEquals("incorrect timeEntry", timeEntry, instance.getTimeEntry());

        TimeEntry timeEntry1 = new TimeEntry();
        instance.setTimeEntry(timeEntry1);
        assertEquals("incorrect timeEntry", timeEntry1, instance.getTimeEntry());
    }

    /**
     * Test method for 'getTimeStatus()'
     */
    public void testGetTimeStatus() {
        TimeStatus timeStatus = new TimeStatus();
        instance.setTimeStatus(timeStatus);
        assertEquals("incorrect timeStatus", timeStatus, instance.getTimeStatus());
    }

    /**
     * Test method for 'setTimeStatus(TimeStatus)'
     */
    public void testSetTimeStatus() {
        TimeStatus timeStatus = new TimeStatus();
        instance.setTimeStatus(timeStatus);
        assertEquals("incorrect timeStatus", timeStatus, instance.getTimeStatus());

        TimeStatus timeStatus1 = new TimeStatus();
        instance.setTimeStatus(timeStatus1);
        assertEquals("incorrect timeStatus", timeStatus1, instance.getTimeStatus());
    }

    /**
     * Test method for 'getTaskType()'
     */
    public void testGetTaskType() {
        TaskType taskType = new TaskType();
        instance.setTaskType(taskType);
        assertEquals("incorrect taskType", taskType, instance.getTaskType());
    }

    /**
     * Test method for 'setTaskType(TaskType)'
     */
    public void testSetTaskType() {
        TaskType taskType = new TaskType();
        instance.setTaskType(taskType);
        assertEquals("incorrect taskType", taskType, instance.getTaskType());

        TaskType taskType1 = new TaskType();
        instance.setTaskType(taskType1);
        assertEquals("incorrect taskType", taskType1, instance.getTaskType());
    }

    /**
     * Test method for 'getProjectWorker()'
     */
    public void testGetProjectWorker() {
        ProjectWorker projectWorker = new ProjectWorker();
        instance.setProjectWorker(projectWorker);
        assertEquals("incorrect projectWorker", projectWorker, instance.getProjectWorker());
    }

    /**
     * Test method for 'setProjectWorker(ProjectWorker)'
     */
    public void testSetProjectWorker() {
        ProjectWorker projectWorker = new ProjectWorker();
        instance.setProjectWorker(projectWorker);
        assertEquals("incorrect projectWorker", projectWorker, instance.getProjectWorker());

        ProjectWorker projectWorker1 = new ProjectWorker();
        instance.setProjectWorker(projectWorker1);
        assertEquals("incorrect projectWorker", projectWorker1, instance.getProjectWorker());
    }

}
