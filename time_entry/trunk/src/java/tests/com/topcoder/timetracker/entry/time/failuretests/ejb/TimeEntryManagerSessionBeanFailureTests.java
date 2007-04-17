/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.delegate.TimeEntryManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerSessionBean;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for TimeEntryManagerSessionBean.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TimeEntryManagerSessionBeanFailureTests extends TestCase {
    /**
     * <p>
     * The TimeEntryManagerSessionBean instance for testing.
     * </p>
     */
    private TimeEntryManagerSessionBean instance;

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
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();

        instance = new TimeEntryManagerSessionBean();
        FailureTestHelper.setUpEJBEnvironment(null, instance, null);

        delegate = new TimeEntryManagerDelegate("com.topcoder.timetracker.entry." +
                "time.delegate.TimeEntryManagerDelegate");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();

        delegate = null;
        instance = null;
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
    public void testAddRejectReasonToEntry() throws Exception {
        try {
            delegate.addRejectReasonToEntry(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testRemoveRejectReasonFromEntry() throws Exception {
        try {
            delegate.removeRejectReasonFromEntry(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        try {
            delegate.removeAllRejectReasonsFromEntry(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testCreateTimeEntry() throws Exception {
        try {
            delegate.createTimeEntry(null, true);
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
     * It tests the case that when entry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry1() throws Exception {
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
    public void testUpdateTimeEntry2() throws Exception {
        TimeEntry timeEntry = FailureTestHelper.createTestingTimeEntry(null);
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
     * Tests TimeEntryManagerSessionBean#deleteTimeEntry(long,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry() throws Exception {
        try {
            delegate.deleteTimeEntry(500, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
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
    public void testGetTimeEntry() throws Exception {
        try {
            delegate.getTimeEntry(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
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
    public void testCreateTimeEntries1() throws Exception {
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
    public void testCreateTimeEntries2() throws Exception {
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
    public void testCreateTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);

        try {
            delegate.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry1 }, true);
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
    public void testCreateTimeEntries4() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);

        try {
            delegate.createTimeEntries(new TimeEntry[] { timeEntry1, null }, true);
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
    public void testCreateTimeEntries5() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            delegate.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries6() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("CreationUser");

        try {
            delegate.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries7() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("ModificationUser");

        try {
            delegate.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries8() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("Date");

        try {
            delegate.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
     * It tests the case that when timeEntries is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries1() throws Exception {
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
    public void testUpdateTimeEntries2() throws Exception {
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
    public void testUpdateTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        try {
            delegate.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry1 }, true);
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
    public void testUpdateTimeEntries4() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        try {
            delegate.updateTimeEntries(new TimeEntry[] { timeEntry1, null }, true);
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
    public void testUpdateTimeEntries5() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);

        try {
            delegate.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries6() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("ModificationUser");
        timeEntry2.setId(300);

        try {
            delegate.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries7() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("Date");
        timeEntry2.setId(200);

        try {
            delegate.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries8() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            delegate.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
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
    public void testDeleteTimeEntries1() throws Exception {
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
    public void testDeleteTimeEntries2() throws Exception {
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
    public void testDeleteTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        try {
            delegate.deleteTimeEntries(new long[] { timeEntry1.getId(), timeEntry1.getId() }, true);
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
    public void testDeleteTimeEntries4() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        try {
            delegate.deleteTimeEntries(new long[] { timeEntry1.getId(), 19998 }, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
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
    public void testGetTimeEntries1() throws Exception {
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
    public void testGetTimeEntries2() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        try {
            delegate.getTimeEntries(new long[] { timeEntry1.getId(), 1234 });
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
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
    public void testSearchTimeEntries1() throws Exception {
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
    public void testSearchTimeEntries2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            delegate.searchTimeEntries(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
