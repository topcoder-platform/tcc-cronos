/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.PastContestDTO;
import com.topcoder.web.tc.dto.PastContestFilter;
import com.topcoder.web.tc.impl.PastContestsManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for PastContestsManagerImpl.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class PastContestsManagerImplAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents PastContestsManagerImpl instance for testing.
     * </p>
     */
    private PastContestsManagerImpl impl;

    /**
     * <p>
     * Represents PastContestFilter instance for testing.
     * </p>
     */
    private PastContestFilter filter;

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy tests for class under test.
     * </p>
     * @return TestSuite that aggregates all Accuracy tests for class under test
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PastContestsManagerImplAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        impl = createPastContestsManagerImpl();
        filter = new PastContestFilter();
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
     * Tests PastContestsManagerImpl constructor.
     * </p>
     * <p>
     * PastContestsManagerImpl instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("PastContestsManagerImpl instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#checkConfiguration()}.
     * </p>
     * <p>
     * PastContestsManagerImpl should be configured properly.
     * </p>
     */
    @Test
    public void testConfig() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by contest name.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_ContestName() throws Exception {
        filter.setContestName("roject4");
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        checkPastProject4(actualList.get(0));
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(String, SortingOrder, int, int, PastContestFilter)}
     * with paging and sorting.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_Paging() throws Exception {
        List < PastContestDTO > actualList =
                impl.retrievePastContests("projectResult.finalScore", SortingOrder.ASCENDING, 1, 1, filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        assertEquals("Project3 should be retrieved successfully.", "Project3", actualList.get(0).getContestName());
        actualList = impl.retrievePastContests("projectResult.finalScore", SortingOrder.ASCENDING, 2, 1, filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        assertEquals("Project4 should be retrieved successfully.", "Project4", actualList.get(0).getContestName());
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by winner handle.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_WinnerHandle() throws Exception {
        filter.setWinnerHandle("Handle1");
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        checkPastProject3(actualList.get(0));
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by before date.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_Before() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setFirstDate(Calendar.getInstance().getTime());
        contestFinalizationDate.setIntervalType(DateIntervalType.BEFORE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by after date.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_After() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.AFTER);
        contestFinalizationDate.setFirstDate(Calendar.getInstance().getTime());
        filter.setContestFinalizationDate(contestFinalizationDate);
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 0, actualList.size());
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by between.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_Between() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setFirstDate(new GregorianCalendar(2010, 0, 1).getTime());
        contestFinalizationDate.setSecondDate(new GregorianCalendar(2011, 0, 1).getTime());
        contestFinalizationDate.setIntervalType(DateIntervalType.BETWEEN_DATES);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 1, actualList.size());
        checkPastProject3(actualList.get(0));
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by before current
     * date.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_BeforeCurrent() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 2, actualList.size());
    }

    /**
     * <p>
     * Tests {@link PastContestsManagerImpl#retrievePastContests(PastContestFilter)} with filter by after current date.
     * </p>
     * <p>
     * Past project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrievePastContests_AfterCurrent() throws Exception {
        DateIntervalSpecification contestFinalizationDate = new DateIntervalSpecification();
        contestFinalizationDate.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setContestFinalizationDate(contestFinalizationDate);
        List < PastContestDTO > actualList = impl.retrievePastContests(filter);
        assertEquals("Project should be retrieved successfully.", 0, actualList.size());
    }


}
