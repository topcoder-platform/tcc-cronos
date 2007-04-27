/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.delegate.TimeEntryManagerDelegate;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TimeEntryManagerSessionBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeEntryManagerSessionBeanTests extends TestCase {
    /**
     * <p>
     * The TimeEntryManagerSessionBean instance for testing.
     * </p>
     */
    private TimeEntryManagerSessionBean bean;

    /**
     * <p>
     * The TimeEntryManagerDelegate instance for testing.
     * </p>
     */
    private TimeEntryManagerDelegate delegate;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();

        bean = new TimeEntryManagerSessionBean();
        TestHelper.setUpEJBEnvironment(null, bean, null);

        delegate = new TimeEntryManagerDelegate("com.topcoder.timetracker.entry."
            + "time.delegate.TimeEntryManagerDelegate");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        delegate = null;
        bean = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeEntryManagerSessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerSessionBean#TimeEntryManagerSessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TimeEntryManagerSessionBean instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TimeEntryManagerSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#addRejectReasonToEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#addRejectReasonToEntry(TimeEntry,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, false);
        delegate.addRejectReasonToEntry(timeEntry, 1, true);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds.length);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#addRejectReasonToEntry(TimeEntry,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry_NullTimeEntry() throws Exception {
        try {
            delegate.addRejectReasonToEntry(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#removeRejectReasonFromEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#removeRejectReasonFromEntry(TimeEntry,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, false);
        delegate.addRejectReasonToEntry(timeEntry, 1, false);

        delegate.removeRejectReasonFromEntry(timeEntry, 1, true);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to remove the reject reason of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#removeRejectReasonFromEntry(TimeEntry,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry_NullTimeEntry() throws Exception {
        try {
            delegate.removeRejectReasonFromEntry(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getAllRejectReasonsForEntry(TimeEntry) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#getAllRejectReasonsForEntry(TimeEntry) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, false);
        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);

        assertEquals("There should be no reject reason for time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#removeAllRejectReasonsFromEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, false);
        delegate.addRejectReasonToEntry(timeEntry, 1, true);
        delegate.addRejectReasonToEntry(timeEntry, 2, true);

        delegate.removeAllRejectReasonsFromEntry(timeEntry, true);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to remove all the reject reasons of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry_NullTimeEntry() throws Exception {
        try {
            delegate.removeAllRejectReasonsFromEntry(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#createTimeEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        assertEquals("Failed to insert the time entries.", timeEntry, delegate.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry_NullEntry() throws Exception {
        try {
            delegate.createTimeEntry(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#updateTimeEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        timeEntry.setDescription("Time Entry.");
        delegate.updateTimeEntry(timeEntry, false);

        assertEquals("Failed to update the time entry.", timeEntry, delegate.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry_NullEntry() throws Exception {
        try {
            delegate.updateTimeEntry(null, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry_UnrecognizedEntityException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        timeEntry.setId(100);

        try {
            delegate.updateTimeEntry(timeEntry, false);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntry(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#deleteTimeEntry(long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        delegate.deleteTimeEntry(timeEntry.getId(), true);

        assertEquals("Failed to remove the time entry.", 0, delegate.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntry(long,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry_UnrecognizedEntityException() throws Exception {
        try {
            delegate.deleteTimeEntry(500, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntry(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#getTimeEntry(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        assertEquals("Failed to get the time entries.", timeEntry, delegate.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntry(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry_UnrecognizedEntityException() throws Exception {
        try {
            delegate.getTimeEntry(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] insertedTimeEntries = delegate.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to insert the time entries.", timeEntry1, insertedTimeEntries[0]);
        assertEquals("Failed to insert the time entries.", timeEntry2, insertedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_NullTimeEntries() throws Exception {
        try {
            delegate.createTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_EmptyTimeEntries() throws Exception {
        try {
            delegate.createTimeEntries(new TimeEntry[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_SameElement() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);

        try {
            delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_NullInTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);

        try {
            delegate.createTimeEntries(new TimeEntry[] {timeEntry1, null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithIdSet() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullCreationUser() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("CreationUser");

        try {
            delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullModificationUser() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationUser");

        try {
            delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("Date");

        try {
            delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        timeEntry1.setDescription("Time Entry.");
        delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, false);

        TimeEntry[] updatedTimeEntries = delegate.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to update the time entries.", timeEntry1, updatedTimeEntries[0]);
        assertEquals("Failed to update the time entries.", timeEntry2, updatedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_NullTimeEntries() throws Exception {
        try {
            delegate.updateTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_EmptyTimeEntries() throws Exception {
        try {
            delegate.updateTimeEntries(new TimeEntry[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_SameTimeEntry() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, false);

        try {
            delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_NullInTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, false);

        try {
            delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithIdSet() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        try {
            delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithNullModificationUser() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationUser");
        timeEntry2.setId(300);

        try {
            delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithNullDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("Date");
        timeEntry2.setId(200);

        try {
            delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_BatchOperationException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            delegate.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        delegate.deleteTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()}, true);

        assertEquals("Failed to remove the time entries.", 0, delegate.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_NullTimeEntryIds() throws Exception {
        try {
            delegate.deleteTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_EmptyTimeEntryIds() throws Exception {
        try {
            delegate.deleteTimeEntries(new long[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_EqualTimeEntryId() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            delegate.deleteTimeEntries(new long[] {timeEntry1.getId(), timeEntry1.getId()}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_BatchOperationException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            delegate.deleteTimeEntries(new long[] {timeEntry1.getId(), 19998}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntries(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#getTimeEntries(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] timeEntries = delegate.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to get the time entries.", timeEntries[0], timeEntry1);
        assertEquals("Failed to get the time entries.", timeEntries[1], timeEntry2);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntries(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries_NullTimeEntryIds() throws Exception {
        try {
            delegate.getTimeEntries(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntries(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries_BatchOperationException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            delegate.getTimeEntries(new long[] {timeEntry1.getId(), 1234});
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#searchTimeEntries(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#searchTimeEntries(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        Filter filter = delegate.getTimeEntryFilterFactory().createCompanyIdFilter(timeEntry1.getCompanyId());
        TimeEntry[] timeEntries = delegate.searchTimeEntries(filter);

        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        assertEquals("Failed to search the time entries.", timeEntry1, timeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#searchTimeEntries(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries_NullFilter() throws Exception {
        try {
            delegate.searchTimeEntries(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#searchTimeEntries(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeEntries_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            delegate.searchTimeEntries(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getAllTimeEntries() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#getAllTimeEntries() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries() throws Exception {
        assertEquals("The time_entry table should be empty.", 0, delegate.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntryFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#getTimeEntryFilterFactory() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryFilterFactory() throws Exception {
        assertNotNull("Failed to get the time entry filter factory.", bean.getTimeEntryFilterFactory());
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        bean.setSessionContext(null);
        assertNull("Failed to get the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#ejbCreate() is correct.
     * </p>
     */
    public void testEjbCreate() {
        bean.ejbCreate();
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerSessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);
        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

}