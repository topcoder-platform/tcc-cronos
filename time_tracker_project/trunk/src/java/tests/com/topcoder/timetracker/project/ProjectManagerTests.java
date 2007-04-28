/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectManager.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectManagerTests extends TestCase {
    /**
     * <p>
     * The ProjectManager instance for testing.
     * </p>
     */
    private ProjectManager manager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        manager = new ProjectManager();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        manager = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectManagerTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectManager#ProjectManager() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectManager instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectManager instance.", manager);
    }

    /**
     * <p>
     * Tests ProjectManager#getProjectId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManager#getProjectId() is correct.
     * </p>
     */
    public void testGetProjectId() {
        manager.setProjectId(8);
        assertEquals("Failed to get the project id correctly.", 8, manager.getProjectId());
    }

    /**
     * <p>
     * Tests ProjectManager#setProjectId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManager#setProjectId(long) is correct.
     * </p>
     */
    public void testSetProjectId() {
        manager.setProjectId(8);
        assertEquals("Failed to set the project id correctly.", 8, manager.getProjectId());
    }

    /**
     * <p>
     * Tests ProjectManager#setProjectId(long) for failure.
     * </p>
     *
     * <p>
     *  It tests the case that when projectId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetProjectId_NegativeId() {
        try {
            manager.setProjectId(-5);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManager#getUserId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManager#getUserId() is correct.
     * </p>
     */
    public void testGetUserId() {
        manager.setUserId(8);
        assertEquals("Failed to get the user id correctly.", 8, manager.getUserId());
    }

    /**
     * <p>
     * Tests ProjectManager#setUserId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManager#setUserId(long) is correct.
     * </p>
     */
    public void testSetUserId() {
        manager.setUserId(8);
        assertEquals("Failed to set the user id correctly.", 8, manager.getUserId());
    }

    /**
     * <p>
     * Tests ProjectManager#setUserId(long) for failure.
     * </p>
     *
     * <p>
     *  It tests the case that when userId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetUserId_NegativeId() {
        try {
            manager.setUserId(-5);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}