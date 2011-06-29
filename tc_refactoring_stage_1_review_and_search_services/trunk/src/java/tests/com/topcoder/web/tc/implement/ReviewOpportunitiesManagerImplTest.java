/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.implement;

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

/**
 * <p>
 * Tests the ReviewOpportunitiesManagerImpl class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ReviewOpportunitiesManagerImplTest extends AbstractJUnit4SpringContextTests {

    /**
     * Represents an instance of ReviewOpportunitiesManager for test.
     */
    private ReviewOpportunitiesManager manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        manager = (ReviewOpportunitiesManager) applicationContext.getBean("reviewOpportunitiesManager");
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        manager = null;
    }

    /**
     * <p>
     * Tests that ReviewOpportunitiesManagerImpl is extended from AbstractManagerImpl.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("ReviewOpportunitiesManagerImpl should extends from AbstractManagerImpl",
                manager instanceof AbstractManagerImpl);
    }

    /**
     * <p>
     * Tests that ReviewOpportunitiesManagerImpl is extended from ReviewOpportunitiesManager.
     * </p>
     */
    @Test
    public void testImplements() {
        assertTrue("ReviewOpportunitiesManagerImpl should implement ReviewOpportunitiesManager",
                manager instanceof ReviewOpportunitiesManager);
    }

    /**
     * <p>
     * Tests the default constructor. Since the constructor does nothing, this test case does not verify anything.
     * </p>
     */
    @Test
    public void testCtor() {
        new ReviewOpportunitiesManagerImpl();
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * negative page number (other than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveReviewOpportunities1_InvalidPageNumber_Negative() throws Exception {
        manager.retrieveReviewOpportunities(null, null, -3, 10, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * zero page number. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveReviewOpportunities1_InvalidPageNumber_Zero() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 0, 10, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * negative page size (other than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveReviewOpportunities1_InvalidPageSize_Negative() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, -5, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * zero page size. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveReviewOpportunities1_InvalidPageSize_Zero() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, 0, new ReviewOpportunitiesFilter());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * null filter. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveReviewOpportunities1_InvalidFilter_Null() throws Exception {
        manager.retrieveReviewOpportunities(null, null, 1, 10, (ReviewOpportunitiesFilter) null);
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * valid arguments but does not perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities1_NoFilter() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities("contestName", SortingOrder.DESCENDING,
                -1, -1, filter);
        assertEquals("2 projects should be returned", 2, result.size());
        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
                .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", result.get(0).getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", result.get(0).getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", result.get(0).getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
                .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(String, SortingOrder, int, int, ReviewOpportunitiesFilter) methods. It uses
     * valid arguments but does not perform any filtering.
     * </p>
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
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", result.get(0).getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", result.get(0).getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", result.get(0).getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
                .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It uses null filter.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveReviewOpportunities2_InvalidFilter_Null() throws Exception {
        manager.retrieveReviewOpportunities((ReviewOpportunitiesFilter) null);
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It uses valid arguments but does not
     * perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_NoFilter() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("2 projects should be returned", 2, result.size());
        assertEquals("contest name should be returned", "test component 1", result.get(0).getContestName());
        assertEquals("type should be returned", "Assembly", result.get(0).getType());
        assertEquals("subtype should be returned", "System Assembly", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 300.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 200.0, result.get(0)
                .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 1, result.get(0).getSubmissionsNumber());
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", result.get(0).getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", result.get(0).getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", result.get(0).getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 2, result.get(0)
                .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It uses valid arguments but does not
     * perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_Filter() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        filter.setContestName("test");
        List<String> list = new ArrayList<String>();
        list.add("Component Design");
        list.add("System Assembly");
        list.add("Conceptualization");
        filter.setSubtype(list);
        list = new ArrayList<String>();
        list.add("Component Design");
        list.add("Conceptualization");
        filter.setType(list);
        list = new ArrayList<String>();
        list.add("Design");
        list.add("UI Development");
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
        filter.setPaymentStart(400);
        filter.setPaymentEnd(1000);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
                .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", result.get(0).getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", result.get(0).getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", result.get(0).getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
                .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It tests the BEFORE criteria for review
     * end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDate_Before() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("2 projects should be returned", 2, result.size());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It tests the AFTER criteria for review
     * end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDate_After() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110131"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("2 projects should be returned", 2, result.size());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It tests the ON criteria for review end
     * date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDate_On() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.ON);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("0 project should be returned", 0, result.size());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It tests the BEFORE_CURRENT_DATE
     * criteria for review end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDate_BeforeCurrent() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("2 projects should be returned", 2, result.size());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It tests the AFTER_CURRENT_DATE
     * criteria for review end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDate_AfterCurrent() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("0 project should be returned", 0, result.size());
    }

    /**
     * <p>
     * Tests the retrieveReviewOpportunities(ReviewOpportunitiesFilter) methods. It tests the BETWEEN_DATES criteria for
     * review end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveReviewOpportunities2_ReviewEndDate_Between() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110801"));
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setReviewEndDate(date);

        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        assertEquals("0 project should be returned", 0, result.size());
    }
}
