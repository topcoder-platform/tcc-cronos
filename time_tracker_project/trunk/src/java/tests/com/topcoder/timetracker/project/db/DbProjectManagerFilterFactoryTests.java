/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectManagerFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectManagerFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbProjectManagerFilterFactory instance for testing.
     * </p>
     */
    private DbProjectManagerFilterFactory factory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        factory = new DbProjectManagerFilterFactory();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        factory = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbProjectManagerFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerFilterFactory#DbProjectManagerFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectManagerFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectManagerFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests DbProjectManagerFilterFactory#createProjectIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerFilterFactory#createProjectIdFilter(long) is correct.
     * </p>
     */
    public void testCreateProjectIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createProjectIdFilter(8);
        assertEquals("Failed to create the project id filter correctly.", "MANAGER_PROJECT_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectManagerFilterFactory#createProjectIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateProjectIdFilter_NegativeProjectId() {
        try {
            factory.createProjectIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerFilterFactory#createUserIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerFilterFactory#createUserIdFilter(long) is correct.
     * </p>
     */
    public void testCreateUserIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createUserIdFilter(8);
        assertEquals("Failed to create the user id filter correctly.", "MANAGER_USER_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectManagerFilterFactory#createUserIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateUserIdFilter_NegativeUserId() {
        try {
            factory.createUserIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}