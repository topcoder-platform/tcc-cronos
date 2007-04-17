/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.delegate.TimeStatusManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerSessionBean;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for TimeStatusManagerSessionBean.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TimeStatusManagerSessionBeanFailureTests extends TestCase {
    /**
     * <p>
     * The TimeStatusManagerSessionBean instance for testing.
     * </p>
     */
    private TimeStatusManagerSessionBean instance;

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
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();

        instance = new TimeStatusManagerSessionBean();
        FailureTestHelper.setUpEJBEnvironment(null, null, instance);

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
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();

        delegate = null;
        instance = null;
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
    public void testCreateTimeStatus() throws Exception {
        try {
            delegate.createTimeStatus(null);
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
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus1() throws Exception {
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
    public void testUpdateTimeStatus2() throws Exception {
        TimeStatus timeStatus = FailureTestHelper.createTestingTimeStatus(null);
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
     * Tests TimeStatusManagerSessionBean#deleteTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus() throws Exception {
        try {
            delegate.deleteTimeStatus(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
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
    public void testGetTimeStatus() throws Exception {
        try {
            delegate.getTimeStatus(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
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
    public void testCreateTimeStatuses1() throws Exception {
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
    public void testCreateTimeStatuses2() throws Exception {
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
    public void testCreateTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            delegate.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus1 });
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
    public void testCreateTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            delegate.createTimeStatuses(new TimeStatus[] { timeStatus1, null });
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
    public void testCreateTimeStatuses5() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);
        timeStatus2.setId(34);

        try {
            delegate.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testCreateTimeStatuses6() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("CreationUser");

        try {
            delegate.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testCreateTimeStatuses7() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationUser");

        try {
            delegate.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testUpdateTimeStatuses1() throws Exception {
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
    public void testUpdateTimeStatuses2() throws Exception {
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
    public void testUpdateTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            delegate.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus1 });
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
    public void testUpdateTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            delegate.updateTimeStatuses(new TimeStatus[] { timeStatus1, null });
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
    public void testUpdateTimeStatuses5() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            delegate.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testUpdateTimeStatuses6() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationUser");
        timeStatus2.setId(300);

        try {
            delegate.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testDeleteTimeStatuses1() throws Exception {
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
    public void testDeleteTimeStatuses2() throws Exception {
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
    public void testDeleteTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            delegate.deleteTimeStatuses(new long[] { timeStatus1.getId(), timeStatus1.getId() });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testGetTimeStatuses() throws Exception {
        try {
            delegate.getTimeStatuses(null);
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
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses1() throws Exception {
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
    public void testSearchTimeStatuses2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            delegate.searchTimeStatuses(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
