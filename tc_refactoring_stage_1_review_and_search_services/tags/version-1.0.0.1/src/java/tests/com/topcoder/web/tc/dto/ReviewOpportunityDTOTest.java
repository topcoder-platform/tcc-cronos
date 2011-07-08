/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests the ReviewOpportunityDTO class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ReviewOpportunityDTOTest {

    /**
     * Represents an instance of ReviewOpportunityDTO for test.
     */
    private ReviewOpportunityDTO dto;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new ReviewOpportunityDTO();
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        dto = null;
    }

    /**
     * <p>
     * Tests that ReviewOpportunityDTO is extended from AbstractContestDTO.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("ReviewOpportunityDTO should extends from AbstractContestDTO", dto instanceof AbstractContestDTO);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        dto = new ReviewOpportunityDTO();
        assertEquals("primary reviewer payment should be initialized to null", 0.0, dto.getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be initialized to 0", 0.0, dto.getSecondaryReviewerPayment());
        assertEquals("submissions number should be initialized to 0", 0, dto.getSubmissionsNumber());
        assertNull("opens on should be initialized to null", dto.getOpensOn());
        assertNull("review start date should be initialized to null", dto.getReviewStart());
        assertNull("review end date should be initialized to null", dto.getReviewEnd());
        assertEquals("number of review positions available should be initialized to 0", 0,
                dto.getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Tests the setPrimaryReviewerPayment and getPrimaryReviewerPayment methods. It verifies that the field can be
     * accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetPrimaryReviewerPayment() {
        // zero
        dto.setPrimaryReviewerPayment(0);
        assertEquals("primary reviewer payment should be set", 0.0, dto.getPrimaryReviewerPayment());
        // negative
        dto.setPrimaryReviewerPayment(-123.45);
        assertEquals("primary reviewer payment should be set", -123.45, dto.getPrimaryReviewerPayment(), 1E-6);
        // positive
        dto.setPrimaryReviewerPayment(543.21);
        assertEquals("primary reviewer payment should be set", 543.21, dto.getPrimaryReviewerPayment(), 1E-6);
    }

    /**
     * <p>
     * Tests the setSecondaryReivewerPayment and getSecondaryReviewerPayment methods. It verifies that the field can be
     * accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetSecondaryReviewerPayment() {
        // zero
        dto.setSecondaryReviewerPayment(0);
        assertEquals("secondary reviewer payment should be set", 0.0, dto.getSecondaryReviewerPayment());
        // negative
        dto.setSecondaryReviewerPayment(-123.45);
        assertEquals("secondary reviewer payment should be set", -123.45, dto.getSecondaryReviewerPayment(), 1E-6);
        // positive
        dto.setSecondaryReviewerPayment(543.21);
        assertEquals("secondary reviewer payment should be set", 543.21, dto.getSecondaryReviewerPayment(), 1E-6);
    }

    /**
     * <p>
     * Tests the setSubmissionNumber and getSubmissionNumber methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetSubmissionsNumber() {
        // zero
        dto.setSubmissionsNumber(0);
        assertEquals("submissions number should be set", 0, dto.getSubmissionsNumber());
        // negative
        dto.setSubmissionsNumber(-2);
        assertEquals("submissions number should be set", -2, dto.getSubmissionsNumber());
        // positive
        dto.setSubmissionsNumber(5);
        assertEquals("submissions number should be set", 5, dto.getSubmissionsNumber());
    }

    /**
     * <p>
     * Tests the setOpensOn and getOpensOn methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetOpensOn() {
        // null
        dto.setOpensOn(null);
        assertNull("opens on should be set", dto.getOpensOn());
        // non-null
        Date date = new Date();
        dto.setOpensOn(date);
        assertEquals("opens on should be set", date, dto.getOpensOn());
    }

    /**
     * <p>
     * Tests the setReviewStart and getReviewStart methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetReviewStart() {
        // null
        dto.setReviewStart(null);
        assertNull("review start date should be set", dto.getReviewStart());
        // non-null
        Date date = new Date();
        dto.setReviewStart(date);
        assertEquals("review start date should be set", date, dto.getReviewStart());
    }

    /**
     * <p>
     * Tests the setReviewEnd and getReviewEnd methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetReviewEnd() {
        // null
        dto.setReviewEnd(null);
        assertNull("review end date should be set", dto.getReviewEnd());
        // non-null
        Date date = new Date();
        dto.setReviewEnd(date);
        assertEquals("review end date should be set", date, dto.getReviewEnd());
    }

    /**
     * <p>
     * Tests the setNumberOfReviewPositionsAvailable and getNumberOfReviewPositionsAvailable methods. It verifies that
     * the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetNumberOfReviewPositionsAvailable() {
        // zero
        dto.setNumberOfReviewPositionsAvailable(0);
        assertEquals("number of review positions available should be set", 0, dto.getNumberOfReviewPositionsAvailable());
        // negative
        dto.setNumberOfReviewPositionsAvailable(-2);
        assertEquals("number of review positions available should be set", -2,
                dto.getNumberOfReviewPositionsAvailable());
        // positive
        dto.setNumberOfReviewPositionsAvailable(5);
        assertEquals("number of review positions available should be set", 5, dto.getNumberOfReviewPositionsAvailable());
    }
}
