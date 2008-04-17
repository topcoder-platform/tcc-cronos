/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.studio.ContestCategoryData;

/**
 * <p>
 * Accuracy tests for ContestCategoryData class.
 * </p>
 *
 * @author moon.river
 * @version 1.0
 */
public class ContestCategoryDataTest extends TestCase {
    /**
     * <p>
     * Represents the instance to test.
     * </p>
     */
    private ContestCategoryData data;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        data = new ContestCategoryData();
    }

    /**
     * <p>
     * Tests setter/getter for contestCategoryId field.
     * </p>
     */
    public void testContestCategoryId() {
        data.setContestCategoryId(35);
        assertEquals("The id is wrong.", 35, data.getContestCategoryId());
    }

    /**
     * <p>
     * Tests setter/getter for contestName field.
     * </p>
     */
    public void testContestName() {
        data.setContestName("abc");
        assertEquals("The name is wrong.", "abc", data.getContestName());
    }

    /**
     * <p>
     * Tests setter/getter for contestDescription field.
     * </p>
     */
    public void testContestDescription() {
        data.setContestDescription("abc");
        assertEquals("The description is wrong.", "abc", data.getContestDescription());
    }

    /**
     * <p>
     * Tests setter/getter for contestCategory field.
     * </p>
     */
    public void testContestCategory() {
        data.setContestCategory("abc");
        assertEquals("The category is wrong.", "abc", data.getContestCategory());
    }
}
