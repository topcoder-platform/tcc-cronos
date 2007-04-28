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
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbProjectFilterFactory instance for testing.
     * </p>
     */
    private DbProjectFilterFactory factory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        factory = new DbProjectFilterFactory();
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
        return new TestSuite(DbProjectFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectFilterFactory#DbProjectFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createStartDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createStartDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateStartDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createStartDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the start date filter correctly.", "PROJECT_START_DATE", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createStartDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the start date filter correctly.", "PROJECT_START_DATE",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createStartDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the start date filter correctly.", "PROJECT_START_DATE",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createStartDateFilter(Date,Date) for failure.
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
     * Tests DbProjectFilterFactory#createStartDateFilter(Date,Date) for failure.
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
     * Tests DbProjectFilterFactory#createEndDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createEndDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateEndDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createEndDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the end date filter correctly.", "PROJECT_END_DATE", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createEndDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the end date filter correctly.", "PROJECT_END_DATE",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createEndDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the end date filter correctly.", "PROJECT_END_DATE",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createEndDateFilter(Date,Date) for failure.
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
     * Tests DbProjectFilterFactory#createEndDateFilter(Date,Date) for failure.
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
     * Tests DbProjectFilterFactory#createClientIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createClientIdFilter(long) is correct.
     * </p>
     */
    public void testCreateClientIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createClientIdFilter(8);
        assertEquals("Failed to create the client id filter correctly.", "CLIENT_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createClientIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when clientId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateClientIdFilter_NegativeClientId() {
        try {
            factory.createClientIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createCompanyIdFilter(long) is correct.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createCompanyIdFilter(8);
        assertEquals("Failed to create the company id filter correctly.", "COMPANY_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createCompanyIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCompanyIdFilter_NegativeCompanyId() {
        try {
            factory.createCompanyIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createNameFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createNameFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateNameFilter() {
        LikeFilter filter = (LikeFilter) factory.createNameFilter(StringMatchType.ENDS_WITH, "name");
        assertEquals("Failed to create the name filter correctly.", "PROJECT_NAME", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateNameFilter_NullMatchType() {
        try {
            factory.createNameFilter(null, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateNameFilter_NullName() {
        try {
            factory.createNameFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when name is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateNameFilter_EmptyName() {
        try {
            factory.createNameFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsProjectManagerFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createContainsProjectManagerFilter(long) is correct.
     * </p>
     */
    public void testCreateContainsProjectManagerFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createContainsProjectManagerFilter(8);
        assertEquals("Failed to create the manager filter correctly.", "PROJECT_MANAGER_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsProjectManagerFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when managerId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateContainsProjectManagerFilter_NegativeManagerId() {
        try {
            factory.createContainsProjectManagerFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsProjectWorkerFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createContainsProjectWorkerFilter(long) is correct.
     * </p>
     */
    public void testCreateContainsProjectWorkerFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createContainsProjectWorkerFilter(8);
        assertEquals("Failed to create the worker filter correctly.", "PROJECT_WORKER_ID", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsProjectWorkerFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when workerId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateContainsProjectWorkerFilter_NegativeWorkerId() {
        try {
            factory.createContainsProjectWorkerFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) is correct.
     * </p>
     */
    public void testCreateContainsEntryFilter_ExpenseEntry() {
        EqualToFilter filter = (EqualToFilter) factory.createContainsEntryFilter(8, EntryType.EXPENSE_ENTRY);
        assertEquals("Failed to create the entry filter correctly.", "PROJECT_EXPENSE_ENTRY", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) is correct.
     * </p>
     */
    public void testCreateContainsEntryFilter_FixedBillingEntry() {
        EqualToFilter filter = (EqualToFilter) factory.createContainsEntryFilter(8, EntryType.FIXED_BILLING_ENTRY);
        assertEquals("Failed to create the entry filter correctly.", "PROJECT_FIXED_BILL_ENTRY", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) is correct.
     * </p>
     */
    public void testCreateContainsEntryFilter_TimeEntry() {
        EqualToFilter filter = (EqualToFilter) factory.createContainsEntryFilter(8, EntryType.TIME_ENTRY);
        assertEquals("Failed to create the entry filter correctly.", "PROJECT_TIME_ENTRY", filter.getName());
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entryId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateContainsEntryFilter_NegativeEntryId() {
        try {
            factory.createContainsEntryFilter(-8, EntryType.TIME_ENTRY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createContainsEntryFilter(long,EntryType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateContainsEntryFilter_NullType() {
        try {
            factory.createContainsEntryFilter(8, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectFilterFactory#createIsActiveFilter(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectFilterFactory#createIsActiveFilter(boolean) is correct.
     * </p>
     */
    public void testCreateIsActiveFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createIsActiveFilter(true);
        assertEquals("Failed to create the active filter correctly.", "PROJECT_ACTIVE", filter.getName());
    }

}