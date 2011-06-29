/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests.implement;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.ContestsServiceManagerException;
import com.topcoder.web.tc.ReviewOpportunitiesManager;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilter;

/**
 * <p>
 * Failure test for ReviewOpportunitiesManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/failure.xml" })
public class ReviewOpportunitiesManagerImplFailureTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of ReviewOpportunitiesManager used in test.
     */
    private ReviewOpportunitiesManager manager;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() throws Exception {
        manager = (ReviewOpportunitiesManager) applicationContext.getBean("reviewOpportunitiesManager");
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageNumberIsInvalid1() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 0, 10, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageNumberIsInvalid2() throws Exception {
        manager.retrieveReviewOpportunities(null, null, -2, 10, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageSizeIsInvalid1() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, 0, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_pageSizeIsInvalid2() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, -2, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When filter is null.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveUpcomingContests1_filterIsNull() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, 10, null);
    }

    /**
     * <p>
     * Failure test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When the connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testretrieveUpcomingContests1_ConnectionIsInvalid() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, 10, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Failure test for retrieveReviewOpportunities(ReviewOpportunitiesFilter filter). When filter is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testretrieveReviewOpportunities2_filterIsNull() throws Exception {
        manager.retrieveReviewOpportunities(null);
    }

    /**
     * <p>
     * Failure test for retrieveReviewOpportunities(ReviewOpportunitiesFilter filter). When the connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testretrieveReviewOpportunities2_ConnectionIsInvalid() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        filter.setContestName("A");
        manager.retrieveReviewOpportunities(filter);
    }
}