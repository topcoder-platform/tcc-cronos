/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.ContestNameEntity;

/**
 * <p>
 * Accuracy test for ContestNameEntity class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestNameEntityAccuracyTest {
    /**
     * Represents the instance of ContestNameEntity used in test.
     */
    private ContestNameEntity entity;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        entity = new ContestNameEntity() {
        };
    }

    /**
     * Accuracy test for ContestNameEntity(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", entity);
    }

    /**
     * <p>
     * Accuracy test for contestName property.
     * </p>
     */
    @Test
    public void testContestName() {
        String contestName = "Dev";
        entity.setContestName(contestName);
        assertEquals("The contestName is inocrrect.", contestName, entity.getContestName());
    }
}