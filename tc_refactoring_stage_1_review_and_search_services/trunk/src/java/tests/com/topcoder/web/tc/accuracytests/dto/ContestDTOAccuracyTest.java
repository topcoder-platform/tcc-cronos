/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestDTO;
import com.topcoder.web.tc.dto.ContestDTO;

/**
 * <p>
 * Accuracy test for ContestDTO class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestDTOAccuracyTest {
    /**
     * Represents the instance of ContestDTO used in test.
     */
    private ContestDTO dto;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new ContestDTO();
    }

    /**
     * Accuracy test for ContestDTO(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", dto);
        assertEquals("The base class is incorrect.", dto.getClass().getSuperclass(), AbstractContestDTO.class);
    }

    /**
     * <p>
     * Accuracy test for catalog property.
     * </p>
     */
    @Test
    public void testCatalog() {
        String catalog = "generic";
        dto.setCatalog(catalog);
        assertEquals("The contestName is inocrrect.", catalog, dto.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for numberOfRegistrants property.
     * </p>
     */
    @Test
    public void testnumberOfRegistrants() {
        int numberOfRegistrants = 20;
        dto.setNumberOfRegistrants(numberOfRegistrants);
        assertEquals("The numberOfRegistrants is inocrrect.", numberOfRegistrants, dto.getNumberOfRegistrants());
    }

    /**
     * <p>
     * Accuracy test for numberOfSubmissions property.
     * </p>
     */
    @Test
    public void testNumberOfSubmissions() {
        int numberOfSubmissions = 3;
        dto.setNumberOfSubmissions(numberOfSubmissions);
        assertEquals("The numberOfSubmissions is inocrrect.", numberOfSubmissions, dto.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Accuracy test for passedScreeningCount property.
     * </p>
     */
    @Test
    public void testPassedScreeningCount() {
        int passedScreeningCount = 2;
        dto.setPassedScreeningCount(passedScreeningCount);
        assertEquals("The passedScreeningCount is inocrrect.", passedScreeningCount, dto.getPassedScreeningCount());
    }

    /**
     * <p>
     * Accuracy test for winnerProfileLink property.
     * </p>
     */
    @Test
    public void testWinnerProfileLink() {
        String winnerProfileLink = "http://www.tocpoder.com/tc";
        dto.setWinnerProfileLink(winnerProfileLink);
        assertEquals("The winnerProfileLink is inocrrect.", winnerProfileLink, dto.getWinnerProfileLink());
    }

    /**
     * <p>
     * Accuracy test for winnerScore property.
     * </p>
     */
    @Test
    public void testWinnerScore() {
        double winnerScore = 98.9;
        dto.setWinnerScore(winnerScore);
        assertEquals("The winnerProfileLink is inocrrect.", winnerScore, dto.getWinnerScore());
    }
}