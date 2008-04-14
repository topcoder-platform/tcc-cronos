/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for ContestCategoryData class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestCategoryDataTest extends TestCase {
    /**
     * Bean to test.
     */
    private ContestCategoryData target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestCategoryDataTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new ContestCategoryData();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for contestCategoryId field.
     */
    public void testContestCategoryId() {
        assertEquals("default value", -1, target.getContestCategoryId());
        target.setContestCategoryId(35);
        assertEquals("new value", 35, target.getContestCategoryId());
    }

    /**
     * Tests setter/getter for contestName field.
     */
    public void testContestName() {
        assertNull("default value", target.getContestName());
        target.setContestName("abc");
        assertEquals("new value", "abc", target.getContestName());
    }

    /**
     * Tests setter/getter for contestDescription field.
     */
    public void testContestDescription() {
        assertNull("default value", target.getContestDescription());
        target.setContestDescription("abc");
        assertEquals("new value", "abc", target.getContestDescription());
    }

    /**
     * Tests setter/getter for contestCategory field.
     */
    public void testContestCategory() {
        assertNull("default value", target.getContestCategory());
        target.setContestCategory("abc");
        assertEquals("new value", "abc", target.getContestCategory());
    }
}
