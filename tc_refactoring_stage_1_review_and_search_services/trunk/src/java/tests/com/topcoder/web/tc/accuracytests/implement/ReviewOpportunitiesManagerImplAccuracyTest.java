/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.implement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.ReviewOpportunitiesManager;
import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilter;
import com.topcoder.web.tc.dto.ReviewOpportunityDTO;
import com.topcoder.web.tc.implement.ReviewOpportunitiesManagerImpl;

/**
 * <p>
 * Accuracy test for UpcomingContestsManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ReviewOpportunitiesManagerImplAccuracyTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of ReviewOpportunitiesManager used in test.
     */
    private ReviewOpportunitiesManager manager;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        manager = (ReviewOpportunitiesManager) applicationContext.getBean("reviewOpportunitiesManager");
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     */
    @After
    public void tearDown() {
        manager = null;
    }

    /**
     * <p>
     * Accuracy test for ReviewOpportunitiesManagerImpl().
     * </p>
     */
    @Test
    public void testCtor() {
        manager = new ReviewOpportunitiesManagerImpl();
        assertTrue("ReviewOpportunitiesManagerImpl should implement ReviewOpportunitiesManager",
            manager instanceof ReviewOpportunitiesManager);
    }

    /**
     * <p>
     * Accuracy test for retrieveReviewOpportunities(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). Din't use filter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities1_FilterIsEmpty() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities("contestName", SortingOrder.ASCENDING,
            -1, -1, filter);

        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.size());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 1", result.get(0)
            .getContestName());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "Assembly", result.get(0).getType());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "System Assembly", result.get(0).getSubtype());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 300.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 200.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 1, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.get(0)
            .getNumberOfReviewPositionsAvailable());

        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 2", result.get(1)
            .getContestName());
    }

    /**
     * <p>
     * Accuracy test for retrieveReviewOpportunities(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When using pagenation.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities1_Page1() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities("contestName", SortingOrder.DESCENDING,
            2, 1, filter);

        assertEquals("The retrieveReviewOpportunities is incorrect.", 1, result.size());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 1", result.get(0)
            .getContestName());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "Assembly", result.get(0).getType());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "System Assembly", result.get(0).getSubtype());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 300.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 200.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 1, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.get(0)
            .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Accuracy test for retrieveReviewOpportunities(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). When using pagenation.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities1_Page2() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities("contestName", SortingOrder.DESCENDING,
            1, 5, filter);

        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.size());

        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
            .getNumberOfReviewPositionsAvailable());

        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 1", result.get(1)
            .getContestName());
    }

    /**
     * <p>
     * Accuracy test for retrieveReviewOpportunities(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, ReviewOpportunitiesFilter filter). Din't use filter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities1_Filter() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        filter.setContestName("test");
        List<String> list = new ArrayList<String>();
        list.add("Component Design");
        list.add("System Assembly");
        list.add("Conceptualization");
        filter.setSubtype(list);
        list = new ArrayList<String>();
        list.add("Assembly");
        list.add("Conceptualization");
        filter.setType(list);
        list = new ArrayList<String>();
        list.add("Design");
        list.add("Development");
        filter.setCatalog(list);
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setReviewEndDate(date);
        filter.setPaymentStart(100);
        filter.setPaymentEnd(1000);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities("primaryReviewerPayment",
            SortingOrder.ASCENDING, 2, 1, filter);
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
            .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Accuracy test for retrieveReviewOpportunities(ReviewOpportunitiesFilter filter). When no filter condition
     * supplied.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_FilterIsEmpty() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.size());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 1", result.get(0)
            .getContestName());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "Assembly", result.get(0).getType());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "System Assembly", result.get(0).getSubtype());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 300.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 200.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 1, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.get(0)
            .getNumberOfReviewPositionsAvailable());

        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 2", result.get(1)
            .getContestName());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. Use the contest name.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ContentName() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        filter.setContestName("component 1");

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 1, result.size());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "test component 1", result.get(0)
            .getContestName());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "Assembly", result.get(0).getType());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "System Assembly", result.get(0).getSubtype());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 300.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 200.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 1, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.get(0)
            .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. Use after criteria for review end date.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDateAfter() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-05-25 18:00:00.0"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 0, result.size());
    }

    /**
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. Use ON criteria for review end date.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDateOn() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.ON);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-05-25 18:00:00.0"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.size());
    }

    /**
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. Use BETWEEN_DATES criteria for review
     * end date.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDateBetween() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-05-01 00:00:00.0"));
        date.setSecondDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-05-27 18:00:00.0"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.size());
    }

    /**
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. Use BEFORE_CURRENT_DATE criteria for
     * review end date.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDateBeforeCurrent() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 2, result.size());
    }

    /**
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. Use AFTER_CURRENT_DATE criteria for
     * review end date.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDateAfterCurrent() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("The retrieveReviewOpportunities is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for retrieveReviewOpportunities(ReviewOpportunitiesFilter filter). When filter used.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_Filter() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        filter.setContestName("test");

        List<String> subType = new ArrayList<String>();
        subType.add("System Assembly");
        subType.add("Conceptualization");
        filter.setSubtype(subType);

        List<String> type = new ArrayList<String>();
        type.add("Component Design");
        type.add("Conceptualization");
        filter.setType(type);

        List<String> catalog = new ArrayList<String>();
        catalog.add("Design");
        catalog.add("UI Development");
        filter.setCatalog(catalog);

        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);

        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setSubmissionEnd(date);

        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setReviewEndDate(date);

        filter.setPaymentStart(499);
        filter.setPaymentEnd(800);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
            .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getOpensOn().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-24 18:00:00.0", result.get(0)
            .getReviewStart().toString());
        assertEquals("The retrieveReviewOpportunities is incorrect.", "2011-05-25 18:00:00.0", result.get(0)
            .getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
            .getNumberOfReviewPositionsAvailable());
    }
}
