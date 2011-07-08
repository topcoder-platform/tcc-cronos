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
 * Tests the UpcomingContestDTO class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class UpcomingContestDTOTest {

    /**
     * Represents an instance of UpcomingContestDTO for test.
     */
    private UpcomingContestDTO dto;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new UpcomingContestDTO();
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
     * Tests that UpcomingContestDTO is extended from AbstractContestDTO.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("UpcomingContestDTO should extends from AbstractContestDTO", dto instanceof AbstractContestDTO);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        dto = new UpcomingContestDTO();
        assertNull("register date should be initialized to null", dto.getRegisterDate());
        assertNull("submit date should be initialized to 0", dto.getSubmitDate());
        assertEquals("duration should be initialized to 0", 0, dto.getDuration());
        assertNull("technologies should be initialized to null", dto.getTechnologies());
        assertNull("status should be initialized to null", dto.getStatus());
        assertEquals("first prize should be initialized to 0", 0.0, dto.getFirstPrize());
    }

    /**
     * <p>
     * Tests the setRegisterDate and getRegisterDate methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetRegisterDate() {
        // null
        dto.setRegisterDate(null);
        assertNull("register date should be set", dto.getRegisterDate());
        // non-null
        Date date = new Date();
        dto.setRegisterDate(date);
        assertEquals("register date should be set", date, dto.getRegisterDate());
    }

    /**
     * <p>
     * Tests the setSubmitDate and getSubmitDate methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetSubmitDate() {
        // null
        dto.setSubmitDate(null);
        assertNull("submit date should be set", dto.getSubmitDate());
        // non-null
        Date date = new Date();
        dto.setSubmitDate(date);
        assertEquals("submit date should be set", date, dto.getSubmitDate());
    }

    /**
     * <p>
     * Tests the setDuration and getDuration methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetDuration() {
        // zero
        dto.setDuration(0);
        assertEquals("duration should be set", 0, dto.getDuration());
        // negative
        dto.setDuration(-2);
        assertEquals("duration should be set", -2, dto.getDuration());
        // positive
        dto.setDuration(5);
        assertEquals("duration should be set", 5, dto.getDuration());
    }

    /**
     * <p>
     * Tests the setTechnologies and getTechnologies methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetTechnologies() {
        // null
        dto.setTechnologies(null);
        assertNull("technologies should be set", dto.getTechnologies());
        // empty
        dto.setTechnologies("");
        assertEquals("technologies should be set", "", dto.getTechnologies());
        // non-null, non-empty
        dto.setTechnologies("technology");
        assertEquals("technologies should be set", "technology", dto.getTechnologies());
    }

    /**
     * <p>
     * Tests the setStatus and getStatus methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetStatus() {
        // null
        dto.setStatus(null);
        assertNull("status should be set", dto.getStatus());
        // empty
        dto.setStatus("");
        assertEquals("status should be set", "", dto.getStatus());
        // non-null, non-empty
        dto.setStatus("status");
        assertEquals("status should be set", "status", dto.getStatus());
    }

    /**
     * <p>
     * Tests the setFirstPrize and getFirstPrize methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetFirstPrize() {
        // zero
        dto.setFirstPrize(0);
        assertEquals("first prize should be set", 0.0, dto.getFirstPrize());
        // negative
        dto.setFirstPrize(-123.45);
        assertEquals("first prize should be set", -123.45, dto.getFirstPrize(), 1E-6);
        // positive
        dto.setFirstPrize(543.21);
        assertEquals("first prize should be set", 543.21, dto.getFirstPrize(), 1E-6);
    }
}
