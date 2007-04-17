/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.util.Date;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate;
import com.topcoder.timetracker.entry.time.delegate.TimeEntryManagerDelegate;
import com.topcoder.timetracker.entry.time.delegate.TimeStatusManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerSessionBean;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerSessionBean;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerSessionBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * The unit test class is used for component demonstration.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTests extends TestCase {
    /**
     * <p>
     * Represents the pending status id.
     * </p>
     */
    private long pendingStatusId;

    /**
     * <p>
     * Represents the approved status id.
     * </p>
     */
    private long approvedStatusId;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(new TaskTypeManagerSessionBean(), new TimeEntryManagerSessionBean(),
            new TimeStatusManagerSessionBean());

        TaskType taskType = TestHelper.createTestingTaskType(null);
        taskType.setCompanyId(1);
        taskType.setDescription("Topcoder Design");
        ManagerFactory.getTaskTypeManager().createTaskType(taskType);

        TimeStatus pendingStatus = TestHelper.createTestingTimeStatus(null);
        pendingStatus.setDescription("Pending");
        ManagerFactory.getTimeStatusManager().createTimeStatus(pendingStatus);
        pendingStatusId = pendingStatus.getId();

        TimeStatus approvedStatus = TestHelper.createTestingTimeStatus(null);
        approvedStatus.setDescription("Approved");
        ManagerFactory.getTimeStatusManager().createTimeStatus(approvedStatus);
        approvedStatusId = approvedStatus.getId();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DemoTests.class);
    }

    /**
     * <p>
     * This test case demonstrates the usage of working with the time entry component.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemo() throws Exception {
        // Create a TimeEntryManager using the DB DAOs, and rejectReasonManager
        TimeEntryManager entryManager = new TimeEntryManagerDelegate(TimeEntryManagerDelegate.class.getName());
        TaskTypeManager taskManager = new TaskTypeManagerDelegate(TaskTypeManagerDelegate.class.getName());
        TimeStatusManager statusManager = new TimeStatusManagerDelegate(TimeStatusManagerDelegate.class.getName());

        // Creating a new Time Entry with a Task Type of "Topcoder Design" and a Status of "Pending"
        TimeEntry newEntry = new TimeEntry();
        newEntry.setCompanyId(1);
        newEntry.setDescription("Pluggable Persistence 3.0");
        newEntry.setDate(new Date());
        newEntry.setBillable(false);
        newEntry.setHours(10);
        newEntry.setCreationUser("topcoder");
        newEntry.setModificationUser("topcoder");

        // Retrieve a task type with a description of "Topcoder Design"
        TaskTypeFilterFactory taskFilterFactory = taskManager.getTaskTypeFilterFactory();

        Filter taskCriterion = new AndFilter(taskFilterFactory.createCompanyIdFilter(1),
            taskFilterFactory.createDescriptionFilter("Design", StringMatchType.SUBSTRING));

        // Perform the search
        TaskType tcDesignType = taskManager.searchTaskTypes(taskCriterion)[0];

        // Assign the type to the entry
        newEntry.setTaskType(tcDesignType);

        // Retrieve a status with a description of "Pending"
        TimeStatus pendingStatus = statusManager.getTimeStatus(pendingStatusId);

        // Assign the status to the entry
        newEntry.setStatus(pendingStatus);

        // Register the entry with the manager, with auditing.
        entryManager.createTimeEntry(newEntry, true);

        // Updating the Entry Details

        // Retrieve an entry from the manager
        TimeEntry changingEntry = entryManager.getTimeEntry(newEntry.getId());

        // Update the entry details.
        changingEntry.setDescription("Pluggable Persistence 3.1");
        TimeStatus approvedStatus = statusManager.getTimeStatus(approvedStatusId);
        changingEntry.setStatus(approvedStatus);

        // Update the entry in the manager
        entryManager.updateTimeEntry(changingEntry, true);

        // Add a RejectReason to the Entry
        entryManager.addRejectReasonToEntry(changingEntry, 1, true);

        // Search for a group of Entries with "design" in the description and status of "pending" and delete them.

        // Define the search criteria.
        TimeEntryFilterFactory filterFactory = entryManager.getTimeEntryFilterFactory();

        Filter searchFilter = new AndFilter(filterFactory.createDescriptionFilter("Persistence",
            StringMatchType.SUBSTRING), filterFactory.createTimeStatusFilter(approvedStatus));

        // Perform the actual search.
        TimeEntry[] matchingEntries = entryManager.searchTimeEntries(searchFilter);

        // Delete the users using atomic mode; auditing is performed.
        long[] timeEntryIds = new long[matchingEntries.length];
        for (int i = 0; i < matchingEntries.length; i++) {
            timeEntryIds[i] = matchingEntries[i].getId();
        }
        entryManager.deleteTimeEntries(timeEntryIds, true);
    }
}
