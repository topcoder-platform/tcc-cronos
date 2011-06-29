/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests.implement;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.ContestsServiceManagerException;
import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;

/**
 * <p>
 * Failure test for UpcomingContestsManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/failure.xml" })
public class UpcomingContestsManagerImplFailureTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of UpcomingContestsManager used in test.
     */
    private UpcomingContestsManager manager;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() throws Exception {
        manager = (UpcomingContestsManager) applicationContext.getBean("upcomingContestsManager");
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageNumberIsInvalid1() throws Exception {
        manager.retrieveUpcomingContests(null, null, 0, 10, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageNumberIsInvalid2() throws Exception {
        manager.retrieveUpcomingContests(null, null, -2, 10, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageSizeIsInvalid1() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, 0, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageSizeIsInvalid2() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, -2, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). When filter is null.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_filterIsNull() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, 10, null);
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). When the connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testretrieveUpcomingContests1_ConnectionIsInvalid() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, 10, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(UpcomingContestsFilter filter). When filter is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests2_filterIsNull() throws Exception {
        manager.retrieveUpcomingContests(null);
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(UpcomingContestsFilter filter). When the connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testretrieveUpcomingContests2_ConnectionIsInvalid() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        filter.setContestName("A");
        manager.retrieveUpcomingContests(filter);
    }
}