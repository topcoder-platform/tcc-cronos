/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.dto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.dto.AbstractContestsFilter;
import com.topcoder.web.tc.dto.ContestsFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;

/**
 * <p>
 * Accuracy test for ContestsFilter class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestsFilterAccuracyTest {
    /**
     * Represents the instance of ContestsFilter used in test.
     */
    private ContestsFilter filter;

    /**
     * <p>
     * Set up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new ContestsFilter();
    }

    /**
     * Accuracy test for ContestsFilter(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", filter);
        assertEquals("The base class is incorrect.", filter.getClass().getSuperclass(), AbstractContestsFilter.class);
    }

    /**
     * <p>
     * Accuracy test for contestFinalization property.
     * </p>
     */
    @Test
    public void testContestFinalization() {
        DateIntervalSpecification contestFinalization = new DateIntervalSpecification();
        filter.setContestFinalization(contestFinalization);
        assertEquals("The contestFinalization is inocrrect.", contestFinalization, filter.getContestFinalization());
    }

    /**
     * <p>
     * Accuracy test for winnerHandle property.
     * </p>
     */
    @Test
    public void testWinnerHandle() {
        String winnerHandle = "test";
        filter.setWinnerHandle(winnerHandle);
        assertEquals("The winnerHandle is inocrrect.", winnerHandle, filter.getWinnerHandle());
    }
}