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
 * Tests the ContestsFilter class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ContestsFilterTest {

    /**
     * Represents an instance of ContestsFilter for test.
     */
    private ContestsFilter filter;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        filter = new ContestsFilter();
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
     * Tests that ContestsFilter is extended from AbstractContestFilter.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("ContestsFilter should extends from AbstractContestFilter", filter instanceof AbstractContestsFilter);
    }

    /**
     * <p>
     * Tests the default constructor. It verifies that all fields are initialized correctly.
     * </p>
     */
    @Test
    public void testCtor() {
        filter = new ContestsFilter();
        assertNull("contest finalization date should be initialized to null", filter.getContestFinalization());
        assertNull("winner handle should be initialized to null", filter.getWinnerHandle());
    }

    /**
     * <p>
     * Tests the setContestFinalization and getContestFinalization methods. It verifies that the field can be accessed
     * correctly.
     * </p>
     */
    @Test
    public void testSetGetContestFinalization() {
        // null
        filter.setContestFinalization(null);
        assertNull("contest finalization date should be set", filter.getContestFinalization());
        // non-null
        DateIntervalSpecification date = new DateIntervalSpecification();
        filter.setContestFinalization(date);
        assertEquals("contest finalization date should be set", date, filter.getContestFinalization());
    }

    /**
     * <p>
     * Tests the setWinnerHandle and getWinnerHandle methods. It verifies that the field can be accessed correctly.
     * </p>
     */
    @Test
    public void testSetGetWinnerHandle() {
        // null
        filter.setWinnerHandle(null);
        assertNull("winner handle should be set", filter.getWinnerHandle());
        // empty
        filter.setWinnerHandle("");
        assertEquals("winner handle should be set", "", filter.getWinnerHandle());
        // non-null, non-empty
        filter.setWinnerHandle("handle");
        assertEquals("winner handle should be set", "handle", filter.getWinnerHandle());
    }
}
