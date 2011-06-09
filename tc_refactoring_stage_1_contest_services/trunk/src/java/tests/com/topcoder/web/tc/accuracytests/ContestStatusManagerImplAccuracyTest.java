/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.dto.ContestStatusDTO;
import com.topcoder.web.tc.dto.ContestStatusFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.impl.ContestStatusManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for ContestStatusManagerImpl.
 * </p>
 *
 * @author sokol
 * @version 1.0
 */
public class ContestStatusManagerImplAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents ContestStatusManagerImpl instance for testing.
     * </p>
     */
    private ContestStatusManagerImpl impl;

    /**
     * <p>
     * Represents ContestStatusFilter instance for testing.
     * </p>
     */
    private ContestStatusFilter filter;

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy tests for class under test.
     * </p>
     *
     * @return TestSuite that aggregates all Accuracy tests for class under test
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestStatusManagerImplAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        impl = createContestStatusManagerImpl();
        filter = new ContestStatusFilter();
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
     * Tests ContestStatusManagerImpl constructor.
     * </p>
     * <p>
     * ContestStatusManagerImpl instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("ContestStatusManagerImpl instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#checkConfiguration()}.
     * </p>
     * <p>
     * ContestStatusManagerImpl should be configured properly.
     * </p>
     */
    @Test
    public void testConfig() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with filter by
     * contest name.
     * </p>
     * <p>
     * Project status should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_ContestName() throws Exception {
        filter.setContestName("roject1");
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("Project status should be retrieved successfully.", 1, actual.size());
        checkProjectStatus1(actual.get(0));
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with filter by
     * contest name and before.
     * </p>
     * <p>
     * Project status should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_Before() throws Exception {
        filter.setContestName("roject2");
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setFirstDate(Calendar.getInstance().getTime());
        contestFinalizationDate.setIntervalType(DateIntervalType.BEFORE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("Project status should be retrieved successfully.", 1, actual.size());
        checkProjectStatus2(actual.get(0));
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with after.
     * </p>
     * <p>
     * No project status should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_After() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setFirstDate(new GregorianCalendar(2012, 0, 1).getTime());
        contestFinalizationDate.setIntervalType(DateIntervalType.AFTER);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("Project status should be retrieved successfully.", 0, actual.size());
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with between.
     * </p>
     * <p>
     * All active projects statuses should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_Between() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setFirstDate(new Date(0));
        contestFinalizationDate.setSecondDate(new GregorianCalendar(2012, 0, 1).getTime());
        contestFinalizationDate.setIntervalType(DateIntervalType.BETWEEN_DATES);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("All active projects statuses should be retrieved successfully.", 4, actual.size());
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with before
     * current.
     * </p>
     * <p>
     * All active projects statuses should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_BeforeCurrnet() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("Project status should be retrieved successfully.", 4, actual.size());
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with after current.
     * </p>
     * <p>
     * No active projects statuses should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_AfterCurrnet() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("Project status should be retrieved successfully.", 0, actual.size());
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerImpl#retrieveContestStatuses(ContestStatusFilter)} with filter by
     * contest name and between.
     * </p>
     * <p>
     * All active projects statuses should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_BeforeCurrent() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("All active projects statuses should be retrieved successfully.", 4, actual.size());
    }

    /**
     * <p>
     * Tests
     * {@link ContestStatusManagerImpl#retrieveContestStatuses(String, com.topcoder.web.tc.SortingOrder, int, int, ContestStatusFilter)}
     * with paging.
     * </p>
     * <p>
     * Active project status should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_Paging() throws Exception {
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(null, SortingOrder.ASCENDING, 1, 1,
            filter);
        assertEquals("Project status should be retrieved successfully.", 1, actual.size());
        actual = impl.retrieveContestStatuses(null, SortingOrder.ASCENDING, 2, 1, filter);
        assertEquals("Project status should be retrieved successfully.", 1, actual.size());
    }

    /**
     * <p>
     * Tests
     * {@link ContestStatusManagerImpl#retrieveContestStatuses(String, com.topcoder.web.tc.SortingOrder, int, int, ContestStatusFilter)}
     * with prize filter.
     * </p>
     * <p>
     * Active project1 status should be retrieved successfully.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testRetrieveContestStatuses_Prize() throws Exception {
        filter.setPrizeLowerBound(501);
        List<ContestStatusDTO> actual = impl.retrieveContestStatuses(filter);
        assertEquals("Project status should be retrieved successfully.", 3, actual.size());
    }
}
