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
import com.topcoder.web.tc.dto.UpcomingContestDTO;

/**
 * <p>
 * Accuracy test for UpcomingContestDTO class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpcomingContestDTOAccuracyTest {
    /**
     * Represents the instance of UpcomingContestDTO used in test.
     */
    private UpcomingContestDTO dto;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new UpcomingContestDTO();
    }

    /**
     * Accuracy test for UpcomingContestDTO(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", dto);
        assertEquals("The base class is incorrect.", dto.getClass().getSuperclass(), AbstractContestDTO.class);
    }

    /**
     * <p>
     * Accuracy test for registerDate property.
     * </p>
     */
    @Test
    public void testPrizeStart() {
        Date date = new Date();
        dto.setRegisterDate(date);
        assertEquals("The registerDate is inocrrect.", date, dto.getRegisterDate());
    }

    /**
     * <p>
     * Accuracy test for submitDate property.
     * </p>
     */
    @Test
    public void testSubmitDate() {
        Date date = new Date();
        dto.setSubmitDate(date);
        assertEquals("The submitDate is inocrrect.", date, dto.getSubmitDate());
    }

    /**
     * <p>
     * Accuracy test for duration property.
     * </p>
     */
    @Test
    public void testDuration() {
        int duration = 11;
        dto.setDuration(duration);
        assertEquals("The duration is inocrrect.", duration, dto.getDuration());
    }

    /**
     * <p>
     * Accuracy test for technologies property.
     * </p>
     */
    @Test
    public void testTechnologies() {
        String technologies = "java";
        dto.setTechnologies(technologies);
        assertEquals("The technologies is inocrrect.", technologies, dto.getTechnologies());
    }

    /**
     * <p>
     * Accuracy test for status property.
     * </p>
     */
    @Test
    public void testStatus() {
        String status = "Reg";
        dto.setStatus(status);
        assertEquals("The status is inocrrect.", status, dto.getStatus());
    }

    /**
     * <p>
     * Accuracy test for firstPrize property.
     * </p>
     */
    @Test
    public void testFirstPrize() {
        double firstPrize = 500.00;
        dto.setFirstPrize(firstPrize);
        assertEquals("The firstPrize is inocrrect.", firstPrize, dto.getFirstPrize());
    }
}