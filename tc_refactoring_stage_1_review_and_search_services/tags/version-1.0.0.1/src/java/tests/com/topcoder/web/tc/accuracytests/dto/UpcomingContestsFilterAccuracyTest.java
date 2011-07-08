/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestsFilter;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;

/**
 * <p>
 * Accuracy test for UpcomingContestsFilter class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpcomingContestsFilterAccuracyTest {
    /**
     * Represents the instance of UpcomingContestsFilter used in test.
     */
    private UpcomingContestsFilter filter;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new UpcomingContestsFilter();
    }

    /**
     * Accuracy test for UpcomingContestsFilter(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", filter);
        assertEquals("The base class is incorrect.", filter.getClass().getSuperclass(), AbstractContestsFilter.class);
    }

    /**
     * <p>
     * Accuracy test for prizeStart property.
     * </p>
     */
    @Test
    public void testPrizeStart() {
        filter.setPrizeStart(3);
        assertEquals("The prizeStart is inocrrect.", 3, filter.getPrizeStart().intValue());
    }

    /**
     * <p>
     * Accuracy test for prizeEnd property.
     * </p>
     */
    @Test
    public void testPrizeEnd() {
        filter.setPrizeEnd(30);
        assertEquals("The prizeEnd is inocrrect.", 30, filter.getPrizeEnd().intValue());
    }
}
