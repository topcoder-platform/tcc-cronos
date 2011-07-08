/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestsFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilter;

/**
 * <p>
 * Accuracy test for ReviewOpportunitiesFilter class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewOpportunitiesFilterAccuracyTest {
    /**
     * Represents the instance of ReviewOpportunitiesFilter used in test.
     */
    private ReviewOpportunitiesFilter filter;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new ReviewOpportunitiesFilter();
    }

    /**
     * Accuracy test for ReviewOpportunitiesFilter(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", filter);
        assertEquals("The base class is incorrect.", filter.getClass().getSuperclass(), AbstractContestsFilter.class);
    }

    /**
     * <p>
     * Accuracy test for paymentStart property.
     * </p>
     */
    @Test
    public void testPaymentStartt() {
        int paymentStart = 500;
        filter.setPaymentStart(paymentStart);
        assertEquals("The paymentStart is inocrrect.", paymentStart, filter.getPaymentStart().intValue());
    }

    /**
     * <p>
     * Accuracy test for paymentEnd property.
     * </p>
     */
    @Test
    public void testPaymentEnd() {
        int paymentEnd = 500;
        filter.setPaymentEnd(paymentEnd);
        assertEquals("The paymentEnd is inocrrect.", paymentEnd, filter.getPaymentEnd().intValue());
    }

    /**
     * <p>
     * Accuracy test for reviewEndDate property.
     * </p>
     */
    @Test
    public void testReviewEndDate() {
        DateIntervalSpecification reviewEndDate = new DateIntervalSpecification();
        filter.setReviewEndDate(reviewEndDate);
        assertEquals("The reviewEndDate is inocrrect.", reviewEndDate, filter.getReviewEndDate());
    }
}