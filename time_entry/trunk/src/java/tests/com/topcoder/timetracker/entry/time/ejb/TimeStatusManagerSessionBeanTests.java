/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.delegate.TimeStatusManagerDelegate;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TimeStatusManagerSessionBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeStatusManagerSessionBeanTests extends TestCase {
    /**
     * <p>
     * The TimeStatusManagerSessionBean instance for testing.
     * </p>
     */
    private TimeStatusManagerSessionBean bean;

    /**
     * <p>
     * The TimeStatusManagerDelegate instance for testing.
     * </p>
     */
    private TimeStatusManagerDelegate delegate;

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

        bean = new TimeStatusManagerSessionBean();
        TestHelper.setUpEJBEnvironment(null, null, bean);

        delegate = new TimeStatusManagerDelegate(
            "com.topcoder.timetracker.entry.time.delegate.TimeStatusManagerDelegate");
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
        return new TestSuite(TimeStatusManagerSessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeStatusManagerSessionBean#TimeStatusManagerSessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TimeStatusManagerSessionBean instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TimeStatusManagerSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#createTimeStatus(TimeStatus) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        assertEquals("Failed to insert the time statuses.", timeStatus, delegate.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus_NullTimeStatus() throws Exception {
        try {
            delegate.createTimeStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#updateTimeStatus(TimeStatus) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        timeStatus.setDescription("Time status.");
        delegate.updateTimeStatus(timeStatus);

        assertEquals("Failed to update the time status.", timeStatus, delegate.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus_NullTimeStatus() throws Exception {
        try {
            delegate.updateTimeStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus_UnrecognizedEntityException() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        timeStatus.setId(187);

        try {
            delegate.updateTimeStatus(timeStatus);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatus(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#deleteTimeStatus(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        delegate.deleteTimeStatus(timeStatus.getId());

        assertEquals("Failed to remove the time status.", 0, delegate.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus_UnrecognizedEntityException() throws Exception {
        try {
            delegate.deleteTimeStatus(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatus(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#getTimeStatus(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        assertEquals("Failed to get the time statuses.", timeStatus, delegate.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus_UnrecognizedEntityException() throws Exception {
        try {
            delegate.getTimeStatus(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] insertedTimeStatuses = delegate.getTimeStatuses(new long[] {timeStatus1.getId(),
            timeStatus2.getId()});

        assertEquals("Failed to insert the time statuses.", timeStatus1, insertedTimeStatuses[0]);
        assertEquals("Failed to insert the time statuses.", timeStatus2, insertedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_NullTimeEntries() throws Exception {
        try {
            delegate.createTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_EmptyTimeEntries() throws Exception {
        try {
            delegate.createTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_SameElement() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);

        try {
            delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_NullInTimeEntries() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);

        try {
            delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithIdSet() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatus2.setId(34);

        try {
            delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithNullCreationUser() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("CreationUser");

        try {
            delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithNullModificationUser() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationUser");

        try {
            delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        timeStatus1.setDescription("Time status.");
        delegate.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] updatedTimeStatuses = delegate.getTimeStatuses(new long[] {timeStatus1.getId(),
            timeStatus2.getId()});

        assertEquals("Failed to update the time statuses.", timeStatus1, updatedTimeStatuses[0]);
        assertEquals("Failed to update the time statuses.", timeStatus2, updatedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_NullTimeEntries() throws Exception {
        try {
            delegate.updateTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_EmptyTimeEntries() throws Exception {
        try {
            delegate.updateTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_SameTimeStatus() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            delegate.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_NullInTimeEntries() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            delegate.updateTimeStatuses(new TimeStatus[] {timeStatus1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_TimeStatusWithIdSet() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        try {
            delegate.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_TimeStatusWithNullModificationUser() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationUser");
        timeStatus2.setId(300);

        try {
            delegate.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#deleteTimeStatuses(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        delegate.deleteTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to remove the time statuses.", 0, delegate.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_NullTimeStatusIds() throws Exception {
        try {
            delegate.deleteTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_EmptyTimeStatusIds() throws Exception {
        try {
            delegate.deleteTimeStatuses(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_EqualTimeStatusId() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            delegate.deleteTimeStatuses(new long[] {timeStatus1.getId(), timeStatus1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#getTimeStatuses(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] timeStatuses = delegate.getTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to get the time statuses.", timeStatuses[0], timeStatus1);
        assertEquals("Failed to get the time statuses.", timeStatuses[1], timeStatus2);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses_NullTimeStatusIds() throws Exception {
        try {
            delegate.getTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#searchTimeStatuses(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#searchTimeStatuses(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatus1.setDescription("NewTimeStatus");
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus1});

        Filter filter = delegate.getTimeStatusFilterFactory().createDescriptionFilter(timeStatus1.getDescription(),
            StringMatchType.ENDS_WITH);
        TimeStatus[] timeStatuses = delegate.searchTimeStatuses(filter);

        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        assertEquals("Failed to search the time statuses.", timeStatus1, timeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses_NullCriteria() throws Exception {
        try {
            delegate.searchTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeStatuses_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");
        try {
            delegate.searchTimeStatuses(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatusFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#getTimeStatusFilterFactory() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusFilterFactory() throws Exception {
        assertNotNull("Failed to get the time status filter factory.", bean.getTimeStatusFilterFactory());
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getAllTimeStatuses() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#getAllTimeStatuses() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses() throws Exception {
        assertEquals("The time_status table should be empty.", 0, delegate.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        bean.setSessionContext(null);
        assertNull("Failed to get the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#ejbCreate() is correct.
     * </p>
     */
    public void testEjbCreate() {
        bean.ejbCreate();
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerSessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);
        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

}