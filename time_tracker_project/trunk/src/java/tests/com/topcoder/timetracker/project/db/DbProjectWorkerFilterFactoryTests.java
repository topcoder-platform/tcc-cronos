/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.util.Date;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectWorkerFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectWorkerFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbProjectWorkerFilterFactory instance for testing.
     * </p>
     */
    private DbProjectWorkerFilterFactory factory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        factory = new DbProjectWorkerFilterFactory();
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
        return new TestSuite(DbProjectWorkerFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerFilterFactory#DbProjectWorkerFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectWorkerFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectWorkerFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createProjectIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerFilterFactory#createProjectIdFilter(long) is correct.
     * </p>
     */
    public void testCreateProjectIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createProjectIdFilter(8);
        assertEquals("Failed to create the project id filter correctly.", "WORKER_PROJECT_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createProjectIdFilter(long) for failure.
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
     * Tests DbProjectWorkerFilterFactory#createUserIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerFilterFactory#createUserIdFilter(long) is correct.
     * </p>
     */
    public void testCreateUserIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createUserIdFilter(8);
        assertEquals("Failed to create the user id filter correctly.", "WORKER_USER_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createUserIdFilter(long) for failure.
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

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createStartDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerFilterFactory#createStartDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateStartDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createStartDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the start date filter correctly.", "WORKER_START_DATE", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createStartDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range start is null and expects success.
     * </p>
     */
    public void testCreateStartDateFilter_NullRangeStart() {
        Filter filter = factory.createStartDateFilter(null, new Date(20000));
        assertEquals("Failed to create the start date filter correctly.", LessThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the start date filter correctly.", "WORKER_START_DATE",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createStartDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range end is null and expects success.
     * </p>
     */
    public void testCreateStartDateFilter_NullRangeEnd() {
        Filter filter = factory.createStartDateFilter(new Date(20000), null);
        assertEquals("Failed to create the start date filter correctly.", GreaterThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the start date filter correctly.", "WORKER_START_DATE",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createStartDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateStartDateFilter_NullBothArguments() {
        try {
            factory.createStartDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createStartDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateStartDateFilter_InvalidArguments() {
        try {
            factory.createStartDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createEndDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerFilterFactory#createEndDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateEndDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createEndDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the end date filter correctly.", "WORKER_END_DATE", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createEndDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range start is null and expects success.
     * </p>
     */
    public void testCreateEndDateFilter_NullRangeStart() {
        Filter filter = factory.createEndDateFilter(null, new Date(20000));
        assertEquals("Failed to create the end date filter correctly.", LessThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the end date filter correctly.", "WORKER_END_DATE",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createEndDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range end is null and expects success.
     * </p>
     */
    public void testCreateEndDateFilter_NullRangeEnd() {
        Filter filter = factory.createEndDateFilter(new Date(20000), null);
        assertEquals("Failed to create the end date filter correctly.", GreaterThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the end date filter correctly.", "WORKER_END_DATE",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createEndDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEndDateFilter_NullBothArguments() {
        try {
            factory.createEndDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createEndDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEndDateFilter_InvalidArguments() {
        try {
            factory.createEndDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createPayRateFilter(double,double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerFilterFactory#createPayRateFilter(double,double) is correct.
     * </p>
     */
    public void testCreatePayRateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createPayRateFilter(5.0, 8.0);
        assertEquals("Failed to create the pay rate filter correctly.", "PAY_RATE", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectWorkerFilterFactory#createPayRateFilter(double,double) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePayRateFilter_InvalidArguments() {
        try {
            factory.createPayRateFilter(12.0, 8.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}