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
 * Tests the ContestDTO class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ContestDTOTest {

    /**
     * Represents an instance of ContestDTO for test.
     */
    private ContestDTO dto;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new ContestDTO();
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
     * Tests that ContestDTO is extended from AbstractContestDTO.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("ContestDTO should extends from AbstractContestDTO", dto instanceof AbstractContestDTO);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        dto = new ContestDTO();
        assertNull("catalog should be initialized to null", dto.getCatalog());
        assertEquals("number of registrants should be initialized to 0", 0, dto.getNumberOfRegistrants());
        assertEquals("number of submissions should be initialized to 0", 0, dto.getNumberOfSubmissions());
        assertEquals("passed screening count should be initialized to 0", 0, dto.getPassedScreeningCount());
        assertNull("winner profile link should be initialized to null", dto.getWinnerProfileLink());
        assertEquals("winner score should be initialized to 0", 0.0, dto.getWinnerScore());
    }

    /**
     * <p>
     * Tests the setCatalog and getCatalog methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetCatalog() {
        // null
        dto.setCatalog(null);
        assertNull("catalog should be set", dto.getCatalog());
        // empty
        dto.setCatalog("");
        assertEquals("catalog should be set", "", dto.getCatalog());
        // non-null, non-empty
        dto.setCatalog("catalog");
        assertEquals("catalog should be set", "catalog", dto.getCatalog());
    }

    /**
     * <p>
     * Tests the setNumberOfRegistrants and getNumberOfRegistrants methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetNumberOfRegistrants() {
        // zero
        dto.setNumberOfRegistrants(0);
        assertEquals("number of registrants should be set", 0, dto.getNumberOfRegistrants());
        // negative
        dto.setNumberOfRegistrants(-2);
        assertEquals("number of registrants should be set", -2, dto.getNumberOfRegistrants());
        // positive
        dto.setNumberOfRegistrants(5);
        assertEquals("number of registrants should be set", 5, dto.getNumberOfRegistrants());
    }

    /**
     * <p>
     * Tests the setNumberOfSubmissions and getNumberOfSubmissions methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetNumberOfSubmissions() {
        // zero
        dto.setNumberOfSubmissions(0);
        assertEquals("number of submissions should be set", 0, dto.getNumberOfSubmissions());
        // negative
        dto.setNumberOfSubmissions(-2);
        assertEquals("number of submissions should be set", -2, dto.getNumberOfSubmissions());
        // positive
        dto.setNumberOfSubmissions(5);
        assertEquals("number of submissions should be set", 5, dto.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Tests the setPassedScreeningCount and getPassedScreeningCount methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetPassedScreeningCount() {
        // zero
        dto.setPassedScreeningCount(0);
        assertEquals("passed screening count should be set", 0, dto.getPassedScreeningCount());
        // negative
        dto.setPassedScreeningCount(-2);
        assertEquals("passed screening count should be set", -2, dto.getPassedScreeningCount());
        // positive
        dto.setPassedScreeningCount(5);
        assertEquals("passed screening count should be set", 5, dto.getPassedScreeningCount());
    }

    /**
     * <p>
     * Tests the setWinnerProfileLink and getWinnerProfileLink methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetWinnerProfileLink() {
        // null
        dto.setWinnerProfileLink(null);
        assertNull("winner profile link should be set", dto.getWinnerProfileLink());
        // empty
        dto.setWinnerProfileLink("");
        assertEquals("winner profile link should be set", "", dto.getWinnerProfileLink());
        // non-null, non-empty
        dto.setWinnerProfileLink("profile");
        assertEquals("winner profile link should be set", "profile", dto.getWinnerProfileLink());
    }

    /**
     * <p>
     * Tests the setWinnerScore and getWinnerScore methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetWinnerScore() {
        // zero
        dto.setWinnerScore(0);
        assertEquals("winner score should be set", 0, dto.getWinnerScore(), 1E-6);
        // negative
        dto.setWinnerScore(-2.5);
        assertEquals("winner score should be set", -2.5, dto.getWinnerScore(), 1E-6);
        // positive
        dto.setWinnerScore(99.1);
        assertEquals("winner score should be set", 99.1, dto.getWinnerScore(), 1E-6);
    }
}
