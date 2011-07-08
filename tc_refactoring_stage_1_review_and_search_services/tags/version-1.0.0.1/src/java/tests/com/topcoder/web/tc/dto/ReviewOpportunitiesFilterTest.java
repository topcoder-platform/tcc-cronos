/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests the ReviewOpportunitiesFilter class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ReviewOpportunitiesFilterTest {

    /**
     * Represents an instance of ReviewOpportunitiesFilter for test.
     */
    private ReviewOpportunitiesFilter filter;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new ReviewOpportunitiesFilter();
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        filter = null;
    }

    /**
     * <p>
     * Tests that ReviewOpportunitiesFilter is extended from AbstractContestFilter.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("ReviewOpportunitiesFilter should extends from AbstractContestFilter",
                filter instanceof AbstractContestsFilter);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        filter = new ReviewOpportunitiesFilter();
        assertNull("payment start should be initialized to null", filter.getPaymentStart());
        assertNull("payment end should be initialized to null", filter.getPaymentEnd());
        assertNull("review end date should be initialized to null", filter.getReviewEndDate());
    }

    /**
     * <p>
     * Tests the setPaymentStart and getPaymentStart methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetPaymentStart() {
        // null
        filter.setPaymentStart(null);
        assertNull("payment start should be set", filter.getPaymentStart());
        // zero
        filter.setPaymentStart(0);
        assertEquals("payment start should be set", 0, filter.getPaymentStart().intValue());
        // negative
        filter.setPaymentStart(-200);
        assertEquals("payment start should be set", -200, filter.getPaymentStart().intValue());
        // positive
        filter.setPaymentStart(500);
        assertEquals("payment start should be set", 500, filter.getPaymentStart().intValue());
    }

    /**
     * <p>
     * Tests the setPaymentEnd and getPaymentEnd methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetPaymentEnd() {
        // null
        filter.setPaymentEnd(null);
        assertNull("payment end should be set", filter.getPaymentEnd());
        // zero
        filter.setPaymentEnd(0);
        assertEquals("payment end should be set", 0, filter.getPaymentEnd().intValue());
        // negative
        filter.setPaymentEnd(-200);
        assertEquals("payment end should be set", -200, filter.getPaymentEnd().intValue());
        // positive
        filter.setPaymentEnd(500);
        assertEquals("payment end should be set", 500, filter.getPaymentEnd().intValue());
    }

    /**
     * <p>
     * Tests the setReviewEndDate and getReviewEndDate methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetReviewEndDate() {
        // null
        filter.setReviewEndDate(null);
        assertNull("review end date should be set", filter.getReviewEndDate());
        // non-null
        DateIntervalSpecification date = new DateIntervalSpecification();
        filter.setReviewEndDate(date);
        assertEquals("review end date should be set", date, filter.getReviewEndDate());
    }
}
