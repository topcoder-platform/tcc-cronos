/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestDTO;
import com.topcoder.web.tc.dto.ReviewOpportunityDTO;

/**
 * <p>
 * Accuracy test for ReviewOpportunityDTO class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewOpportunityDTOAccuracyTest {
    /**
     * Represents the instance of ReviewOpportunityDTO used in test.
     */
    private ReviewOpportunityDTO dto;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new ReviewOpportunityDTO();
    }

    /**
     * Accuracy test for ReviewOpportunityDTO(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", dto);
        assertEquals("The base class is incorrect.", dto.getClass().getSuperclass(), AbstractContestDTO.class);
    }

    /**
     * <p>
     * Accuracy test for primaryReviewerPayment property.
     * </p>
     */
    @Test
    public void testPrimaryReviewerPayment() {
        double primaryReviewerPayment = 11.1;
        dto.setPrimaryReviewerPayment(primaryReviewerPayment);
        assertEquals("The primaryReviewerPayment is inocrrect.", primaryReviewerPayment, dto
            .getPrimaryReviewerPayment());
    }

    /**
     * <p>
     * Accuracy test for secondaryReviewerPayment property.
     * </p>
     */
    @Test
    public void testSecondaryReviewerPayment() {
        double secondaryReviewerPayment = 11.2;
        dto.setSecondaryReviewerPayment(secondaryReviewerPayment);
        assertEquals("The secondaryReviewerPayment is inocrrect.", secondaryReviewerPayment, dto
            .getSecondaryReviewerPayment());
    }

    /**
     * <p>
     * Accuracy test for submissionsNumber property.
     * </p>
     */
    @Test
    public void testSubmissionsNumber() {
        int submissionsNumber = 11;
        dto.setSubmissionsNumber(submissionsNumber);
        assertEquals("The submissionsNumber is inocrrect.", submissionsNumber, dto.getSubmissionsNumber());
    }

    /**
     * <p>
     * Accuracy test for opensOn property.
     * </p>
     */
    @Test
    public void testOpensOn() {
        Date opensOn = new Date(0);
        dto.setOpensOn(opensOn);
        assertEquals("The opensOn is inocrrect.", opensOn, dto.getOpensOn());
    }

    /**
     * <p>
     * Accuracy test for reviewStart property.
     * </p>
     */
    @Test
    public void testReviewStart() {
        Date reviewStart = new Date(0);
        dto.setReviewStart(reviewStart);
        assertEquals("The reviewStart is inocrrect.", reviewStart, dto.getReviewStart());
    }

    /**
     * <p>
     * Accuracy test for reviewEnd property.
     * </p>
     */
    @Test
    public void testReviewEnd() {
        Date reviewEnd = new Date(0);
        dto.setReviewEnd(reviewEnd);
        assertEquals("The reviewEnd is inocrrect.", reviewEnd, dto.getReviewEnd());
    }

    /**
     * <p>
     * Accuracy test for numberOfReviewPositionsAvailable property.
     * </p>
     */
    @Test
    public void testNumberOfReviewPositionsAvailable() {
        int numberOfReviewPositionsAvailable = 2;
        dto.setNumberOfReviewPositionsAvailable(numberOfReviewPositionsAvailable);
        assertEquals("The numberOfReviewPositionsAvailable is inocrrect.", numberOfReviewPositionsAvailable, dto
            .getNumberOfReviewPositionsAvailable());
    }
}