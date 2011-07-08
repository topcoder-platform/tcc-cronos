/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestDTO;
import com.topcoder.web.tc.dto.ContestNameEntity;

/**
 * <p>
 * Accuracy test for AbstractContestDTO class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractContestDTOAccuracyTest {
    /**
     * Represents the instance of AbstractContestDTO used in test.
     */
    private AbstractContestDTO dto;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        dto = new AbstractContestDTO() {
        };
    }

    /**
     * Accuracy test for ContestDTO(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", dto);
        assertEquals("The base class is incorrect.", dto.getClass().getSuperclass().getSuperclass(), ContestNameEntity.class);
    }

    /**
     * <p>
     * Accuracy test for type property.
     * </p>
     */
    @Test
    public void testType() {
        String type = "generic";
        dto.setType(type);
        assertEquals("The type is inocrrect.", type, dto.getType());
    }

    /**
     * <p>
     * Accuracy test for type property.
     * </p>
     */
    @Test
    public void testsubType() {
        String subType = "sub";
        dto.setSubtype(subType);
        assertEquals("The subType is inocrrect.", subType, dto.getSubtype());
    }
}