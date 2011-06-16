/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.dto.ActiveContestDTO;
import com.topcoder.web.tc.dto.ActiveContestFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.Filterable;
import com.topcoder.web.tc.impl.ActiveContestsManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for ActiveContestsManagerImpl.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ActiveContestsManagerImplAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents ActiveContestsManagerImpl instance for testing.
     * </p>
     */
    private ActiveContestsManagerImpl impl;

    /**
     * <p>
     * Represents ActiveContestFilter instance for testing.
     * </p>
     */
    private ActiveContestFilter filter;

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy tests for class under test.
     * </p>
     * @return TestSuite that aggregates all Accuracy tests for class under test
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActiveContestsManagerImplAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        impl = createActiveContestsManagerImpl();
        filter = new ActiveContestFilter();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        super.tearDown();
        impl = null;
        filter = null;
    }

    /**
     * <p>
     * Tests ActiveContestsManagerImpl constructor.
     * </p>
     * <p>
     * ActiveContestsManagerImpl instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("ActiveContestsManagerImpl instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link ActiveContestsManagerImpl#checkConfiguration()}.
     * </p>
     * <p>
     * ActiveContestsManagerImpl should be configured properly.
     * </p>
     */
    @Test
    public void testConfig() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(com.topcoder.web.tc.dto.ActiveContestFilter)} with
     * empty filter passed.
     * </p>
     * </p>
     * <p>emo
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests1() throws Exception {
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(com.topcoder.web.tc.dto.ActiveContestFilter)} with
     * filter by contest name passed.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests2() throws Exception {
        filter.setContestName("roject1");
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        ActiveContestDTO actual = actualList.get(0);
        checkActiveProject1(actual);
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(com.topcoder.web.tc.dto.ActiveContestFilter)} with
     * filter by lower bound passed.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_FirstPrizeFilter1() throws Exception {
        filter.setPrizeLowerBound(600);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        checkActiveProject2(actualList.get(0));
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(com.topcoder.web.tc.dto.ActiveContestFilter)} with
     * filter by open lower bound.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_FirstPrizeFilter2() throws Exception {
        filter.setPrizeLowerBound(501);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        checkActiveProject2(actualList.get(0));
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(com.topcoder.web.tc.dto.ActiveContestFilter)} with
     * filter with open lowe and upper bounds.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_FirstPrizeFilter3() throws Exception {
        filter.setPrizeLowerBound(Filterable.OPEN_INTERVAL);
        filter.setPrizeUpperBound(Filterable.OPEN_INTERVAL);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * <p>
     * Tests
     * {@link ActiveContestsManagerImpl#retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)}
     * with with paging.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_Page1() throws Exception {
        List < ActiveContestDTO > actualList =
                impl.retrieveActiveContests("firstPrizeInfo.value", SortingOrder.ASCENDING, 1, 1, filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        assertEquals("Project should be retrieved successfully.", "Project1", actualList.get(0).getContestName());
        actualList = impl.retrieveActiveContests("firstPrizeInfo.value", SortingOrder.ASCENDING, 2, 1, filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        assertEquals("Project should be retrieved successfully.", "Project2", actualList.get(0).getContestName());
    }

    /**
     * <p>
     * <p>
     * Tests
     * {@link ActiveContestsManagerImpl#retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)}
     * with with paging and lower bound filter.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_Page2() throws Exception {
        filter.setPrizeLowerBound(501);
        List < ActiveContestDTO > actualList =
                impl.retrieveActiveContests("firstPrizeInfo.value", SortingOrder.ASCENDING, 1, 1, filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        assertEquals("Project should be retrieved successfully.", "Project2", actualList.get(0).getContestName());
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by type.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_FilterType() throws Exception {
        List < String > subTypes = new ArrayList < String >();
        subTypes.add("Component Design");
        filter.setSubTypes(subTypes);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 1, actualList.size());
        checkActiveProject2(actualList.get(0));
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type between.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_RegistrationDate_Between() throws Exception {
        DateIntervalSpecification registrationStartDate = new DateIntervalSpecification();
        Date firstDate = new GregorianCalendar(2010, 0, 2).getTime();
        registrationStartDate.setFirstDate(firstDate);
        Date secondDate = new GregorianCalendar(2012, 0, 1).getTime();
        registrationStartDate.setSecondDate(secondDate);
        registrationStartDate.setIntervalType(DateIntervalType.BETWEEN_DATES);
        filter.setRegistrationStartDate(registrationStartDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 1, actualList.size());
        checkActiveProject2(actualList.get(0));
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type after.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_RegistrationDate_After() throws Exception {
        DateIntervalSpecification registrationStartDate = new DateIntervalSpecification();
        Date firstDate = new GregorianCalendar(2010, 0, 2).getTime();
        registrationStartDate.setFirstDate(firstDate);
        registrationStartDate.setIntervalType(DateIntervalType.AFTER);
        filter.setRegistrationStartDate(registrationStartDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 1, actualList.size());
        checkActiveProject2(actualList.get(0));
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type before.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_RegistrationDate_Before() throws Exception {
        DateIntervalSpecification registrationStartDate = new DateIntervalSpecification();
        Date firstDate = Calendar.getInstance().getTime();
        registrationStartDate.setFirstDate(firstDate);
        registrationStartDate.setIntervalType(DateIntervalType.BEFORE);
        filter.setRegistrationStartDate(registrationStartDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type before current date.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_RegistrationDate_BeforeCurrent() throws Exception {
        DateIntervalSpecification registrationStartDate = new DateIntervalSpecification();
        registrationStartDate.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setRegistrationStartDate(registrationStartDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type before current date.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_RegistrationDate_On() throws Exception {
        DateIntervalSpecification registrationStartDate = new DateIntervalSpecification();
        registrationStartDate.setIntervalType(DateIntervalType.ON);
        Date firstDate = new GregorianCalendar(2011, 0, 1, 12, 0, 0).getTime();
        registrationStartDate.setFirstDate(firstDate);
        filter.setRegistrationStartDate(registrationStartDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 1, actualList.size());
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type before current date.
     * </p>
     * </p>
     * <p>
     * Active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_ContestFinalizationDate_On() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.ON);
        Date firstDate = new GregorianCalendar(2011, 4, 15, 12, 0, 0).getTime();
        contestFinalizationDate.setFirstDate(firstDate);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * <p>
     * Tests {@link ActiveContestsManagerImpl#retrieveActiveContests(ActiveContestFilter)} with filtering by
     * registration date with interval type after current date.
     * </p>
     * </p>
     * <p>
     * No active project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveActiveContests_RegistrationDate_AfterCurrent() throws Exception {
        DateIntervalSpecification registrationStartDate = new DateIntervalSpecification();
        registrationStartDate.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setRegistrationStartDate(registrationStartDate);
        List < ActiveContestDTO > actualList = impl.retrieveActiveContests(filter);
        assertEquals("Project2 should be retrieved successfully.", 0, actualList.size());
    }
}
