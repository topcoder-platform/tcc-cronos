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
 * Tests the UpcomingContestsFilter class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class UpcomingContestsFilterTest {

    /**
     * Represents an instance of UpcomingContestsFilter for test.
     */
    private UpcomingContestsFilter filter;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new UpcomingContestsFilter();
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        filter = null;
    }

    /**
     * <p>
     * Tests that UpcomingContestsFilter is extended from AbstractContestFilter.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("UpcomingContestsFilter should extends from AbstractContestFilter",
                filter instanceof AbstractContestsFilter);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        filter = new UpcomingContestsFilter();
        assertNull("prize start should be initialized to null", filter.getPrizeStart());
        assertNull("prize end should be initialized to null", filter.getPrizeEnd());
    }

    /**
     * <p>
     * Tests the setPrizeStart and getPrizeStart methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetPrizeStart() {
        // null
        filter.setPrizeStart(null);
        assertNull("prize start should be set", filter.getPrizeStart());
        // zero
        filter.setPrizeStart(0);
        assertEquals("prize start should be set", 0, filter.getPrizeStart().intValue());
        // negative
        filter.setPrizeStart(-200);
        assertEquals("prize start should be set", -200, filter.getPrizeStart().intValue());
        // positive
        filter.setPrizeStart(500);
        assertEquals("prize start should be set", 500, filter.getPrizeStart().intValue());
    }

    /**
     * <p>
     * Tests the setPrizeEnd and getPrizeEnd methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetPrizeEnd() {
        // null
        filter.setPrizeEnd(null);
        assertNull("prize end should be set", filter.getPrizeEnd());
        // zero
        filter.setPrizeEnd(0);
        assertEquals("prize end should be set", 0, filter.getPrizeEnd().intValue());
        // negative
        filter.setPrizeEnd(-200);
        assertEquals("prize end should be set", -200, filter.getPrizeEnd().intValue());
        // positive
        filter.setPrizeEnd(500);
        assertEquals("prize end should be set", 500, filter.getPrizeEnd().intValue());
    }
}
