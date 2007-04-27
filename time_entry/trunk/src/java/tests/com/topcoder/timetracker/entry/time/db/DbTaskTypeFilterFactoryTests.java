/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTaskTypeFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTaskTypeFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbTaskTypeFilterFactory instance for testing.
     * </p>
     */
    private DbTaskTypeFilterFactory factory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        factory = new DbTaskTypeFilterFactory();
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
        return new TestSuite(DbTaskTypeFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTaskTypeFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTaskTypeFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) is correct.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        LikeFilter filter = (LikeFilter) factory.createDescriptionFilter("description", StringMatchType.ENDS_WITH);
        assertEquals("Failed to create the description filter correctly.", "DESCRIPTION", filter.getName());
        assertEquals("Failed to create the description filter correctly.", "EW:description", filter.getValue());
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_NullDescription() {
        try {
            factory.createDescriptionFilter(null, StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_EmptyDescription() {
        try {
            factory.createDescriptionFilter(" ", StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_NullMatchType() {
        try {
            factory.createDescriptionFilter("description", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeFilterFactory#createCompanyIdFilter(long) is correct.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createCompanyIdFilter(8);
        assertEquals("Failed to create the company id filter correctly.", "COMPANY_ID", filter.getName());
        assertEquals("Failed to create the company id filter correctly.", new Long(8), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createCompanyIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCompanyIdFilter_Negative() {
        try {
            factory.createCompanyIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createActiveFilter(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeFilterFactory#createActiveFilter(boolean) is correct.
     * </p>
     */
    public void testCreateActiveFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createActiveFilter(true);
        assertEquals("Failed to create the active filter correctly.", "ACTIVE", filter.getName());
        assertEquals("Failed to create the active filter correctly.", new Long(1), filter.getValue());
    }

}